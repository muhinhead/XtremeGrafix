/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xgraf;

import static com.xgraf.QuoteitemGrid.SELECT;
import com.xgraf.orm.Invoiceitem;
import com.xgraf.orm.Quoteitem;
import com.xgraf.remote.IMessageSender;
import java.awt.event.ActionEvent;
import java.rmi.RemoteException;
import java.util.HashMap;
import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 *
 * @author nick
 */
class InvoiceitemGrid extends GeneralGridPanel {

    private static final String WHERE_COND = " where invoice_id=";
    public static final String SELECT = "Select "
            + "invoiceitem_id \"Id\","
            + "descr \"Description\","
            + "qty \"Qty\","
            + "concat('R',unit_price) \"Unit Price\","
            + "concat('R',qty*unit_price) \"Unit Total\", "
            + "concat('R',round(qty*unit_price*0.14,2)) \"Vat 14%\","
            + "concat('R',qty*unit_price+round(qty*unit_price*0.14,2)) \"Line Total\" "
            + "from invoiceitem ";
    private static HashMap<Integer, Integer> maxWidths = new HashMap<Integer, Integer>();

    static {
        maxWidths.put(0, 40);
    }
    private final EditInvoicePanel papaPanel;

    public InvoiceitemGrid(IMessageSender exchanger, Integer papaDocID, EditInvoicePanel papaPanel) throws RemoteException {
        super(exchanger, SELECT + WHERE_COND + papaDocID.toString(), maxWidths, false);
        this.papaPanel = papaPanel;
    }

    private Integer getParentDocID() {
        int pos = getSelect().indexOf(WHERE_COND);
        if (pos > 0) {
            return new Integer(getSelect().substring(pos + WHERE_COND.length()));
        }
        return null;
    }

    @Override
    public void refresh() {
        super.refresh();
        papaPanel.recalcTotal(getParentDocID());
    }

    @Override
    public void refresh(int id) {
        super.refresh(id);
        papaPanel.recalcTotal(getParentDocID());
    }

    @Override
    protected AbstractAction addAction() {
        return new AbstractAction("Add", new ImageIcon(XGrafWorks.loadImage("plus.png", QuoteGrid.class))) {
            @Override
            public void actionPerformed(ActionEvent ae) {
                EditInvoiceitemDialog.invoiceID = getParentDocID();
                EditInvoiceitemDialog ed = new EditInvoiceitemDialog("Add an Item", null);
                if (EditInvoiceitemDialog.okPressed) {
                    Invoiceitem rec = (Invoiceitem) ed.getEditPanel().getDbObject();
                    refresh(rec.getPK_ID());
                }
            }
        };
    }

    @Override
    protected AbstractAction editAction() {
        return new AbstractAction("Edit", new ImageIcon(XGrafWorks.loadImage("edit16.png", QuoteGrid.class))) {
            @Override
            public void actionPerformed(ActionEvent ae) {
                int id = getSelectedID();
                if (id != 0) {
                    try {
                        Invoiceitem item = (Invoiceitem) exchanger.loadDbObjectOnID(Invoiceitem.class, id);
                        EditInvoiceitemDialog.invoiceID = getParentDocID();
                        new EditInvoiceitemDialog("Edit an Item", item);
                        if (EditInvoiceitemDialog.okPressed) {
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
        return new AbstractAction("Delete", new ImageIcon(XGrafWorks.loadImage("minus.png", QuoteGrid.class))) {
            @Override
            public void actionPerformed(ActionEvent ae) {
                int id = getSelectedID();
                if (id != 0) {
                    try {
                        Invoiceitem item = (Invoiceitem) exchanger.loadDbObjectOnID(Invoiceitem.class, id);
                        if (item != null && GeneralFrame.yesNo("Attention!", "Do you want to delete this item?") == JOptionPane.YES_OPTION) {
                            exchanger.deleteObject(item);
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
