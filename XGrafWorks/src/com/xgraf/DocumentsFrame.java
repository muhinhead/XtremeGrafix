/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xgraf;

import com.xgraf.remote.IMessageSender;
import java.awt.Component;
import java.rmi.RemoteException;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

/**
 *
 * @author nick
 */
public class DocumentsFrame extends GeneralFrame {

    private static String[] sheetList = new String[]{
        "Quotes", "Pro-Forma Invoices", "Tax Invoices", "Statement of Accounts", "Credit Notes"
    };
    private QuoteGrid quotesPanel;
    private ProFormaInvoiceGrid proFormaInvoicePanel;
    private InvoiceGrid invoicesPanel;

    public DocumentsFrame(IMessageSender exchanger) {
        super("Customers & Contacts", exchanger);
    }

    @Override
    protected String[] getSheetList() {
        return sheetList;
    }

    @Override
    protected JTabbedPane buildMainPanel() {
        MyJideTabbedPane workTab = new MyJideTabbedPane();
        workTab.add(getQuotesPanel(), sheetList[0]);
        workTab.add(getProformaInvoicesPanel(), sheetList[1]);
        workTab.add(getInvoicesPanel(), sheetList[2]);
        workTab.add(getStatementsAccountsPanel(), sheetList[3]);
        workTab.add(getCreditNotesPanel(), sheetList[4]);
        return workTab;
    }

    private Component getQuotesPanel() {
        if (quotesPanel == null) {
            try {
                registerGrid(quotesPanel = new QuoteGrid(getExchanger()));
            } catch (RemoteException ex) {
                XGrafWorks.logAndShowMessage(ex);
            }
        }
        return quotesPanel;
    }

    private Component getProformaInvoicesPanel() {
        if(proFormaInvoicePanel==null) {
            try {
                proFormaInvoicePanel = new ProFormaInvoiceGrid(getExchanger());
            } catch (RemoteException ex) {
                XGrafWorks.logAndShowMessage(ex);
            }
        }
        return proFormaInvoicePanel;
    }

    private Component getInvoicesPanel() {
        if (invoicesPanel == null) {
            try {
                registerGrid(invoicesPanel = new InvoiceGrid(getExchanger()));
            } catch (RemoteException ex) {
                XGrafWorks.logAndShowMessage(ex);
            }
        }
        return invoicesPanel;
    }

    private Component getStatementsAccountsPanel() {
        return new JPanel(); 
    }

    private Component getCreditNotesPanel() {
        return new JPanel();
    }

}
