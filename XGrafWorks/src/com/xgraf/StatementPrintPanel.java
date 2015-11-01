/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xgraf;

import static com.xgraf.GeneralReportPanel.exchanger;
import com.xgraf.orm.Company;
import com.xgraf.orm.Contact;
import com.xgraf.orm.Statement;
import com.xgraf.remote.IMessageSender;
import com.xlend.util.PopupDialog;
import java.rmi.RemoteException;
import java.util.Calendar;
import javax.swing.JEditorPane;

/**
 *
 * @author nick
 */
public class StatementPrintPanel extends GeneralReportPanel {

    private final PopupDialog ownerDialog;
    private final Statement statement;

    public StatementPrintPanel(PopupDialog dlg, Statement statement) {
        super(exchanger, statement.getPK_ID());
        ownerDialog = dlg;
        this.statement = statement;
        updateReport();
    }

    @Override
    protected JEditorPane createEditorPanel() {
        if (html == null) {
            try {
                Company company = (Company) exchanger.loadDbObjectOnID(Company.class, statement.getCompanyId());
                Contact contact = (Contact) exchanger.loadDbObjectOnID(Contact.class, statement.getContactId());
                prevZoomerValue = zoomer.getValue();
                html = new StringBuffer("<html>"
                        + "<table border=\"0\">"
                        + "<tr><td colspan=\"2\" style=\"font-size: " + (prevZoomerValue - 10) + "%; font-family: sans-serif\" ><img margin=20 src='file:./images/logo.png'/><br>" + Calendar.getInstance().getTime().toString() + "</td>"
                        + "</tr>"
                        + "</table>"
                        + "</html>");
                
            } catch (RemoteException ex) {
                html = new StringBuffer(ex.getMessage());
            }
        } else {
            adjustCache();
        }
        editorPanel = new JEditorPane("text/html", html.toString());
        editorPanel.setEditable(false);
        return editorPanel;
    }

    @Override
    protected void closeOwner() {
        if (ownerDialog != null) {
            ownerDialog.dispose();
        }
    }
}
