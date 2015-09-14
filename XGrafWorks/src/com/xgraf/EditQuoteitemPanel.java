// Generated by com.nm.GridEditGenerator at Sun Sep 13 18:59:53 EEST 2015
// generated file, so all hand editions will be overwritten
package com.xgraf;

import com.xgraf.orm.Quoteitem;
import com.xgraf.orm.dbobject.DbObject;
import com.xlend.util.SelectedNumberSpinner;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class EditQuoteitemPanel extends RecordEditPanel {
    private SelectedNumberSpinner unitPriceSpinner;

    public EditQuoteitemPanel(DbObject dbObject) {
        super(dbObject);
    }

    private JTextField idField;
    private JTextField descrField;
    private SelectedNumberSpinner qtyNumberSpinner;

    @Override
    protected void fillContent() {
        String[] titles = {
            "ID:",
            "Description:",
            "Unit Price:"
        };
        JComponent[] edits = new JComponent[]{
            getGridPanel(idField = new JTextField(), 5),
            descrField = new JTextField(30),
            getBorderPanel(new JComponent[]{
                unitPriceSpinner = new SelectedNumberSpinner(1.0, 1.0, 99999.0, 0.1),
                new JLabel("Qty:",SwingConstants.RIGHT),
                qtyNumberSpinner = new SelectedNumberSpinner(1, 1, 10000, 1)
            }),
        };
        organizePanels(titles, edits, null);    
        idField.setEnabled(false);
    }

    @Override
    public void loadData() {
        Quoteitem it = (Quoteitem) getDbObject();
        if (it != null) {
            idField.setText(it.getQuoteitemId().toString());
            descrField.setText(it.getDescr());
            unitPriceSpinner.setValue(it.getUnitPrice());
            qtyNumberSpinner.setValue(it.getQty());
        }
    }

    @Override
    public boolean save() throws Exception {
        Quoteitem it = (Quoteitem) getDbObject();
        boolean isNew = it == null;
        if (isNew) {
            it = new Quoteitem(null);
            it.setQuoteitemId(0);
        }
        it.setDescr(descrField.getText());
        it.setQty((Integer) qtyNumberSpinner.getValue());
        it.setUnitPrice((Double) unitPriceSpinner.getValue());
        it.setQuoteId(EditQuoteitemDialog.quoteID);
        return saveDbRecord(it, isNew);
    }
}
