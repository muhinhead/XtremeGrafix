// Generated by com.nm.GridEditGenerator at Fri Oct 30 16:44:52 EET 2015
// generated file, so all hand editions will be overwritten
package com.xgraf;

import com.xgraf.GeneralFrame;
import com.xgraf.GeneralGridPanel;
import com.xgraf.orm.Statementitem;
import com.xgraf.remote.IMessageSender;
import java.awt.event.ActionEvent;
import java.rmi.RemoteException;
import java.util.HashMap;
import javax.swing.AbstractAction;
import javax.swing.JOptionPane;

public class StatementitemGrid extends GeneralGridPanel {

    private static final String WHERE_COND = " where statement_id=";
    public static final String SELECT = "Select "
                                      + "statementitem_id,"
                                      + "statement_id,"
                                      + "stitem_date,"
                                      + "stitem_ref,"
                                      + "descry,"
                                      + "amount,"
                                      + "payment,"
                                      + "balance from statementitem where statement_id=#";
    private static HashMap<Integer, Integer> maxWidths = new HashMap<Integer, Integer>();

    static {
        maxWidths.put(0, 40);
    }

    private EditStatementPanel papaPanel;
    
    public StatementitemGrid(IMessageSender exchanger, Integer papaID, EditStatementPanel papaPanel) throws RemoteException {
        super(exchanger, SELECT.replace("#", papaID.toString()), maxWidths, false);
        this.papaPanel = papaPanel;
    }

//    public StatementitemGrid(IMessageSender exchanger, String select) throws RemoteException {
//        super(exchanger, select, maxWidths, false);
//    }
//
//    public StatementitemGrid(IMessageSender exchanger,String select, boolean readOnly) throws RemoteException {
//        super(exchanger, select, maxWidths, readOnly);
//    }

    private Integer getParentQuoteID() {
        int pos = getSelect().indexOf(WHERE_COND);
        if (pos > 0) {
            return new Integer(getSelect().substring(pos + WHERE_COND.length()));
        }
        return null;
    }
    
    @Override
    public void refresh() {
        super.refresh();
        papaPanel.recalcTotal(getParentQuoteID());
    }
    
     @Override
    public void refresh(int id) {
        super.refresh(id);
        papaPanel.recalcTotal(getParentQuoteID());
    }
    
    @Override
    protected AbstractAction addAction() {
        return new AbstractAction("Add record") {
            @Override
            public void actionPerformed(ActionEvent ae) {
                EditStatementitemDialog ed = new EditStatementitemDialog("Add Statement item", null);
                if (EditStatementitemDialog.okPressed) {
                    Statementitem rec = (Statementitem) ed.getEditPanel().getDbObject();
                    refresh(rec.getPK_ID());
                }
            }
        };
    }

    @Override
    protected AbstractAction editAction() {
        return new AbstractAction("Edit") {
            @Override
            public void actionPerformed(ActionEvent ae) {
                int id = getSelectedID();
                if (id != 0) {
                    try {
                        Statementitem statementitem = (Statementitem) exchanger.loadDbObjectOnID(Statementitem.class, id);
                        new EditStatementitemDialog("Edit record", statementitem);
                        if (EditStatementitemDialog.okPressed) {
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
        return new AbstractAction("Delete") {
            @Override
            public void actionPerformed(ActionEvent ae) {
                int id = getSelectedID();
                if (id != 0) {
                    try {
                        Statementitem statementitem = (Statementitem) exchanger.loadDbObjectOnID(Statementitem.class, id);
                        if (statementitem != null && GeneralFrame.yesNo("Attention!", "Do you want to delete this record?") == JOptionPane.YES_OPTION) {
                            exchanger.deleteObject(statementitem);
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
