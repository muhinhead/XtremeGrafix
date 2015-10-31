/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xgraf;

import com.xgraf.orm.IDocument;
import com.xgraf.orm.Invoice;
import com.xgraf.orm.Invoiceitem;
import com.xgraf.orm.dbobject.DbObject;
import com.xlend.util.PopupDialog;
import java.awt.Dimension;
import java.rmi.RemoteException;
import javax.swing.JPanel;

/**
 *
 * @author nick
 */
public class EditInvoicePanel extends EditQuotePanel {

    public EditInvoicePanel(DbObject dbObject) {
        super(dbObject);
    }
    
    @Override
    protected String docRefLabel() {
        return "Invoice ref #:";
    }
    
    @Override
    protected String validUntilLabel() {
        return "Invoices valid until:";
    }
    
    @Override
    protected String tableName() {
        return "invoice";
    }
    
    @Override
    protected JPanel getPrintPanel(PopupDialog dlg) {
        return new DocumentPrintPanel(dlg,(Invoice)getDbObject(), Invoiceitem.class);
    }
    
    @Override
    public void recalcTotal(Integer docID) {
        double total = 0.0;
        try {
            DbObject[] obs = XGrafWorks.getExchanger().getDbObjects(Invoiceitem.class, "invoice_id=" + docID, null);
            for (DbObject ob : obs) {
                Invoiceitem itm = (Invoiceitem) ob;
                total += (itm.getQty() * itm.getUnitPrice());
            }
        } catch (RemoteException ex) {
            XGrafWorks.logAndShowMessage(ex);
        }
        totalLabel.setText("R" + total);
    }
    
    @Override
    protected GeneralGridPanel getItmGrid(IDocument q) throws RemoteException {
        InvoiceitemGrid itmGrid = new InvoiceitemGrid(XGrafWorks.getExchanger(), q.getPK_ID(), this);
        itmGrid.setPreferredSize(new Dimension(itmGrid.getPreferredSize().width, 300));
        return itmGrid;
    }
}
