/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xgraf;

import com.xgraf.orm.User;
import com.xgraf.remote.IMessageSender;
import java.awt.event.ActionEvent;
import java.rmi.RemoteException;
import javax.swing.AbstractAction;
import javax.swing.JOptionPane;

/**
 *
 * @author Nick Mukhin
 */
public class UsersGrid extends GeneralGridPanel {

    public static final String SELECT = "select user_id \"Id\","
            + "first_name \"First name\", last_name \"Last name\", initials \"Initials\","
            + "login \"Login\", if(is_admin,'Yes','No') \"Admin permissions\" from user";

    public UsersGrid(IMessageSender exchanger) throws RemoteException {
        super(exchanger, SELECT, null, false);
    }

    @Override
    protected AbstractAction addAction() {
        if (XGrafWorks.getCurrentUser().getIsAdmin() != null && XGrafWorks.getCurrentUser().getIsAdmin().intValue() == 1) {
            return new AbstractAction("Add User") {
                @Override
                public void actionPerformed(ActionEvent e) {
                    EditUserDialog ed = new EditUserDialog("New User", null);
                    if (EditUserDialog.okPressed) {
                        User user = (User) ed.getEditPanel().getDbObject();
                        refresh(user.getUserId());
                    }
                }
            };
        } else {
            return null;
        }
    }

    @Override
    protected AbstractAction editAction() {
        return new AbstractAction("Edit") {
            @Override
            public void actionPerformed(ActionEvent ae) {
                int id = getSelectedID();
                if (id != 0) {
                    try {
                        User user = (User) exchanger.loadDbObjectOnID(User.class, id);
                        new EditUserDialog("Edit User", user);
                        if (EditUserDialog.okPressed) {
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
        if (XGrafWorks.getCurrentUser().getIsAdmin() != null && XGrafWorks.getCurrentUser().getIsAdmin().intValue() == 1) {
            return new AbstractAction("Delete") {
                @Override
                public void actionPerformed(ActionEvent ae) {
                    int id = getSelectedID();
                    if (id != 0) {
                        try {
                            User user = (User) exchanger.loadDbObjectOnID(User.class, id);
                            if (user != null) {
                                if (id == 1) {
                                    GeneralFrame.errMessageBox("Attention!",
                                            "You can't to dismiss the chief administrator!");
                                } else if (GeneralFrame.yesNo("Attention!",
                                        "Do you want to delete this user?") == JOptionPane.YES_OPTION) {
                                    exchanger.deleteObject(user);
                                    refresh();
                                }
                            }
                        } catch (RemoteException ex) {
                            XGrafWorks.logAndShowMessage(ex);
                        }
                    }
                }
            };
        } else {
            return null;
        }
    }
}
