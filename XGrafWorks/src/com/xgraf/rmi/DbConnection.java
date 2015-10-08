package com.xgraf.rmi;

import com.xgraf.XGrafWorks;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;

/**
 *
 * @author Nick Mukhin
 */
public class DbConnection {

    private static class ConnectionWithFlag {

        boolean isBusy = false;
        Connection connection = null;
        long lastUsage = Calendar.getInstance().getTimeInMillis();

        ConnectionWithFlag(Connection c) {
            connection = c;
            isBusy = true;
//            XGrafWorks.log("connection (" + new Date(lastUsage).toString() + ") opened");
        }

        private void freeConnection() {
            isBusy = false;
//            XGrafWorks.log("connection (" + new Date(lastUsage).toString() + ") released");
        }
    }

    private static class ConnectionTouchTask extends TimerTask {

        private final boolean withLog;

        public ConnectionTouchTask(boolean withLog) {
            super();
            this.withLog = withLog;
        }

        @Override
        public void run() {
            for (ConnectionWithFlag cf : connections) {
                if (!cf.isBusy) {
                    if (Calendar.getInstance().getTimeInMillis() - cf.lastUsage > 30 * 1000) {
                        try {
                            cf.connection.close();
                            connections.remove(cf);
                            XGrafWorks.log("connection (" + new Date(cf.lastUsage).toString() + ") closed on timeout");
                            return;
                        } catch (SQLException ex) {
                            XGrafWorks.log(ex);
                        }
                    }
                } else {
                    XGrafWorks.log("connection busy");
                }
            }
        }
    }
    private static final int DB_VERSION_ID = 2;
    public static final String DB_VERSION = "0.2";
    private static boolean isFirstTime = true;
    private static Properties props = new Properties();
    private static String[] createLocalDBsqls = loadDDLscript("crebas.sql", ";");
    private static ArrayList<ConnectionWithFlag> connections = new ArrayList<ConnectionWithFlag>();
    private static String[] fixLocalDBsqls = new String[]{
        "update dbversion set version_id = " + DB_VERSION_ID + ",version = '" + DB_VERSION + "'",
        "alter table contact add photo mediumblob",
        "alter table company add logo mediumblob",
        "create table quote ("
        + "    quote_id int not null auto_increment,"
        + "    company_id int not null,"
        + "    contact_id int not null,"
        + "    service_type varchar(32),"
        + "    quote_ref varchar(32) not null,"
        + "    order_no varchar(16),"
        + "    quote_date date not null,"
        + "    is_proforma bit default 0,"
        + "    sub_total decimal (10,2),"
        + "    bank_acc_holder varchar(64),"
        + "    bank varchar(64),"
        + "    bank_branch_code varchar(32),"
        + "    bank_acc_no varchar(32),"
        + "    bank_acc_type varchar(16),"
        + "    valid_term date,"
        + "    deposit_percent tinyint,"
        + "    refund_break_percent tinyint,"
        + "    outbalance_weeks tinyint,"
        + "    pref_pay_method varchar(32),"
        + "    add_conditions varchar(128),"
        + "    constraint quote_pk primary key (quote_id),"
        + "    constraint quote_company_fk foreign key (company_id) references company (company_id),"
        + "    constraint quote_contact_fk foreign key (contact_id) references contact (contact_id)"
        + ")",
        "create table quoteitem ("
        + "    quoteitem_id int not null auto_increment,"
        + "    quote_id int not null,"
        + "    descr varchar(512) not null,"
        + "    qty int not null,"
        + "    unit_price decimal (8,2) not null,"
        + "    constraint quoteitem_pk primary key (quoteitem_id),"
        + "    constraint quoteitem_quote_fk foreign key (quote_id) references quote (quote_id) on delete cascade"
        + ")",
        "alter table company add trading_as varchar(128)",
        "create table invoice"
        + "("
        + "    invoice_id int not null auto_increment,"
        + "    company_id int not null,"
        + "    contact_id int not null,"
        + "    service_type varchar(32),"
        + "    invoice_ref varchar(32) not null,"
        + "    order_no varchar(16),"
        + "    invoice_date date not null,"
        + "    sub_total decimal (10,2),"
        + "    bank_acc_holder varchar(64),"
        + "    bank varchar(64),"
        + "    bank_branch_code varchar(32),"
        + "    bank_acc_no varchar(32),"
        + "    bank_acc_type varchar(16),"
        + "    valid_term date,"
        + "    deposit_percent tinyint,"
        + "    refund_break_percent tinyint,"
        + "    outbalance_weeks tinyint,"
        + "    pref_pay_method varchar(32),"
        + "    add_conditions varchar(128),"
        + "    constraint invoice_pk primary key (invoice_id),"
        + "    constraint invoice_company_fk foreign key (company_id) references company (company_id),"
        + "    constraint invoice_contact_fk foreign key (contact_id) references contact (contact_id)"
        + ")",
        "create table invoiceitem"
        + "("
        + "    invoiceitem_id int not null auto_increment,"
        + "    invoice_id int not null,"
        + "    descr varchar(255) not null,"
        + "    qty int not null,"
        + "    unit_price decimal (8,2) not null,"
        + "    constraint invoiceitem_pk primary key (invoiceitem_id),"
        + "    constraint invoiceitem_invoice_fk foreign key (invoice_id) references invoice (invoice_id) on delete cascade"
        + ")"
    };

