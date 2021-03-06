/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xgraf;

import com.xlend.util.PopupDialog;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 *
 * @author nick
 */
public abstract class EditRecordDialog extends PopupDialog {

//    protected JButton applyButton;
//    private AbstractAction applyAction;
    protected JButton saveButton;
    private AbstractAction saveAction;
    protected JButton cancelButton;
    private AbstractAction cancelAction;
    private RecordEditPanel editPanel;

    public EditRecordDialog(String title, Object obj) {
        super(null, title, obj);
    }

    @Override
    protected Color getHeaderBackground() {
        return XGrafWorks.HDR_COLOR;
    }
    
    protected void addBeforeButtons(JPanel btnPanel) {        
    }

    protected void addAfterButtons(JPanel btnPanel) {        
    }
    
    protected void fillContent(RecordEditPanel editPanel) {
        super.fillContent();
        setOkPressed(false);
        XGrafWorks.setWindowIcon(this, "xg.png");
        this.editPanel = editPanel;
        this.editPanel.setOwnerDialog(this);
        JPanel btnPanel = new JPanel();
//        btnPanel.add(applyButton = new JButton(applyAction = getApplyAction()));
        addBeforeButtons(btnPanel);
        btnPanel.add(saveButton = new JButton(saveAction = getSaveAction()));
        btnPanel.add(cancelButton = new JButton(cancelAction = getCancelAction()));
        addAfterButtons(btnPanel);
        
//        applyButton.setToolTipText("Apply changes to database");
        saveButton.setToolTipText("Save changes and close the dialog");
        cancelButton.setToolTipText("Discard changes and close the dialog");

//        getContentPane().add(new JPanel(), BorderLayout.WEST);
//        getContentPane().add(new JPanel(), BorderLayout.EAST);
//        getContentPane().add(editPanel, BorderLayout.CENTER);
//
//        JPanel aroundButton = new JPanel(new BorderLayout());
//        aroundButton.add(btnPanel, BorderLayout.EAST);
//        getContentPane().add(aroundButton, BorderLayout.SOUTH);
//        getRootPane().setDefaultButton(saveButton);
        JPanel innerPanel = new JPanel(new BorderLayout());

        innerPanel.add(new JPanel(), BorderLayout.WEST);
        innerPanel.add(new JPanel(), BorderLayout.EAST);
        innerPanel.add(editPanel, BorderLayout.CENTER);

        JPanel aroundButton = new JPanel(new BorderLayout());
        aroundButton.add(btnPanel, BorderLayout.EAST);
        innerPanel.add(aroundButton, BorderLayout.SOUTH);
        getContentPane().add(new JScrollPane(innerPanel), BorderLayout.CENTER);
        getRootPane().setDefaultButton(saveButton);
        
    }

    @Override
    public void freeResources() {
//        applyButton.removeActionListener(applyAction);
        saveButton.removeActionListener(saveAction);
        cancelButton.removeActionListener(cancelAction);
        saveAction = null;
        cancelAction = null;
//        applyAction = null;
    }

//    protected AbstractAction getApplyAction() {
//        return new AbstractAction("Apply") {
//
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                try {
//                    if (getEditPanel().save()) {
//                        getEditPanel().loadData();
//                    }
//                } catch (Exception ex) {
//                    XlendWorks.log(ex);
//                    GeneralFrame.errMessageBox("Error:", ex.getMessage());
//                }
//            }
//        };
//    }
    protected AbstractAction getSaveAction() {
        return new AbstractAction("Save",new ImageIcon(XGrafWorks.loadImage("ok.png", EditRecordDialog.class))) {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (getEditPanel().save()) {
                        setOkPressed(true);
                        dispose();
                    }
                } catch (Exception ex) {
                    XGrafWorks.logAndShowMessage(ex);
                }
            }
        };
    }

    protected void setOkPressed(boolean b) {
    }

    protected AbstractAction getCancelAction() {
        return new AbstractAction("Cancel",new ImageIcon(XGrafWorks.loadImage("cancel.png", EditRecordDialog.class))) {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        };
    }

    /**
     * @return the editPanel
     */
    public RecordEditPanel getEditPanel() {
        return editPanel;
    }
}
