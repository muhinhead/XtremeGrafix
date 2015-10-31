/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xgraf;

import com.xgraf.orm.Company;
import com.xgraf.orm.Contact;
import com.xgraf.orm.dbobject.ComboItem;
import com.xgraf.orm.dbobject.DbObject;
import com.xlend.util.Java2sAutoComboBox;
import com.xlend.util.PopupDialog;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.rmi.RemoteException;
import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

/**
 *
 * @author nick
 */
public abstract class BaseEditDocPanel extends RecordEditPanel {
    protected JTextField bankAccHolderField;
    protected JTextField bankAccNoField;
    protected Java2sAutoComboBox bankAccTypeCB;
    protected JTextField bankBranchCodeField;
    protected JTextField bankField;
    protected JTextField companyAddressField;
    protected JComboBox companyCB;
    protected DefaultComboBoxModel companyCbModel;
    protected DefaultComboBoxModel contactCbModel;
    protected JTextField contactEmailField;
    protected JComboBox contactPersonCB;
    protected JTextField contactPhoneField;
    protected Java2sAutoComboBox serviceTypeCB;

    public BaseEditDocPanel(DbObject dbObject) {
        super(dbObject);
    }

    protected abstract String tableName();

    protected abstract JPanel getPrintPanel(PopupDialog dlg);

    public abstract void recalcTotal(Integer docID);

    @Override
    protected JComponent getRightUpperPanel() {
        JPanel rightUpperPanel = new JPanel(new BorderLayout());
        rightUpperPanel.setBorder(BorderFactory.createTitledBorder("Billing Information"));
        JPanel upLabelPanel = new JPanel(new GridLayout((this instanceof EditStatementPanel) ? 12 : 14, 1, 5, 5));
        JPanel upEditPanel = new JPanel(new GridLayout((this instanceof EditStatementPanel) ? 12 : 14, 1, 5, 5));
        rightUpperPanel.add(upLabelPanel, BorderLayout.WEST);
        rightUpperPanel.add(upEditPanel, BorderLayout.CENTER);
        upLabelPanel.add(new JLabel("Billing Contact:", SwingConstants.RIGHT));
        upLabelPanel.add(new JLabel("Billing Address:", SwingConstants.RIGHT));
        upLabelPanel.add(new JLabel("Contact Person:", SwingConstants.RIGHT));
        upLabelPanel.add(new JLabel("Contact Number:", SwingConstants.RIGHT));
        upLabelPanel.add(new JLabel("Contact E-mail:", SwingConstants.RIGHT));
        if (!(this instanceof EditStatementPanel)) {
            upLabelPanel.add(new JLabel("Service Type:", SwingConstants.RIGHT));
        }
        upLabelPanel.add(new JPanel());
        upLabelPanel.add(new JPanel());
        upLabelPanel.add(new JPanel());
        upLabelPanel.add(new JPanel());
        upLabelPanel.add(new JPanel());
        if (!(this instanceof EditStatementPanel)) {
            upLabelPanel.add(new JPanel());
        }
        upLabelPanel.add(new JPanel());

        companyCbModel = new DefaultComboBoxModel();
        for (ComboItem ci : XGrafWorks.loadAllCompanies()) {
            companyCbModel.addElement(ci);
        }
        contactCbModel = new DefaultComboBoxModel();
        upEditPanel.add(comboPanelWithLookupBtn(companyCB = new JComboBox(companyCbModel), new CompanyLookupAction(companyCB, contactCbModel)));
        upEditPanel.add(companyAddressField = new JTextField(30));
        upEditPanel.add(comboPanelWithLookupBtn(contactPersonCB = new JComboBox(contactCbModel), new ContactLookupAction(contactPersonCB, companyCB)));
        upEditPanel.add(contactPhoneField = new JTextField());
        upEditPanel.add(contactEmailField = new JTextField());
        if (!(this instanceof EditStatementPanel)) {
            upEditPanel.add(serviceTypeCB = new Java2sAutoComboBox(XGrafWorks.loadDistinct(new String[]{"quote","invoice"}, "service_type")));
        }
        upEditPanel.add(new JPanel());
        upEditPanel.add(new JPanel());
        upEditPanel.add(new JPanel());
        upEditPanel.add(new JPanel());
        upEditPanel.add(new JPanel());
        if (!(this instanceof EditStatementPanel)) {
            upEditPanel.add(new JPanel());
        }
        upEditPanel.add(new JPanel());

        companyAddressField.setEditable(false);
        contactPhoneField.setEditable(false);
        contactEmailField.setEditable(false);
        if (!(this instanceof EditStatementPanel)) {
            serviceTypeCB.setEditable(true);
            serviceTypeCB.setStrict(false);
        }
        companyCB.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Integer companyID = getSelectedCbItem(companyCB);
                contactCbModel.removeAllElements();
                for (ComboItem ci : XGrafWorks.loadContactsOnCompany(companyID)) {
                    contactCbModel.addElement(ci);
                }
                try {
                    Company comp = (Company) XGrafWorks.getExchanger().loadDbObjectOnID(Company.class, companyID.intValue());
                    companyAddressField.setText(comp.getStreet() + " " + comp.getCity());
                } catch (RemoteException ex) {
                    XGrafWorks.logAndShowMessage(ex);
                }
            }
        });
        companyCB.setSelectedIndex(0);
        contactPersonCB.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Integer contactID = getSelectedCbItem(contactPersonCB);
                if (contactID != null) {
                    try {
                        Contact cont = (Contact) XGrafWorks.getExchanger().loadDbObjectOnID(Contact.class, contactID.intValue());
                        contactPhoneField.setText(cont.getPhone());
                        contactEmailField.setText(cont.getEmail() + (cont.getEmail1() != null && !cont.getEmail1().isEmpty() ? ", " + cont.getEmail1() : ""));
                    } catch (RemoteException ex) {
                        XGrafWorks.logAndShowMessage(ex);
                    }
                }
            }
        });
        return rightUpperPanel;
    }

}
