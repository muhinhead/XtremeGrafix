// Generated by com.nm.GridEditGenerator at Sun Sep 13 18:59:53 EEST 2015
// generated file, so all hand editions will be overwritten
package com.xgraf;

import com.xgraf.orm.Quoteitem;
import com.xgraf.remote.IMessageSender;
import java.awt.event.ActionEvent;
import java.rmi.RemoteException;
import java.util.HashMap;
import javax.swing.AbstractAction;
import javax.swing.JOptionPane;

public class QuoteitemGrid extends GeneralGridPanel {

    private static final String WHERE_COND = " where quote_id=";
    public static final String SELECT = "Select "
            + "quoteitem_id \"Id\","
            + "descr \"Description\","
            + "qty \"Qty\","
            + "concat('R',unit_price) \"Unit Price\","
            + "concat('R',qty*unit_price) \"Line Total\" "
            + "from quoteitem ";
    private static HashMap<Integer, Integer> maxWidths = new HashMap<Integer, Integer>();

    static {
        maxWidths.put(0, 40);
    }
    private final EditQuotePanel papaPanel;

    public QuoteitemGrid(IMessageSender exchanger, Integer quoteID, EditQuotePanel papaPanel) throws RemoteException {
        super(exchanger, SELECT + WHERE_COND + quoteID.toString(), maxWidths, false);
        this.papaPanel = papaPanel;
    }

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
        return new AbstractAction("Add") {
            @Override
            public void actionPerformed(ActionEvent ae) {
                EditQuoteitemDialog.quoteID = getParentQuoteID();
                EditQuoteitemDialog ed = new EditQuoteitemDialog("Add an Item", null);
                if (EditQuoteitemDialog.okPressed) {
                    Quoteitem rec = (Quoteitem) ed.getEditPanel().getDbObject();
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
                        Quoteitem quoteitem = (Quoteitem) exchanger.loadDbObjectOnID(Quoteitem.class, id);
                        EditQuoteitemDialog.quoteID = getParentQuoteID();
                        new EditQuoteitemDialog("Edit an Item", quoteitem);
                        if (EditQuoteitemDialog.okPressed) {
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
                        Quoteitem quoteitem = (Quoteitem) exchanger.loadDbObjectOnID(Quoteitem.class, id);
                        if (quoteitem != null && GeneralFrame.yesNo("Attention!", "Do you want to delete this item?") == JOptionPane.YES_OPTION) {
                            exchanger.deleteObject(quoteitem);
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