    public static String getLogin() {
        return props.getProperty("dbUser", "xgraf");
    }

    public static String getPassword() {
        return props.getProperty("dbPassword", "xgraf");
    }

//    public static String getBackupCommand() {
//        return props.getProperty("dbDump", "mysqldump");
//    }
//
//    public static String getFtpURL() {
//        return props.getProperty("ftpURL", "ec2-54-226-3-180.compute-1.amazonaws.com");
//    }
//
//    public static String getFtpPath() {
//        return props.getProperty("ftpPath", "/root/backups/");
//    }
//
//    public static String getFtpLogin() {
//        return props.getProperty("ftpLogin", "alison");
//    }
//
//    public static String getFtpPassword() {
//        return props.getProperty("ftpLogin", "dainton");
//    }
    public static Connection getConnection() throws RemoteException {
        for (ConnectionWithFlag con : connections) {
            if (!con.isBusy) {
                con.isBusy = true;
//                XGrafWorks.log("connection (" + new Date(con.lastUsage).toString() + ") used");
                con.lastUsage = Calendar.getInstance().getTimeInMillis();
                return con.connection;
            }
//            else
//                System.out.println("!!! "+con.hashCode()+"connection is busy");
        }
        Connection connection = null;
        try {
            Locale.setDefault(Locale.ENGLISH);
            DriverManager.registerDriver(
                    (java.sql.Driver) Class.forName(
                            props.getProperty("dbDriverName",
                                    "com.mysql.jdbc.Driver")).newInstance());
            String connectionString = props.getProperty("JDBCconnection",
                    "jdbc:mysql://localhost/xgraf?characterEncoding=UTF8");
            String login = getLogin();
            String pwd = getPassword();
            connection = DriverManager.getConnection(
                    connectionString,
                    login, pwd);
            connection.setAutoCommit(true);
            sqlBatch(fixLocalDBsqls, connection, false);
//            RmiMessageSender.isMySQL = (connection.getClass().getCanonicalName().indexOf("mysql") > -1);
//            System.out.println("!!! "+connection.hashCode()+" - NEW CONNECTION");
        } catch (Exception e) {
            XGrafWorks.log(e);
        }
        if (isFirstTime) {
//            initLocalDB(connection);
//            fixLocalDB(connection);
            Timer timer = new Timer();
            timer.schedule(new ConnectionTouchTask(false), 1000, 30 * 1000);
            isFirstTime = false;
        }
        if (connection != null) {
            connections.add(new ConnectionWithFlag(connection));
            return checkVersion(connection);
        } else {
            XGrafWorks.logAndShowMessage(new Throwable("Can't establish the database connection"));
            return null;
        }
    }

