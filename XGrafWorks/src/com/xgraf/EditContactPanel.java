// Generated by com.nm.GridEditGenerator at Tue Sep 01 08:40:03 EEST 2015
// generated file, so all hand editions will be overwritten
package com.xgraf;

import com.xgraf.orm.Contact;
import com.xgraf.orm.dbobject.ComboItem;
import com.xgraf.orm.dbobject.DbObject;
import java.awt.BorderLayout;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class EditContactPanel extends EditPanelWithPhoto {

    private CompanyLookupAction compLA;

    public EditContactPanel(DbObject dbObject) {
        super(dbObject);
    }

//column: contact_id type: INT class: java.lang.Integer
    private JTextField idField;
//column: name type: VARCHAR class: java.lang.String
    private JTextField nameField;
    private JComboBox companyCB;
//column: phone type: VARCHAR class: java.lang.String
    private JTextField phoneField;
//column: email type: VARCHAR class: java.lang.String
    private JTextField emailField;
//column: email1 type: VARCHAR class: java.lang.String
    private JTextField email1Field;
//column: comments type: VARCHAR class: java.lang.String
    private JTextArea commentsField;

    @Override
    protected void fillContent() {
        String[] titles = {
            "ID:",
            "Name:",
            "Company:",
            "Phone(s):",
            "E-mail" //second e-mail            
        };
        DefaultComboBoxModel companyCbModel = new DefaultComboBoxModel();
        for (ComboItem ci : XGrafWorks.loadAllCompanies()) {
            companyCbModel.addElement(ci);
        }
        JComponent[] edits = new JComponent[]{
            getGridPanel(idField = new JTextField(), 4),
            nameField = new JTextField(32),
            comboPanelWithLookupBtn(companyCB = new JComboBox(companyCbModel),
            compLA = new CompanyLookupAction(companyCB, null)),
            phoneField = new JTextField(),
            getBorderPanel(new JComponent[]{
                emailField = new JTextField(16),
                new JLabel("second E-mail:", SwingConstants.RIGHT),
                email1Field = new JTextField(16)
            })
        };
        idField.setEnabled(false);
        organizePanels(titles, edits, null);

        MyJideTabbedPane detailsTab = new MyJideTabbedPane();
        JScrollPane sp;
        detailsTab.add(sp = new JScrollPane(commentsField = new JTextArea(6, 40),
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER), "Comments");
        Contact cont = (Contact) getDbObject();
        if (cont != null) {
            int contact_id = cont.getContactId();
            try {
                detailsTab.add(new QuoteGrid(contact_id, XGrafWorks.getExchanger()), "Quotes");
                detailsTab.add(new ProFormaInvoiceGrid(contact_id, XGrafWorks.getExchanger()), "Pro-Forma Invoices");
                detailsTab.add(new InvoiceGrid(contact_id, XGrafWorks.getExchanger()), "Tax Invoices");
            } catch (RemoteException ex) {
                XGrafWorks.logAndShowMessage(ex);
            }
        }
        add(detailsTab, BorderLayout.CENTER);
        compLA.setEnabled(EditContactDialog.companyID == null);
        companyCB.setEnabled(EditContactDialog.companyID == null);
    }

    @Override
    public void loadData() {
        Contact cont = (Contact) getDbObject();
        if (cont != null) {
            idField.setText(cont.getContactId().toString());
            nameField.setText(cont.getName());
            selectComboItem(companyCB, cont.getCompanyId());
            phoneField.setText(cont.getPhone());
            emailField.setText(cont.getEmail());
            email1Field.setText(cont.getEmail1());
            commentsField.setText(cont.getComments());
            imageData = (byte[]) cont.getPhoto();
            setImage(imageData);
        } else {
            selectComboItem(companyCB, EditContactDialog.companyID);
        }
        setEnabledPictureControl(true);
    }

    @Override
    public boolean save() throws Exception {
        Contact cont = (Contact) getDbObject();
        boolean isNew = cont == null;
        if (isNew) {
            cont = new Contact(null);
            cont.setContactId(0);
        }
        cont.setName(nameField.getText());
        cont.setCompanyId(getSelectedCbItem(companyCB));
        cont.setPhone(phoneField.getText());
        cont.setEmail(emailField.getText());
        cont.setEmail1(email1Field.getText());
        cont.setComments(commentsField.getText());
        cont.setPhoto(imageData);
        return saveDbRecord(cont, isNew);
    }
}
