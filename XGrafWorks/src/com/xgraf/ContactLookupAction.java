/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xgraf;

import com.xgraf.orm.Company;
import com.xgraf.orm.Contact;
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
public class ContactLookupAction extends AbstractAction {

    private JComboBox contactCB;
    private JComboBox companyCB;
    
    public ContactLookupAction(JComboBox cBox, JComboBox companyCB) {
        super(null,new ImageIcon(XGrafWorks.loadImage("lookup.png", CompanyLookupAction.class)));
        this.contactCB = cBox;
        this.companyCB = companyCB;
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        showContactLookup();
    }
    
    private void showContactLookup() {
        try {
            LookupDialog ld = new LookupDialog("Contacts Lookup", contactCB,
                    new ContactGrid(XGrafWorks.getExchanger()),
                    new String[]{"name"});
            if (companyCB != null) {
                Integer contactID = ld.getChoosed();
                Contact contact = (Contact) XGrafWorks.getExchanger().loadDbObjectOnID(Contact.class, contactID.intValue());
                if (contact != null) {
                    RecordEditPanel.selectComboItem(companyCB, contact.getCompanyId());
                }
            }
        } catch (RemoteException ex) {
            GeneralFrame.errMessageBox("Error:", ex.getMessage());
        }
    }
}
