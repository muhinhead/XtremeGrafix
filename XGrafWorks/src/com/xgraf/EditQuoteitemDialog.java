// Generated by com.nm.GridEditGenerator at Sun Sep 13 18:59:53 EEST 2015
// generated file, so all hand editions will be overwritten
package com.xgraf;

import com.xgraf.EditRecordDialog;
import com.xgraf.orm.Quoteitem;

public class EditQuoteitemDialog extends EditRecordDialog {

    public static Integer quoteID = null;
    public static boolean okPressed;
    public EditQuoteitemDialog(String title, Quoteitem rec) {
        super(title, rec);
    }

    @Override
    protected void fillContent() {
        super.fillContent(new EditQuoteitemPanel((Quoteitem) getObject()));
    }

    @Override
    protected void setOkPressed(boolean b) {
        okPressed = b;
    }
}
