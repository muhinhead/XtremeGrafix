package com.xgraf.rmi;

import com.xgraf.XGrafWorks;
import com.xgraf.remote.IMessageSender;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

/**
 *
 * @author Nick Mukhin
 */
public class ExchangeFactory {

    private static final int DB_VERSION_ID = 2;
    public static final String DB_VERSION = "0.2";
    private static String[] fixLocalDBsqls = new String[]{
        "update dbversion set version_id = " + DB_VERSION_ID + ",version = '" + DB_VERSION + "'",
        "alter table contact add photo mediumblob",
        "alter table company add logo mediumblob",
        "alter table invoice add quote_id int",
        "alter table invoice add constraint invoice_quote_fk foreign key (quote_id) references quote (quote_id)"
    };

    public static IMessageSender getExchanger(String connectString, Properties props) {
        IMessageSender exchanger = null;
        if (connectString.startsWith("rmi:")) {
            try {
                exchanger = (IMessageSender) Naming.lookup(connectString);
                XGrafWorks.protocol = "rmi";
            } catch (Exception ex) {
                XGrafWorks.log("RMI server not found");
            }
        } else if (connectString.startsWith("jdbc:")) {
            String dbUser = props.getProperty("dbUser", "xgraf");
            String dbPassword = props.getProperty("dbPassword", "xgraf");
            String dbDriver = props.getProperty("dbDriverName", "com.mysql.jdbc.Driver");
            try {
                exchanger = createJDBCexchanger(dbDriver, connectString, dbUser, dbPassword);
                XGrafWorks.protocol = "jdbc";
            } catch (Exception ex) {
                XGrafWorks.log(ex);
            }
        }
        return exchanger;
    }

    public static IMessageSender createRMIexchanger(String address) throws NotBoundException, MalformedURLException, RemoteException {
        XGrafWorks.protocol = "rmi";
        return (IMessageSender) Naming.lookup("rmi://" + address + "/" + XGrafWorks.SERVERNAME);
    }

    public static IMessageSender createJDBCexchanger(String[] dbParams) throws SQLException, IllegalAccessException, ClassNotFoundException, InstantiationException {
        if (dbParams.length < 4) {
            return null;
        }
        return createJDBCexchanger(dbParams[0], dbParams[1], dbParams[2], dbParams[3]);
    }

    public static IMessageSender createJDBCexchanger(String dbDriver, String connectString,
            String dbUser, String dbPassword) throws SQLException, IllegalAccessException, ClassNotFoundException, InstantiationException {
        if (dbDriver == null || dbDriver.isEmpty() || connectString == null || connectString.isEmpty()
                || dbUser == null || dbUser.isEmpty() || dbPassword == null || dbPassword.isEmpty()) {
            throw new SQLException("Incomplete DB connection parameters");
        }
        XGrafWorks.protocol = "jdbc";
        IMessageSender exchanger;
        DriverManager.registerDriver(
                (java.sql.Driver) Class.forName(dbDriver).newInstance());
        Connection connection = DriverManager.getConnection(connectString, dbUser, dbPassword);
        connection.setAutoCommit(true);
        sqlBatch(fixLocalDBsqls, connection, false);
        exchanger = new DbClientDataSender(XGrafWorks.getProperties());
        return exchanger;
    }

    public static void sqlBatch(String[] sqls, Connection connection, boolean tolog) {
        PreparedStatement ps = null;
        for (int i = 0; i < sqls.length; i++) {
            try {
                ps = connection.prepareStatement(sqls[i]);
                ps.execute();
                if (tolog) {
                    XGrafWorks.log("STATEMENT [" + sqls[i].substring(0,
                            sqls[i].length() > 60 ? 60 : sqls[i].length()) + "]... processed");
                }
            } catch (SQLException e) {
                if (tolog) {
                    XGrafWorks.log(e);
                }
            } finally {
                try {
                    ps.close();
                } catch (SQLException ex) {
                }
            }
        }
    }
}
