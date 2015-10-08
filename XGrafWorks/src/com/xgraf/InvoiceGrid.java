/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xgraf;

import com.xgraf.orm.Invoice;
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
public class InvoiceGrid extends GeneralGridPanel {

    private static final String SELECT = "Select "
            + "invoice_id \"Id\","
            + "invoice_date \"Date\","
            + "(select name from company where company_id=invoice.company_id) \"Customer\","
            + "invoice_ref \"Invoice Ref #\","
            + "order_no \"Order No\","
            + "sub_total \"Amount\" "
            + " from invoice ";

    private static HashMap<Integer, Integer> maxWidths = new HashMap<Integer, Integer>();

    static {
        maxWidths.put(0, 40);
        maxWidths.put(5, 200);
    }

    public InvoiceGrid(IMessageSender exchanger) throws RemoteException {
        super(exchanger, SELECT, maxWidths, false);
    }

    public InvoiceGrid(IMessageSender exchanger, int company_id) throws RemoteException {
        super(exchanger, SELECT + " where company_id=" + company_id, maxWidths, false);
    }

    public InvoiceGrid(int contact_id, IMessageSender exchanger) throws RemoteException {
        super(exchanger, SELECT + " where contact_id=" + contact_id, maxWidths, false);
    }

    public InvoiceGrid(IMessageSender exchanger, String select) throws RemoteException {
        super(exchanger, select, maxWidths, false);
    }

    @Override
    protected AbstractAction addAction() {
        if (getSelect().indexOf(" company_id=") > 0 || getSelect().indexOf(" contact_id=") > 0) {
            return null;
        } else {
            return new AbstractAction("Add", new ImageIcon(XGrafWorks.loadImage("newdocument.png", InvoiceGrid.class))) {
                @Override
                public void actionPerformed(ActionEvent ae) {
                    EditInvoiceDialog ed = new EditInvoiceDialog("Add Invoice", null);
                    if (EditInvoiceDialog.okPressed) {
                        Invoice rec = (Invoice) ed.getEditPanel().getDbObject();
                        refresh(rec.getPK_ID());
                    }
                }
            };
        }
    }

    @Override
    protected AbstractAction editAction() {
        return new AbstractAction("Edit", new ImageIcon(XGrafWorks.loadImage("edit.png", InvoiceGrid.class))) {
            @Override
            public void actionPerformed(ActionEvent ae) {
                int id = getSelectedID();
                if (id != 0) {
                    try {
                        Invoice invoice = (Invoice) exchanger.loadDbObjectOnID(Invoice.class, id);
                        new EditInvoiceDialog("Edit Tax Invoice", invoice);
                        if (EditQuoteDialog.okPressed) {
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
        return new AbstractAction("Delete", new ImageIcon(XGrafWorks.loadImage("trash.png", InvoiceGrid.class))) {
            @Override
            public void actionPerformed(ActionEvent ae) {
                int id = getSelectedID();
                if (id != 0) {
                    try {
                        Invoice invoice = (Invoice) exchanger.loadDbObjectOnID(Invoice.class, id);
                        if (invoice != null && GeneralFrame.yesNo("Attention!", "Do you want to delete this record?") == JOptionPane.YES_OPTION) {
                            exchanger.deleteObject(invoice);
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
