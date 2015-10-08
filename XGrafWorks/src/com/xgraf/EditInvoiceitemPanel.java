/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xgraf;

import com.xgraf.orm.IDocumentItem;
import com.xgraf.orm.Invoiceitem;
import com.xgraf.orm.dbobject.DbObject;

/**
 *
 * @author nick
 */
public class EditInvoiceitemPanel extends EditQuoteitemPanel {

    public EditInvoiceitemPanel(DbObject dbObject) {
        super(dbObject);
    }
    
    @Override
    protected Integer findParentDocID() {
        return EditInvoiceitemDialog.invoiceID;
    }
    
    @Override
        protected IDocumentItem createItm() {
        return new Invoiceitem(null);
    }

}
