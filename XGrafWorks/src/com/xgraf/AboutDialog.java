package com.xgraf;

import com.xlend.util.ImagePanel;
import com.xlend.util.PopupDialog;
import com.xlend.util.TexturedPanel;
import com.xlend.util.Util;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Nick Mukhin
 */
public class AboutDialog extends PopupDialog {

    private static final String BACKGROUNDIMAGE = "about.png";
    private AbstractAction closeAction;
    private JButton closeBtn;

    public AboutDialog() {
        super(null, "X-treme Grafi-X Contact Manager", null);
        XGrafWorks.setWindowIcon(this, "xg.png");
    }

    @Override
    protected Color getHeaderBackground() {
        return null;//new Color(102, 125, 158);
    }

    protected void fillContent() {
        Color fg = XGrafWorks.HDR_COLOR;
        super.fillContent();
        JPanel main = new TexturedPanel(BACKGROUNDIMAGE);
        getContentPane().add(main, BorderLayout.CENTER);
        ImagePanel img = new ImagePanel(Util.loadImage(BACKGROUNDIMAGE));
        this.setMinimumSize(new Dimension(img.getWidth(), img.getHeight() + 25));
        closeBtn = new JButton(closeAction = new AbstractAction("Ok") {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        JLabel version = new JLabel("Version " + XGrafWorks.getVersion());
        version.setBounds(370, 50, version.getPreferredSize().width, version.getPreferredSize().height);
        version.setForeground(fg);
        main.add(version);

        JLabel devBy = new JLabel("Nick Mukhin (mukhin.nick@gmail.com) (c) 2015");
        devBy.setFont(devBy.getFont().deriveFont(Font.ITALIC, 10));
        devBy.setBounds(74, 137, devBy.getPreferredSize().width, devBy.getPreferredSize().height);
        devBy.setForeground(fg);
        main.add(devBy);

        JLabel str1 = new JLabel("X-treme Grafi-X");
        str1.setFont(str1.getFont().deriveFont(Font.BOLD, 12));
        str1.setBounds(222, 184, str1.getPreferredSize().width, str1.getPreferredSize().height);
        str1.setForeground(fg);
        main.add(str1);

        JLabel str2 = new JLabel("Reg. no. 2003/101496/23  VAT no. 4120244407");
        str2.setFont(str2.getFont().deriveFont(Font.ITALIC | Font.BOLD, 10));
        str2.setBounds(74, 199, str2.getPreferredSize().width, str2.getPreferredSize().height);
        str2.setForeground(fg);
        main.add(str2);

        JLabel str3 = new JLabel("9 Wye Avenue, Three Rivers, Vereeniging, 1929");
        str3.setFont(str3.getFont().deriveFont(Font.ITALIC, 10));
        str3.setBounds(74, 212, str3.getPreferredSize().width, str3.getPreferredSize().height);
        str3.setForeground(fg);
        main.add(str3);

        JLabel str4 = new JLabel("morne@vodamail.co.za");
        str4.setFont(str4.getFont().deriveFont(Font.ITALIC, 10));
        str4.setBounds(74, 225, str4.getPreferredSize().width, str4.getPreferredSize().height);
        str4.setForeground(fg);
        main.add(str4);

        closeBtn.setBounds(400, 220,
                closeBtn.getPreferredSize().width,
                closeBtn.getPreferredSize().height);

        main.add(closeBtn);
        setResizable(false);
    }

    @Override
    public void freeResources() {
        closeBtn.removeActionListener(closeAction);
        closeAction = null;
    }
}
