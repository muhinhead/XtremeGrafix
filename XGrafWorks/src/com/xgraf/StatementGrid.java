// Generated by com.nm.GridEditGenerator at Thu Oct 29 08:45:37 EET 2015
// generated file, so all hand editions will be overwritten
package com.xgraf;

import com.xgraf.orm.Statement;
import com.xgraf.remote.IMessageSender;
import java.awt.event.ActionEvent;
import java.rmi.RemoteException;
import java.util.HashMap;
import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

public class StatementGrid extends GeneralGridPanel {

    public static final String SELECT = "Select "
                                      + "statement_id \"Id\","
                                      + "statement_date \"Date\","
                                      + "(select name from company where company_id=statement.company_id) \"Customer\","
                                      + "statement_ref \"Statement Ref\","
                                      + "outstanding_balance \"Outstanding Balance\" from statement";
    private static HashMap<Integer, Integer> maxWidths = new HashMap<Integer, Integer>();

    static {
        maxWidths.put(0, 40);
    }

    public StatementGrid(IMessageSender exchanger) throws RemoteException {
        super(exchanger, SELECT, maxWidths, false);
    }

//    public StatementGrid(IMessageSender exchanger, String select) throws RemoteException {
//        super(exchanger, select, maxWidths, false);
//    }
//
//    public StatementGrid(IMessageSender exchanger,String select, boolean readOnly) throws RemoteException {
//        super(exchanger, select, maxWidths, readOnly);
//    }

    @Override
    protected AbstractAction addAction() {
        return new AbstractAction("Add record", new ImageIcon(XGrafWorks.loadImage("newdocument.png", StatementGrid.class))) {
            @Override
            public void actionPerformed(ActionEvent ae) {
                EditStatementDialog ed = new EditStatementDialog("Add Statement", null);
                if (EditStatementDialog.okPressed) {
                    Statement rec = (Statement) ed.getEditPanel().getDbObject();
                    refresh(rec.getPK_ID());
                }
            }
        };
    }

    @Override
    protected AbstractAction editAction() {
        return new AbstractAction("Edit", new ImageIcon(XGrafWorks.loadImage("edit.png", StatementGrid.class))) {
            @Override
            public void actionPerformed(ActionEvent ae) {
                int id = getSelectedID();
                if (id != 0) {
                    try {
                        Statement statement = (Statement) exchanger.loadDbObjectOnID(Statement.class, id);
                        new EditStatementDialog("Edit record", statement);
                        if (EditStatementDialog.okPressed) {
                            refresh();
                        }
                    } catch (RemoteException ex) {
                        XGrafWorks.logAndShowMessage(ex);
                    }
                }
            }
        };
    }

    @Override
    protected AbstractAction delAction() {
        return new AbstractAction("Delete", new ImageIcon(XGrafWorks.loadImage("trash.png", StatementGrid.class))) {
            @Override
            public void actionPerformed(ActionEvent ae) {
                int id = getSelectedID();
                if (id != 0) {
                    try {
                        Statement statement = (Statement) exchanger.loadDbObjectOnID(Statement.class, id);
                        if (statement != null && GeneralFrame.yesNo("Attention!", "Do you want to delete this record?") == JOptionPane.YES_OPTION) {
                            exchanger.deleteObject(statement);
                            refresh();
                        }
                    } catch (RemoteException ex) {
                        XGrafWorks.logAndShowMessage(ex);
                    }
                }
            }
        };
    }
}

