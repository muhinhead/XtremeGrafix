/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xgraf;

import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.JTabbedPane;

/**
 *
 * @author nick
 */
public class MyJideTabbedPane extends JTabbedPane {

    public MyJideTabbedPane() {
//        super(JideTabbedPane.TOP);
//        setShowTabButtons(true);
//        setBoldActiveTab(true);
//        setColorTheme(JideTabbedPane.COLOR_THEME_OFFICE2003);
//        setTabShape(JideTabbedPane.SHAPE_BOX);
    }

    public void addTab(JComponent comp, String title, Icon icon) {
//        if (icon == null) {
//            super.add(comp, title);
//        } else {
//            super.addTab(title, icon, comp);
//        }
        super.add(title,comp);
    }

//    public void addTab(JComponent comp, String title) {
//        addTab(comp, title, new ImageIcon(AIBclient.loadImage("xlendfolder.jpg", DashBoard.ourInstance)));
//    }
}