/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xgraf;

import com.xgraf.orm.Quote;

/**
 *
 * @author nick
 */
public class EditProFormaInvoiceDialog extends EditQuoteDialog {

    public EditProFormaInvoiceDialog(String title, Quote rec) {
        super(title, rec);
    }
    
    @Override
    protected void fillContent() {
        super.fillContent(new EditProFormaInvoicePanel((Quote) getObject()));
    }
}
