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
import com.xgraf.orm.Statementitem;
import com.xgraf.orm.dbobject.DbObject;
import com.xlend.util.PopupDialog;
import java.rmi.RemoteException;
import java.text.DecimalFormat;
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
        super(XGrafWorks.getExchanger(), statement.getPK_ID());
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
                        + "<tr>"
                        + "<table>" //Billing Information
                        + "<tr>"
                        + "<th colspan=\"2\" style=\"font-size: " + Math.round(prevZoomerValue + 0.5) + "%; font-family: sans-serif\" allign=\"left\">Billing Information</th>"
                        + "<th colspan=\"2\" style=\"font-size: " + Math.round(prevZoomerValue + 0.5)
                        + "%; font-family: sans-serif\" allign=\"left\">STATEMENT OF ACCOUNTS</th>"
                        + "</tr>"
                        + "<tr>"
                        + "<td style=\"font-size: " + Math.round(prevZoomerValue + 0.5) + "%; font-family: sans-serif\">Billing Contact:</td>"
                        + "<td style=\"font-size: " + Math.round(prevZoomerValue + 0.5) + "%; font-family: sans-serif\">" + company.getName()
                        + "</td>"
                        + "<td align=\"center\" colspan=\"2\" style=\"font-size: " + Math.round(prevZoomerValue) + "%; font-family: sans-serif\">VERKLARING VAN REKENINGE</td>"
                        + "</tr>"
                        + "<tr>"
                        + "<td style=\"font-size: " + Math.round(prevZoomerValue + 0.5) + "%; font-family: sans-serif\" >Billing Address:</td>"
                        + "<td style=\"font-size: " + Math.round(prevZoomerValue + 0.5) + "%; font-family: sans-serif\">" + company.getStreet() + " " + company.getAreaPobox() + " " + company.getCity()
                        + "</td>"
                        + "<td colspan=\"2\" rowspan=\"5\">"
                        + "<table border=\"0\">"
                        + "<tr>"
                        + "<td style=\"font-size: " + Math.round(prevZoomerValue + 0.5) + "%; font-family: sans-serif\" >Statement ref #</td>"
                        + "<td style=\"font-size: " + Math.round(prevZoomerValue + 0.5) + "%; font-family: sans-serif\">" + statement.getStatementRef()
                        + "</td>"
                        + "</tr>"
                        + "<tr>"
//                        + "<td style=\"font-size: " + Math.round(prevZoomerValue + 0.5) + "%; font-family: sans-serif\" >Order No</td>"
//                        + "<td style=\"font-size: " + Math.round(prevZoomerValue + 0.5) + "%; font-family: sans-serif\">" + statement.get
//                        + "</td>"
                        + "</tr>"
                        + "<tr>"
                        + "<td style=\"font-size: " + Math.round(prevZoomerValue + 0.5) + "%; font-family: sans-serif\" >Date</td>"
                        + "<td style=\"font-size: " + Math.round(prevZoomerValue + 0.5) + "%; font-family: sans-serif\">" + statement.getStatementDate().toString()
                        + "</td>"
                        + "</tr>"
                        + "</table border=\"0\">"
                        + "</td>"
                        + "</tr>"
                        + "<tr>"
                        + "<td style=\"font-size: " + Math.round(prevZoomerValue + 0.5) + "%; font-family: sans-serif\" >Contact Person:</td>"
                        + "<td style=\"font-size: " + Math.round(prevZoomerValue + 0.5) + "%; font-family: sans-serif\">" + contact.getName()
                        + "</td>"
                        + "</tr>"
                        + "<tr>"
                        + "<td style=\"font-size: " + Math.round(prevZoomerValue + 0.5) + "%; font-family: sans-serif\" >Contact Number:</td>"
                        + "<td style=\"font-size: " + Math.round(prevZoomerValue + 0.5) + "%; font-family: sans-serif\">" + contact.getPhone()
                        + "</td>"
                        + "</tr>"
                        + "<tr>"
                        + "<td style=\"font-size: " + Math.round(prevZoomerValue + 0.5) + "%; font-family: sans-serif\" >Contact E-mail:</td>"
                        + "<td style=\"font-size: " + Math.round(prevZoomerValue + 0.5) + "%; font-family: sans-serif\">" + contact.getEmail() + (contact.getEmail1() != null ? " " + contact.getEmail1() : "")
                        + "</td>"
                        + "</tr>"
                        + "<tr>"
