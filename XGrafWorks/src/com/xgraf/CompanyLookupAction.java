/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xgraf;

import java.awt.event.ActionEvent;
import java.rmi.RemoteException;
import javax.swing.AbstractAction;
import javax.swing.JComboBox;

/**
 *
 * @author nick
 */
class CompanyLookupAction extends AbstractAction {

    private JComboBox supplierCB;

    public CompanyLookupAction(JComboBox cBox) {
        super("...");
        this.supplierCB = cBox;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        showSupplierLookup();
    }

    private void showSupplierLookup() {
        try {
            LookupDialog ld = new LookupDialog("Companies Lookup", supplierCB,
                    new CompanyGrid(XGrafWorks.getExchanger()),
                    new String[]{"name", "street", "city", "reg_no","vat_no"});
        } catch (RemoteException ex) {
            GeneralFrame.errMessageBox("Error:", ex.getMessage());
        }
    }
    
}
