package com.xgraf;

import com.xgraf.orm.dbobject.ComboItem;
import com.xlend.util.Java2sAutoComboBox;
import com.xlend.util.PopupDialog;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.rmi.RemoteException;
import javax.swing.*;

/**
 *
 * @author Nick Mukhin
 */
public class LookupDialog extends PopupDialog {

    private static Integer choosedID;
    private JButton okBtn, cancelBtn;
    private AbstractAction okAction, cancelAction;
    private GeneralGridPanel grid;
    private String[] filteredColumns;
    private JComboBox comboBox;
    private JTextField filterField;
    private String originalSelect;

    public LookupDialog(String title, JComboBox cb, GeneralGridPanel grid, String[] filteredColumns) {
        super(null, title, new Object[]{cb, grid, filteredColumns});
    }

    @Override
    protected Color getHeaderBackground() {
        return new Color(102, 125, 158);
    }

    @Override
    protected void fillContent() {
        super.fillContent();
        XGrafWorks.setWindowIcon(this, "xg.png");
        Object[] params = (Object[]) getObject();
        comboBox = (JComboBox) params[0];
        grid = (GeneralGridPanel) params[1];
        if (comboBox != null && comboBox.getSelectedItem() != null && !(comboBox instanceof Java2sAutoComboBox)) {
            choosedID = ((ComboItem) comboBox.getSelectedItem()).getId();
            grid.selectRowOnId(choosedID);
        }
        this.filteredColumns = (String[]) params[2];
        originalSelect = grid.getSelect();

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        JPanel centerPanel = new JPanel(new BorderLayout());
        if (filteredColumns != null) {
            JPanel upCenterPanel = new JPanel(new FlowLayout());
            upCenterPanel.add(new JLabel("Filter:"));
            upCenterPanel.add(filterField = new JTextField(40));
            centerPanel.add(upCenterPanel, BorderLayout.NORTH);
        }
        centerPanel.add(new JScrollPane(grid), BorderLayout.CENTER);
        getContentPane().add(centerPanel, BorderLayout.CENTER);

        if (filteredColumns != null) {
            filterField.addKeyListener(new KeyAdapter() {
                @Override
                public void keyReleased(KeyEvent e) {
                    String select = originalSelect;
                    int w = findNotInParents(select.toLowerCase(), " where");//select.toLowerCase().lastIndexOf(" where");
                    int o = findNotInParents(select.toLowerCase(), " order by");
                    o = o < w ? -1 : o;
                    StringBuilder addWhereCond = new StringBuilder();
                    for (String col : filteredColumns) {
                        addWhereCond.append(addWhereCond.length() > 0 ? " or " : "(").append("upper(").append(col).append(") like '%").append(filterField.getText().toUpperCase()).append("%'");
                    }
                    if (addWhereCond.length() > 0) {
                        addWhereCond.append(")");
                    }

                    if (w < 0 && o < 0) {
                        select += (" where " + addWhereCond.toString());
                    } else if (w < 0 && o > 0) {
                        select = select.substring(0, o) + " where " + addWhereCond.toString() + select.substring(o);
                    } else {
                        select = select.substring(0, w + 7) + addWhereCond.toString() + " aNd " + select.substring(w + 7);//select.substring(o);
                    }
                    grid.setSelect(select);
                    try {
                        GeneralFrame.updateGrid(XGrafWorks.getExchanger(),
                                grid.getTableView(), grid.getTableDoc(), grid.getSelect(), null, -1);
                    } catch (RemoteException ex) {
                        XGrafWorks.logAndShowMessage(ex);
                    }
                }
            });
            okBtn = new JButton(okAction = selectionAction("Pick up"));
            getRootPane().setDefaultButton(okBtn);
            buttonPanel.add(okBtn);
        }

        cancelBtn = new JButton(cancelAction = new AbstractAction("Cancel",new ImageIcon(XGrafWorks.loadImage("cancel.png", LookupDialog.class))) {
            @Override
            public void actionPerformed(ActionEvent e) {
                choosedID = null;
                dispose();
            }
        });
        buttonPanel.add(cancelBtn);
        getContentPane().add(buttonPanel, BorderLayout.SOUTH);

        grid.getTableView().removeMouseListener(grid.getDoubleClickAdapter());
        grid.getTableView().addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    okAction.actionPerformed(null);
                }
            }
        });
        setPreferredSize(new Dimension(800,getPreferredSize().height));
//        } else {
//            GeneralFrame.errMessageBox("Sorry!", "Empty combobox, couldn't build lookup dialog");
//            dispose();
//        }
    }

    private static int findNotInParents(String source, String search) {
        int level = 0;
        for (int i = 0; i < source.length(); i++) {
            String sub = source.substring(i);
            if (sub.startsWith("(")) {
                level++;
            } else if (sub.startsWith(")")) {
                level--;
            } else if (sub.startsWith(search) && level == 0) {
                return i;
            }
        }
        return -1;
    }

    private AbstractAction selectionAction(String title) {
        return new AbstractAction(title,new ImageIcon(XGrafWorks.loadImage("ok.png", LookupDialog.class))) {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean found = false;
                choosedID = grid.getSelectedID();
                for (int i = 0; comboBox != null && i < comboBox.getItemCount(); i++) {
                    if (comboBox instanceof Java2sAutoComboBox) {
                        //TODO
                    } else {
                        ComboItem citm = (ComboItem) comboBox.getItemAt(i);
                        if (citm != null && citm.getId() == choosedID) {
                            comboBox.setSelectedIndex(i);
                            found = true;
                            break;
                        }
                    }
                }
                if (!found && comboBox != null) {
                    if (comboBox instanceof Java2sAutoComboBox) {
                        //TODO
                    } else {
                        ComboItem newItem;
                        DefaultComboBoxModel cbModel = (DefaultComboBoxModel) comboBox.getModel();
                        cbModel.addElement(newItem = new ComboItem(choosedID, grid.getSelectedRowCeil(1)));
                        comboBox.setSelectedItem(newItem);
                    }
                }
                dispose();
            }
        };
    }

    /**
     * @return the choosed
     */
    public static Integer getChoosed() {
        return choosedID;
    }

    @Override
    public void freeResources() {
        if (okBtn != null && okAction != null) {
            okBtn.removeActionListener(okAction);
        }
        okAction = null;
        if (cancelBtn != null && cancelAction != null) {
            cancelBtn.removeActionListener(cancelAction);
        }
        cancelAction = null;
    }
}
