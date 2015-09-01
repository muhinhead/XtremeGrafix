// Generated by com.nm.GridEditGenerator at Tue Sep 01 08:40:03 EEST 2015
// generated file, so all hand editions will be overwritten
package com.xgraf;

import com.xgraf.GeneralFrame;
import com.xgraf.GeneralGridPanel;
import com.xgraf.orm.Contact;
import com.xgraf.remote.IMessageSender;
import java.awt.event.ActionEvent;
import java.rmi.RemoteException;
import java.util.HashMap;
import javax.swing.AbstractAction;
import javax.swing.JOptionPane;

public class ContactGrid extends GeneralGridPanel {

    public static final String SELECT = "Select "
                                      + "contact_id,"
                                      + "name,"
                                      + "phone,"
                                      + "email,"
                                      + "email1,"
                                      + "comments from contact";
    private static HashMap<Integer, Integer> maxWidths = new HashMap<Integer, Integer>();

    static {
        maxWidths.put(0, 40);
    }

    public ContactGrid(IMessageSender exchanger) throws RemoteException {
        super(exchanger, SELECT, maxWidths, false);
    }

    public ContactGrid(IMessageSender exchanger, Integer companyID) throws RemoteException {
        super(exchanger, SELECT+" where company_id=" + companyID, maxWidths, false);
    }

    public ContactGrid(IMessageSender exchanger, String select) throws RemoteException {
        super(exchanger, select, maxWidths, false);
    }

    public ContactGrid(IMessageSender exchanger,String select, boolean readOnly) throws RemoteException {
        super(exchanger, select, maxWidths, readOnly);
    }

    @Override
    protected AbstractAction addAction() {
        return new AbstractAction("Add record") {
            @Override
            public void actionPerformed(ActionEvent ae) {
                EditContactDialog ed = new EditContactDialog("Add Contact", null);
                if (EditContactDialog.okPressed) {
                    Contact rec = (Contact) ed.getEditPanel().getDbObject();
                    refresh(rec.getPK_ID());
                }
            }
        };
    }

    @Override
    protected AbstractAction editAction() {
        return new AbstractAction("Edit") {
            @Override
            public void actionPerformed(ActionEvent ae) {
                int id = getSelectedID();
                if (id != 0) {
                    try {
                        Contact contact = (Contact) exchanger.loadDbObjectOnID(Contact.class, id);
                        new EditContactDialog("Edit record", contact);
                        if (EditContactDialog.okPressed) {
                            refresh();
                        }
                    } catch (RemoteException ex) {
                        XGrafWorks.logAndShowMessage(ex);
                    }
                }
            }
        };
    }

    @Override
    protected AbstractAction delAction() {
        return new AbstractAction("Delete") {
            @Override
            public void actionPerformed(ActionEvent ae) {
                int id = getSelectedID();
                if (id != 0) {
                    try {
                        Contact contact = (Contact) exchanger.loadDbObjectOnID(Contact.class, id);
                        if (contact != null && GeneralFrame.yesNo("Attention!", "Do you want to delete this record?") == JOptionPane.YES_OPTION) {
                            exchanger.deleteObject(contact);
                            refresh();
                        }
                    } catch (RemoteException ex) {
                        XGrafWorks.logAndShowMessage(ex);
                    }
                }
            }
        };
    }
}

