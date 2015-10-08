/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xgraf;

import com.xgraf.orm.Invoiceitem;

/**
 *
 * @author nick
 */
class EditInvoiceitemDialog extends EditRecordDialog {

    public static Integer invoiceID = null;
    public static boolean okPressed;
    public EditInvoiceitemDialog(String title, Invoiceitem rec) {
        super(title, rec);
    }

    @Override
    protected void fillContent() {
        super.fillContent(new EditInvoiceitemPanel((Invoiceitem) getObject()));
    }

    @Override
    protected void setOkPressed(boolean b) {
        okPressed = b;
    }
}
