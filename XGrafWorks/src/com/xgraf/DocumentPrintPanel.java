/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xgraf;

import com.xgraf.orm.Company;
import com.xgraf.orm.Contact;
import com.xgraf.orm.IDocument;
import com.xgraf.orm.IDocumentItem;
import com.xgraf.orm.Quote;
import com.xgraf.orm.Quoteitem;
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
class DocumentPrintPanel extends GeneralReportPanel {

    private static final double VAT = 0.14;
    private static final double WITHVAT = 1.14;
    private final PopupDialog ownerDialog;
    private final Class docClass;
    private final Class docItemClass;
    private final IDocument document;

    public DocumentPrintPanel(PopupDialog dlg, IDocument doc, Class docItemClass) {
        super(XGrafWorks.getExchanger(), doc.getPK_ID());
        ownerDialog = dlg;
        document = doc;
        docClass = doc.getClass();
        this.docItemClass = docItemClass;
        updateReport();
    }

    @Override
    protected JEditorPane createEditorPanel() {
        if (html == null) {
            try {
                IDocument doc = (IDocument) exchanger.loadDbObjectOnID(docClass, intParameter);
                Company company = (Company) exchanger.loadDbObjectOnID(Company.class, doc.getCompanyId());
                Contact contact = (Contact) exchanger.loadDbObjectOnID(Contact.class, doc.getContactId());
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
                        + "%; font-family: sans-serif\" allign=\"left\">"
                        + (docClass == Quote.class ? (null == document.getIsProforma() || document.getIsProforma() == 0 ? "QUOTE" : "PRO-FORMA INVOICE") : "INVOICE") + "</th>"
                        + "</tr>"
                        + "<tr>"
                        + "<td style=\"font-size: " + Math.round(prevZoomerValue + 0.5) + "%; font-family: sans-serif\">Billing Contact:</td>"
                        + "<td style=\"font-size: " + Math.round(prevZoomerValue + 0.5) + "%; font-family: sans-serif\">" + company.getName()
                        + "</td>"
                        + "<td align=\"center\" colspan=\"2\" style=\"font-size: " + Math.round(prevZoomerValue) + "%; font-family: sans-serif\">" + (docClass == Quote.class ? "KWOTASIE" : "FACTUUR") + "</td>"
                        + "</tr>"
                        + "<tr>"
                        + "<td style=\"font-size: " + Math.round(prevZoomerValue + 0.5) + "%; font-family: sans-serif\" >Billing Address:</td>"
                        + "<td style=\"font-size: " + Math.round(prevZoomerValue + 0.5) + "%; font-family: sans-serif\">" + company.getStreet() + " " + company.getAreaPobox() + " " + company.getCity()
                        + "</td>"
                        + "<td colspan=\"2\" rowspan=\"5\">"
                        + "<table border=\"0\">"
                        + "<tr>"
                        + "<td style=\"font-size: " + Math.round(prevZoomerValue + 0.5) + "%; font-family: sans-serif\" >"
                        + (docClass == Quote.class ? "Quote" : "Invoice") + " ref #</td>"
                        + "<td style=\"font-size: " + Math.round(prevZoomerValue + 0.5) + "%; font-family: sans-serif\">" + doc.getDocRef()
                        + "</td>"
                        + "</tr>"
                        + "<tr>"
                        + "<td style=\"font-size: " + Math.round(prevZoomerValue + 0.5) + "%; font-family: sans-serif\" >Order No</td>"
                        + "<td style=\"font-size: " + Math.round(prevZoomerValue + 0.5) + "%; font-family: sans-serif\">" + doc.getOrderNo()
                        + "</td>"
                        + "</tr>"
                        + "<tr>"
                        + "<td style=\"font-size: " + Math.round(prevZoomerValue + 0.5) + "%; font-family: sans-serif\" >Date</td>"
                        + "<td style=\"font-size: " + Math.round(prevZoomerValue + 0.5) + "%; font-family: sans-serif\">" + doc.getDocDate().toString()
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
                        + "<td style=\"font-size: " + Math.round(prevZoomerValue + 0.5) + "%; font-family: sans-serif\" >Service Type:</td>"
                        + "<td style=\"font-size: " + Math.round(prevZoomerValue + 0.5) + "%; font-family: sans-serif\">" + doc.getServiceType()
                        + "</td>"
                        + "</tr>"
                        + "<tr><td colspan=6><hr><td></tr>"
                        + "<tr>"
                        + "<th style=\"font-size: " + Math.round(prevZoomerValue + 0.5) + "%; font-family: sans-serif\" align=\"left\" width=\"30%\">Description</th>"
                        + "<th style=\"font-size: " + Math.round(prevZoomerValue + 0.5) + "%; font-family: sans-serif\" align=\"right\" >Qty</th>"
                        + "<th style=\"font-size: " + Math.round(prevZoomerValue + 0.5) + "%; font-family: sans-serif\" align=\"right\" >Unit Price</th>"
                        + "<th style=\"font-size: " + Math.round(prevZoomerValue + 0.5) + "%; font-family: sans-serif\" align=\"right\" >Unit Total</th>"
                        + "<th style=\"font-size: " + Math.round(prevZoomerValue + 0.5) + "%; font-family: sans-serif\" align=\"right\" >Vat("+VAT+"%)</th>"
                        + "<th style=\"font-size: " + Math.round(prevZoomerValue + 0.5) + "%; font-family: sans-serif\" align=\"right\" >Line Total</th>"
                        + showItemList()
                        + "<tr><th style=\"font-size: " + Math.round(prevZoomerValue + 0.5) + "%; font-family: sans-serif\"  align=\"left\">Signature:__________________</th>"
                        + "<th style=\"font-size: " + Math.round(prevZoomerValue + 0.5) + "%; font-family: sans-serif\" >Date:__________________</th></tr>"
                        + "<tr/>"
                        + "<tr><th style=\"font-size: " + Math.round(prevZoomerValue + 0.5) + "%; font-family: sans-serif\" align=\"left\" >Banking Details</th></tr>"
                        + "<tr><td align=\"left\" width=\"40%\"><table>"
                        + "<tr>"
                        + "<td style=\"font-size: " + Math.round(prevZoomerValue + 0.5) + "%; font-family: sans-serif\" align=\"left\" >Acc Holder:</td>"
                        + "<td style=\"font-size: " + Math.round(prevZoomerValue + 0.5) + "%; font-family: sans-serif\" align=\"left\" >"+doc.getBankAccHolder()+"</td>"
                        + "</tr>"
                        + "<tr>"
                        + "<td style=\"font-size: " + Math.round(prevZoomerValue + 0.5) + "%; font-family: sans-serif\" align=\"left\" >Bank:</td>"
                        + "<td style=\"font-size: " + Math.round(prevZoomerValue + 0.5) + "%; font-family: sans-serif\" align=\"left\" >"+doc.getBank()+"</td>"
                        + "</tr>"
                        + "<tr>"
                        + "<td style=\"font-size: " + Math.round(prevZoomerValue + 0.5) + "%; font-family: sans-serif\" align=\"left\" >Branch Code:</td>"
                        + "<td style=\"font-size: " + Math.round(prevZoomerValue + 0.5) + "%; font-family: sans-serif\" align=\"left\" >"+doc.getBankBranchCode()+"</td>"
                        + "</tr>"
                        + "<tr>"
                        + "<td style=\"font-size: " + Math.round(prevZoomerValue + 0.5) + "%; font-family: sans-serif\" align=\"left\" >Account No:</td>"
                        + "<td style=\"font-size: " + Math.round(prevZoomerValue + 0.5) + "%; font-family: sans-serif\" align=\"left\" >"+doc.getBankAccNo()+"</td>"
                        + "</tr>"
                        + "<tr>"
                        + "<td style=\"font-size: " + Math.round(prevZoomerValue + 0.5) + "%; font-family: sans-serif\" align=\"left\" >Account Type:</td>"
                        + "<td style=\"font-size: " + Math.round(prevZoomerValue + 0.5) + "%; font-family: sans-serif\" align=\"left\" >"+doc.getBankAccType()+"</td>"
                        + "</tr>"
                        + "</td></table></tr>"
                        + "</table></td></tr>"
                        + "</tr><th style=\"font-size: " + Math.round(prevZoomerValue + 0.5) + "%; font-family: sans-serif\" align=\"left\">Terms and Conditions</th></tr>"
                        + "</tr><td style=\"font-size: " + Math.round(prevZoomerValue + 0.5) 
                        + "%; font-family: sans-serif\" align=\"left\">Important: Please make use of "+(docClass == Quote.class ? (null == document.getIsProforma() || document.getIsProforma() == 0 ? "quote" : "pro-forma invoice") : "invoice")+" ref # when making payments </td></tr>"
                        + "</tr><td style=\"font-size: " + Math.round(prevZoomerValue + 0.5) + "%; font-family: sans-serif\" align=\"left\">Full payment confirms your order. Manyfactoring will commence after funds have cleared.</td></tr>"
                        + "</tr><td style=\"font-size: " + Math.round(prevZoomerValue + 0.5) + "%; font-family: sans-serif\" align=\"left\">Preferred method of payment is "+document.getPrefPayMethod()+".  We regret, no cheques will be accepted.</td></tr>"
                        + "</tr><td style=\"font-size: " + Math.round(prevZoomerValue + 0.5) + "%; font-family: sans-serif\" align=\"left\">All goods shall remain the property of X-treme Grafi-X cc until paid for in full.</td></tr>"
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

    private String showItemList() {
        StringBuilder lst = new StringBuilder();
        try {
            double sum = 0.0;
            DecimalFormat myFormatter = new DecimalFormat("R##0.00");
            DbObject[] itms = exchanger.getDbObjects(docItemClass, (docClass == Quote.class ? "quote_id=" : "invoice_id=") + intParameter, null);
            lst.append("<tr><td colspan=6><hr><td></tr>");
            for (DbObject obj : itms) {
                IDocumentItem itm = (IDocumentItem) obj;
                lst.append("<tr>").append("<td style=\"font-size: ").append(Math.round(prevZoomerValue + 0.5))
                        .append("%; font-family: sans-serif\" >").append(itm.getDescr()).append("</td>").append("<td align=\"right\" style=\"font-size: ").append(Math.round(prevZoomerValue + 0.5))
                        .append("%; font-family: sans-serif\" >")
                        .append(itm.getQty()).append("</td>")
                        .append("<td align=\"right\" style=\"font-size: ").append(Math.round(prevZoomerValue + 0.5))
                        .append("%; font-family: sans-serif\" >")
                        .append(myFormatter.format(itm.getUnitPrice())).append("</td>")
                        .append("<td align=\"right\" style=\"font-size: ").append(Math.round(prevZoomerValue + 0.5))
                        .append("%; font-family: sans-serif\" >")
                        .append(myFormatter.format(itm.getQty() * itm.getUnitPrice())).append("</td>")
                        .append("<td align=\"right\" style=\"font-size: ").append(Math.round(prevZoomerValue + 0.5))
                        .append("%; font-family: sans-serif\" >")
                        .append(myFormatter.format(VAT * itm.getQty() * itm.getUnitPrice())).append("</td>")
                        .append("<td align=\"right\" style=\"font-size: ").append(Math.round(prevZoomerValue + 0.5))
                        .append("%; font-family: sans-serif\" >")
                        .append(myFormatter.format(WITHVAT * itm.getQty() * itm.getUnitPrice())).append("</td>")
                        .append("</tr>");
                sum += (itm.getQty() * itm.getUnitPrice());
            }
            lst.append("<tr><td colspan=6><hr><td></tr>");
            lst.append("<tr><td/><td/><th align=\"right\" style=\"font-size: ")
                    .append(Math.round(prevZoomerValue + 0.5))
                    .append("%; font-family: sans-serif\" >Total:</th>")
                    .append("<td align=\"right\" style=\"font-size: ")
                    .append(Math.round(prevZoomerValue + 0.5)).append("%; font-family: sans-serif\" >")
                    .append(myFormatter.format(sum))
                    .append("</td>")
                    .append("<td align=\"right\" style=\"font-size: ")
                    .append(Math.round(prevZoomerValue + 0.5)).append("%; font-family: sans-serif\" >")
                    .append(myFormatter.format(sum * VAT))
                    .append("</td>")
                    .append("<td align=\"right\" style=\"font-size: ")
                    .append(Math.round(prevZoomerValue + 0.5)).append("%; font-family: sans-serif\" >")
                    .append(myFormatter.format(sum * WITHVAT))
                    .append("</td>")
                    .append("</tr>");
        } catch (RemoteException ex) {
            XGrafWorks.logAndShowMessage(ex);
            lst.append("<tr><td colspan=\"4\">").append(ex.getMessage()).append("</td></tr>");
        }
        return lst.toString();
    }

    @Override
    protected void closeOwner() {
        if (ownerDialog != null) {
            ownerDialog.dispose();
        }
    }
}
