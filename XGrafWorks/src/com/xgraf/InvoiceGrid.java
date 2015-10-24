/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xgraf;

import com.xgraf.orm.Invoice;
import com.xgraf.orm.Quote;
import com.xgraf.remote.IMessageSender;
import com.xlend.mvc.dbtable.DbTableView;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.AbstractAction;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 *
 * @author nick
 */
public class InvoiceGrid extends GeneralGridPanel {

    private Invoice curInvoice = null;
    private static final String SELECT = "Select "
            + "invoice_id \"Id\","
            + "invoice_date \"Date\","
            + "(select name from company where company_id=invoice.company_id) \"Customer\","
            + "invoice_ref \"Invoice Ref #\","
            + "order_no \"Order No\","
            + "sub_total \"Amount\" "
            + " from invoice";

    private static HashMap<Integer, Integer> maxWidths = new HashMap<Integer, Integer>();

    static {
        maxWidths.put(0, 40);
        maxWidths.put(5, 200);
    }
    private JButton quoteButton;

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
    protected JPanel getRightPanel(JPanel btnPanel) {
        btnPanel.setLayout(new GridLayout(4, 1, 5, 5));
        btnPanel.add(quoteButton = new JButton(showParentQuoteAction()));
        quoteButton.setToolTipText("Show the initial quote");
        final DbTableView table = getTableView();
        ListSelectionModel cellSelectionModel = table.getSelectionModel();
        cellSelectionModel.addListSelectionListener(new ListSelectionListener() {

            @Override
            public void valueChanged(ListSelectionEvent e) {
                int[] selectedRow = table.getSelectedRows();
                if (selectedRow.length > 0) {
                    Object[] content = (Object[]) getTableDoc().getBody();
                    Vector rowData = (Vector) content[1];
                    Vector line = (Vector) rowData.get(selectedRow[0]);
                    int invoice_id = Integer.parseInt((String) line.get(0));
                    try {
                        curInvoice = (Invoice) exchanger.loadDbObjectOnID(Invoice.class, invoice_id);
                        quoteButton.setEnabled(curInvoice.getQuoteId() != null);
                    } catch (RemoteException ex) {
                        XGrafWorks.logAndShowMessage(ex);
                    }
                }
            }

        });
        return super.getRightPanel(btnPanel);
    }

    @Override
    protected AbstractAction addAction() {
        if (getSelect().indexOf("from invoice where company_id=") > 0 || getSelect().indexOf("from invoice where contact_id=") > 0) {
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

    private AbstractAction showParentQuoteAction() {
        return new AbstractAction("Initial Quote", new ImageIcon(XGrafWorks.loadImage("duplicate.png", getClass()))) {

            @Override
            public void actionPerformed(ActionEvent e) {
                if (curInvoice != null) {
                    try {
                        Quote quote = (Quote) exchanger.loadDbObjectOnID(Quote.class, curInvoice.getQuoteId());
                        if (quote.getIsProforma() != null && quote.getIsProforma() == 1) {
                            new EditProFormaInvoiceDialog("Edit Pro-Forma Invoice", quote);
                        } else {
                            new EditQuoteDialog("Edit Quote", quote);
                        }
                        if(EditQuoteDialog.okPressed) {
                            
                        }
                    } catch (RemoteException ex) {
                        XGrafWorks.logAndShowMessage(ex);
                    }
                }
            }

        };
    }
}
