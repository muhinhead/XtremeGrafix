/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xgraf;

import com.xgraf.orm.IDocument;
import com.xgraf.orm.Invoice;
import com.xgraf.orm.Quote;
import javax.swing.JButton;
import javax.swing.JPanel;

/**
 *
 * @author nick
 */
public class EditInvoiceDialog extends EditQuoteDialog {

    public EditInvoiceDialog(String title, IDocument rec) {
        super(title, rec);
    }
    
    @Override
    protected void fillContent() {
        super.fillContent(new EditInvoicePanel((Invoice) getObject()));
    }
    
    @Override
    protected void addBeforeButtons(JPanel btnPanel) {  
        btnPanel.add(new JButton(((EditInvoicePanel)getEditPanel()).printAction()));
    }
}
