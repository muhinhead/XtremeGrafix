// Generated by com.nm.GridEditGenerator at Tue Sep 01 08:40:03 EEST 2015
// generated file, so all hand editions will be overwritten
package com.xgraf;

import com.xgraf.EditRecordDialog;
import com.xgraf.orm.Contact;

public class EditContactDialog extends EditRecordDialog {

    public static boolean okPressed;
    public EditContactDialog(String title, Contact rec) {
        super(title, rec);
    }

    @Override
    protected void fillContent() {
        super.fillContent(new EditContactPanel((Contact) getObject()));
    }

    @Override
    protected void setOkPressed(boolean b) {
        okPressed = b;
    }
}
