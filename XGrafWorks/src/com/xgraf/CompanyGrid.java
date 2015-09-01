// Generated by com.nm.GridEditGenerator at Mon Aug 31 18:35:20 EEST 2015
// generated file, so all hand editions will be overwritten
package com.xgraf;

import com.xgraf.GeneralFrame;
import com.xgraf.GeneralGridPanel;
import com.xgraf.orm.Company;
import com.xgraf.remote.IMessageSender;
import com.xgraf.GeneralFrame;
import java.awt.event.ActionEvent;
import java.rmi.RemoteException;
import java.util.HashMap;
import javax.swing.AbstractAction;
import javax.swing.JOptionPane;

public class CompanyGrid extends GeneralGridPanel {

    public static final String SELECT = "Select "
                                      + "company_id,"
                                      + "name,"
                                      + "street,"
                                      + "area_pobox,"
                                      + "city,"
                                      + "postcode,"
                                      + "reg_no,"
                                      + "vat_no,"
                                      + "comments from company";
    private static HashMap<Integer, Integer> maxWidths = new HashMap<Integer, Integer>();

    static {
        maxWidths.put(0, 40);
    }

    public CompanyGrid(IMessageSender exchanger) throws RemoteException {
        super(exchanger, SELECT, maxWidths, false);
    }

    public CompanyGrid(IMessageSender exchanger, String select) throws RemoteException {
        super(exchanger, select, maxWidths, false);
    }

    public CompanyGrid(IMessageSender exchanger,String select, boolean readOnly) throws RemoteException {
        super(exchanger, select, maxWidths, readOnly);
    }

    @Override
    protected AbstractAction addAction() {
        return new AbstractAction("Add record") {
            @Override
            public void actionPerformed(ActionEvent ae) {
                EditCompanyDialog ed = new EditCompanyDialog("Add Company", null);
                if (EditCompanyDialog.okPressed) {
                    Company rec = (Company) ed.getEditPanel().getDbObject();
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
                        Company company = (Company) exchanger.loadDbObjectOnID(Company.class, id);
                        new EditCompanyDialog("Edit record", company);
                        if (EditCompanyDialog.okPressed) {
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
                        Company company = (Company) exchanger.loadDbObjectOnID(Company.class, id);
                        if (company != null && GeneralFrame.yesNo("Attention!", "Do you want to delete this record?") == JOptionPane.YES_OPTION) {
                            exchanger.deleteObject(company);
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