//                        + "<td style=\"font-size: " + Math.round(prevZoomerValue + 0.5) + "%; font-family: sans-serif\" >Service Type:</td>"
//                        + "<td style=\"font-size: " + Math.round(prevZoomerValue + 0.5) + "%; font-family: sans-serif\">" + doc.getServiceType()
//                        + "</td>"
                        + "</tr>"
                        + "<tr><td colspan=6><hr><td></tr>"
                        + "<tr>"
                        + "<th style=\"font-size: " + Math.round(prevZoomerValue + 0.5) + "%; font-family: sans-serif\" align=\"left\" width=\"10%\">Date</th>"
                        + "<th style=\"font-size: " + Math.round(prevZoomerValue + 0.5) + "%; font-family: sans-serif\" align=\"left\" width=\"10%\">Ref #</th>"
                        + "<th style=\"font-size: " + Math.round(prevZoomerValue + 0.5) + "%; font-family: sans-serif\" align=\"left\" width=\"30%\">Description</th>"
                        + "<th style=\"font-size: " + Math.round(prevZoomerValue + 0.5) + "%; font-family: sans-serif\" align=\"right\" >Amount</th>"
                        + "<th style=\"font-size: " + Math.round(prevZoomerValue + 0.5) + "%; font-family: sans-serif\" align=\"right\" >Payment</th>"
                        + "<th style=\"font-size: " + Math.round(prevZoomerValue + 0.5) + "%; font-family: sans-serif\" align=\"right\" >Balance</th>"
                        + showItemList()
                        + "<tr><td colspan=6><hr><td></tr>"