    public static void initLocalDB(Connection connection) {
        sqlBatch(createLocalDBsqls, connection, false);
    }

    public static void fixLocalDB(Connection connection) {
        sqlBatch(fixLocalDBsqls, connection,
                props.getProperty("LogDbFixes", "false").equalsIgnoreCase("true"));
    }

    public static void sqlBatch(String sql, Connection connection, boolean tolog) {
        PreparedStatement ps = null;
        try {
            ps = connection.prepareStatement(sql);
            ps.execute();
            if (tolog) {
                XGrafWorks.log("STATEMENT [" + sql.substring(0,
                        sql.length() > 60 ? 60 : sql.length()) + "]... processed");
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

    public static void setProps(Properties props) {
        DbConnection.props = props;
    }

    public static Properties getProps() {
        return props;
    }

    public static void closeConnection(Connection connection) throws SQLException {
        for (ConnectionWithFlag cf : connections) {
            if (cf.connection == connection) {
                cf.freeConnection();
                return;
            }
        }
        connection = null;
    }

    public static void closeAllConnections() throws SQLException {
        for (ConnectionWithFlag cf : connections) {
            cf.connection.close();
            cf.connection = null;
        }
        connections.clear();
    }

    public static String getCurDir() {
        File curdir = new File("./");
        return curdir.getAbsolutePath();
    }

    private static Connection checkVersion(Connection connection) throws RemoteException {
        PreparedStatement ps = null;
        ResultSet rs = null;
        String err;
        String stmt = "SELECT version_id,version FROM dbversion WHERE dbversion_id=1";
        int curversion_id = 0;
        String curversion = "0.0";
        try {
            ps = connection.prepareStatement(stmt);
            rs = ps.executeQuery();
            if (rs.next()) {
                curversion_id = rs.getInt(1);
                curversion = rs.getString(2);
            }
            if (DB_VERSION_ID > curversion_id || !curversion.equals(DB_VERSION)) {
                err = "Invalid database version! " + "expected:" + DB_VERSION + "(" + DB_VERSION_ID + ") "
                        + "found:" + curversion + "(" + curversion_id + ")";
                XGrafWorks.log(err);
                throw new RemoteException(err);
            }
        } catch (SQLException ex) {
            XGrafWorks.log(ex);
            try {
                closeConnection(connection);
            } catch (SQLException ex1) {
            }
        } finally {
            try {
                if (rs != null) {
                    try {
                        rs.close();
                    } catch (SQLException ex) {
                    }
                }
            } finally {
                if (ps != null) {
                    try {
                        ps.close();
                    } catch (SQLException ex) {
                    }
                }
            }
        }
        return connection;
    }

    public static String[] loadDDLscript(String fname, String splitter) {
        String[] ans = new String[0];
        File sqlFile = new File(fname);
        boolean toclean = true;
        if (!sqlFile.exists()) {
            fname = "../sql/" + fname;
            sqlFile = new File(fname);
            toclean = false;
        }
        if (sqlFile.exists()) {
            StringBuffer contents = new StringBuffer();
            java.io.BufferedReader reader = null;
            try {
                reader = new java.io.BufferedReader(new FileReader(sqlFile));
                String line = null;
                int lineNum = 0;
                while ((line = reader.readLine()) != null) {
                    int cut = line.indexOf("--");
                    if (cut >= 0) {
                        line = line.substring(0, cut);
                    }
                    contents.append(line).append(System.getProperty("line.separator"));
                }
                ans = contents.toString().split(splitter);
            } catch (Exception e) {
                XGrafWorks.log(e);
            } finally {
                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException ie) {
                }
            }
            if (toclean) {
                sqlFile.delete();
            }
        } else {
            XGrafWorks.log("File " + fname + " not found");
        }
        return ans;
    }
}
