/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xgraf;

import com.xgraf.orm.dbobject.ComboItem;
import java.awt.event.ActionEvent;
import java.rmi.RemoteException;
import javax.swing.AbstractAction;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;

/**
 *
 * @author nick
 */
class CompanyLookupAction extends AbstractAction {

    private JComboBox companyCB;
    private DefaultComboBoxModel dependentContacts;
    
    public CompanyLookupAction(JComboBox cBox,DefaultComboBoxModel dependentContacts) {
        super(null,new ImageIcon(XGrafWorks.loadImage("lookup.png", CompanyLookupAction.class)));
        this.companyCB = cBox;
        this.dependentContacts = dependentContacts;
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
            if(dependentContacts!=null) {
                dependentContacts.removeAllElements();
                for (ComboItem ci : XGrafWorks.loadContactsOnCompany(ld.getChoosed())) {
                    dependentContacts.addElement(ci);
                }
            }
        } catch (RemoteException ex) {
            GeneralFrame.errMessageBox("Error:", ex.getMessage());
        }
    }
    
}
