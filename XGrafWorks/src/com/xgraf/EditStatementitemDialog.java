// Generated by com.nm.GridEditGenerator at Fri Oct 30 16:44:52 EET 2015
// generated file, so all hand editions will be overwritten
package com.xgraf;

import com.xgraf.EditRecordDialog;
import com.xgraf.orm.Statementitem;

public class EditStatementitemDialog extends EditRecordDialog {

    public static boolean okPressed;
    public EditStatementitemDialog(String title, Statementitem rec) {
        super(title, rec);
    }

    @Override
    protected void fillContent() {
        super.fillContent(new EditStatementitemPanel((Statementitem) getObject()));
    }

    @Override
    protected void setOkPressed(boolean b) {
        okPressed = b;
    }
}