//                        + "<tr><th style=\"font-size: " + Math.round(prevZoomerValue + 0.5) + "%; font-family: sans-serif\"  align=\"left\">Signature:__________________</th>"
//                        + "<th style=\"font-size: " + Math.round(prevZoomerValue + 0.5) + "%; font-family: sans-serif\" >Date:__________________</th></tr>"
//                        + "<tr/>"
                        + "<tr><th style=\"font-size: " + Math.round(prevZoomerValue + 0.5) + "%; font-family: sans-serif\" align=\"left\" >Banking Details</th>"
                        + "<th/><th/>"
                        + "<th colspan=\"2\" style=\"font-size: " + Math.round(prevZoomerValue + 0.5) + "%; font-family: sans-serif\" align=\"left\" >Outstanding Account Balance:</th>"
                        + "<td style=\"font-size: " + Math.round(prevZoomerValue + 0.5) + "%; font-family: sans-serif\" align=\"left\" >R"+statement.getOutstandingBalance()+"</td>"
                        + "</tr>"
                        + "<tr><td align=\"left\" width=\"40%\">"
                        + "<table>"
                        + "<tr>"
                        + "<td style=\"font-size: " + Math.round(prevZoomerValue + 0.5) + "%; font-family: sans-serif\" align=\"left\" >Acc Holder:</td>"
                        + "<td style=\"font-size: " + Math.round(prevZoomerValue + 0.5) + "%; font-family: sans-serif\" align=\"left\" >"+statement.getBankAccHolder()+"</td>"
                        + "</tr>"
                        + "<tr>"
                        + "<td style=\"font-size: " + Math.round(prevZoomerValue + 0.5) + "%; font-family: sans-serif\" align=\"left\" >Bank:</td>"
                        + "<td style=\"font-size: " + Math.round(prevZoomerValue + 0.5) + "%; font-family: sans-serif\" align=\"left\" >"+statement.getBank()+"</td>"
                        + "</tr>"
                        + "<tr>"
                        + "<td style=\"font-size: " + Math.round(prevZoomerValue + 0.5) + "%; font-family: sans-serif\" align=\"left\" >Branch Code:</td>"
                        + "<td style=\"font-size: " + Math.round(prevZoomerValue + 0.5) + "%; font-family: sans-serif\" align=\"left\" >"+statement.getBankBranchCode()+"</td>"
                        + "</tr>"
                        + "<tr>"
                        + "<td style=\"font-size: " + Math.round(prevZoomerValue + 0.5) + "%; font-family: sans-serif\" align=\"left\" >Account No:</td>"
                        + "<td style=\"font-size: " + Math.round(prevZoomerValue + 0.5) + "%; font-family: sans-serif\" align=\"left\" >"+statement.getBankAccNo()+"</td>"
                        + "</tr>"
                        + "<tr>"
                        + "<td style=\"font-size: " + Math.round(prevZoomerValue + 0.5) + "%; font-family: sans-serif\" align=\"left\" >Account Type:</td>"
                        + "<td style=\"font-size: " + Math.round(prevZoomerValue + 0.5) + "%; font-family: sans-serif\" align=\"left\" >"+statement.getBankAccType()+"</td>"
                        + "</tr>"
                        + "</td>"
                        + "</table></tr>"
                        + "</table></td></tr>"
                        + "</tr><th style=\"font-size: " + Math.round(prevZoomerValue + 0.5) + "%; font-family: sans-serif\" align=\"left\">Terms and Conditions</th></tr>"
                        + "</tr><td style=\"font-size: " + Math.round(prevZoomerValue + 0.5) 
                        + "%; font-family: sans-serif\" align=\"left\">Important: Please make use of statement ref # when making payments </td></tr>"
                        + "</tr><td style=\"font-size: " + Math.round(prevZoomerValue + 0.5) + "%; font-family: sans-serif\" align=\"left\">Payment of outstanding balance is due within "+statement.getPaymentDue()+" days.</td></tr>"
                        + "</tr><td style=\"font-size: " + Math.round(prevZoomerValue + 0.5) + "%; font-family: sans-serif\" align=\"left\">All goods shall remain the property of X-treme Grafi-X cc until paid for in full.</td></tr>"
                        + "</tr><td style=\"font-size: " + Math.round(prevZoomerValue + 0.5) + "%; font-family: sans-serif\" align=\"left\">X-treme Frafi-X reserves the right to reposess or remove any products not paid for in full.</td></tr>"
                        + "</table>"
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

    private String showItemList() {
        StringBuilder lst = new StringBuilder();
        try {
            double sum = 0.0;
            DecimalFormat myFormatter = new DecimalFormat("R##0.00");
            DbObject[] itms = exchanger.getDbObjects(Statementitem.class, "statement_id=" + intParameter, null);
            lst.append("<tr><td colspan=6><hr><td></tr>");
            for (DbObject obj : itms) {
                Statementitem itm = (Statementitem) obj;
                lst.append("<tr>")
                        .append("<td style=\"font-size: ").append(Math.round(prevZoomerValue + 0.5))
                        .append("%; font-family: sans-serif\" >").append(itm.getStitemDate()).append("</td>")
                        .append("<td style=\"font-size: ").append(Math.round(prevZoomerValue + 0.5))
                        .append("%; font-family: sans-serif\" >").append(itm.getStitemRef()).append("</td>")
                        .append("<td style=\"font-size: ").append(Math.round(prevZoomerValue + 0.5))
                        .append("%; font-family: sans-serif\" >").append(itm.getDescr()).append("</td>")
                        .append("<td style=\"font-size: ").append(Math.round(prevZoomerValue + 0.5))
                        .append("%; font-family: sans-serif\" align=\"right\">").append(myFormatter.format(itm.getAmount())).append("</td>")
                        .append("<td style=\"font-size: ").append(Math.round(prevZoomerValue + 0.5))
                        .append("%; font-family: sans-serif\" align=\"right\">").append(myFormatter.format(itm.getPayment())).append("</td>")
                        .append("<td style=\"font-size: ").append(Math.round(prevZoomerValue + 0.5))
                        .append("%; font-family: sans-serif\" align=\"right\">").append(myFormatter.format(itm.getBalance())).append("</td>")
                        .append("</tr>");
            }
        } catch (RemoteException ex) {
            XGrafWorks.logAndShowMessage(ex);
            lst.append("<tr><td colspan=\"4\">").append(ex.getMessage()).append("</td></tr>");
        }
        return lst.toString();
    }
}
