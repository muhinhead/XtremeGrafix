/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xgraf;

import java.awt.event.ActionEvent;
import java.rmi.RemoteException;
import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;

/**
 *
 * @author nick
 */
class CompanyLookupAction extends AbstractAction {

    private JComboBox companyCB;

    public CompanyLookupAction(JComboBox cBox) {
        super(null,new ImageIcon(XGrafWorks.loadImage("lookup.png", QuoteGrid.class)));
        this.companyCB = cBox;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        showCompanyLookup();
    }

    private void showCompanyLookup() {
        try {
            LookupDialog ld = new LookupDialog("Companies Lookup", companyCB,
                    new CompanyGrid(XGrafWorks.getExchanger()),
                    new String[]{"name", "street", "city", "reg_no","vat_no"});
        } catch (RemoteException ex) {
            GeneralFrame.errMessageBox("Error:", ex.getMessage());
        }
    }
    
}
