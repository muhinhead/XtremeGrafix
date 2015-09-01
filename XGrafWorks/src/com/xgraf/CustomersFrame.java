/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xgraf;

import com.xgraf.remote.IMessageSender;
import java.awt.Component;
import java.rmi.RemoteException;
import javax.swing.JTabbedPane;

/**
 *
 * @author nick
 */
public class CustomersFrame extends GeneralFrame {

    private GeneralGridPanel companiesPanel;
    private GeneralGridPanel contactsPanel;

    private static String[] sheetList = new String[]{
        "Companies list", "Contacts list"
    };

    public CustomersFrame(IMessageSender exchanger) {
        super("Customers & Contacts", exchanger);
    }

    @Override
    protected String[] getSheetList() {
        return sheetList;
    }

    @Override
    protected JTabbedPane buildMainPanel() {
        MyJideTabbedPane workTab = new MyJideTabbedPane();
        workTab.add(getCompaniesListPanel(), sheetList[0]);
        workTab.add(getContactsListPanel(), sheetList[1]);
        return workTab;
    }

    private Component getCompaniesListPanel() {
        if (companiesPanel == null) {
            try {
                registerGrid(companiesPanel = new CompanyGrid(getExchanger()));
            } catch (RemoteException ex) {
                XGrafWorks.logAndShowMessage(ex);
            }
        }
        return companiesPanel;
    }

    private Component getContactsListPanel() {
        if (contactsPanel == null) {
            try {
                registerGrid(contactsPanel = new ContactGrid(getExchanger()));
            } catch (RemoteException ex) {
                XGrafWorks.logAndShowMessage(ex);
            }
        }
        return contactsPanel;
    }
    
}
