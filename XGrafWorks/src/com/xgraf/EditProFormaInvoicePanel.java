/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xgraf;

import com.xgraf.orm.dbobject.DbObject;

/**
 *
 * @author nick
 */
public class EditProFormaInvoicePanel extends EditQuotePanel {

    public EditProFormaInvoicePanel(DbObject dbObject) {
        super(dbObject);
    }

    @Override
    protected Integer getIsPerform() {
        return 1;
    }
}
