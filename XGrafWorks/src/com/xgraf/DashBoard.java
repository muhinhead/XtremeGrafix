/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.xgraf;

import com.xgraf.remote.IMessageSender;
import com.xlend.util.ImagePanel;
import com.xlend.util.TexturedPanel;
import com.xlend.util.ToolBarButton;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.HeadlessException;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.AbstractAction;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.UIManager;

/**
 *
 * @author nick
 */
public class DashBoard extends JFrame {

    protected TexturedPanel main;
    protected JPanel controlsPanel;
    private final IMessageSender exchanger;
    public static DashBoard ourInstance;
    private int dashWidth;
    private int dashHeight;
    private static final String BACKGROUNDIMAGE = "background.png";
    private ToolBarButton setupButton;
    private ToolBarButton customersButton;
    private ToolBarButton documentsButton;
    private ToolBarButton exitButton;
    private GeneralFrame adminsFrame;
    private GeneralFrame customersFrame;

//    protected class WinListener extends WindowAdapter {
//
//        public WinListener(JFrame frame) {
//        }
//
//        public void windowClosing(WindowEvent e) {
//            exit();
//        }
//    }
    protected class LayerPanel extends JLayeredPane {

        LayerPanel() {
            super();
        }

        @Override
        public void setBounds(int x, int y, int width, int height) {
            super.setBounds(x, y, width, height);
            controlsPanel.setBounds(getBounds());
        }
    }

    protected void exit() {
        dispose();
        System.exit(1);
    }

    public DashBoard(String title, IMessageSender exchanger) {
        super(title);
        this.exchanger = exchanger;
        ourInstance = this;
        //addWindowListener(new DashBoard.WinListener(this));
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        lowLevelInit();
        initBackground();
        fillControlsPanel();

        setVisible(true);
    }

    protected void fillControlsPanel() throws HeadlessException {
        String imgName;
        customersButton = new ToolBarButton(imgName = "customers.png", true);
        customersButton.setToolTipText("Customers list");
        documentsButton = new ToolBarButton("documents.png", true);
        documentsButton.setToolTipText("Quotes, Orders, Invoices");
        setupButton = new ToolBarButton("setup.png", true);
        setupButton.setToolTipText("Users list & DB connection settings");
        exitButton = new ToolBarButton("shutdown.png", true);
        exitButton.setToolTipText("Shutdown the program");

        int step = 150;
        int shift = 30;
        int yshift = 150;

        ImagePanel img = new ImagePanel(XGrafWorks.loadImage(imgName, this));

        customersButton.setBounds(shift, yshift, img.getWidth(), img.getHeight());
        main.add(customersButton);
        shift += step;

        documentsButton.setBounds(shift, yshift, img.getWidth(), img.getHeight());
        main.add(documentsButton);
        shift += step;

        setupButton.setBounds(shift, yshift, img.getWidth(), img.getHeight());
        main.add(setupButton);
        shift += step;

//        exitButton.setBounds(getWidth()-130,getHeight()-135,img.getWidth(), img.getHeight());
//        main.add(exitButton);
        exitButton.setBounds(shift, yshift, img.getWidth(), img.getHeight());
        main.add(exitButton);

        customersButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if (customersFrame == null) {
                    customersFrame = new CustomersFrame(XGrafWorks.getExchanger());//new CompanyGrid(XGrafWorks.getExchanger());
                } else {
                    try {
                        customersFrame.setLookAndFeel(XGrafWorks.readProperty("LookAndFeel",
                                UIManager.getSystemLookAndFeelClassName()));
                    } catch (Exception ex) {
                    }
                    customersFrame.setVisible(true);
                }
            }
        });

        setupButton.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if (adminsFrame == null) {
                    adminsFrame = new AdminsFrame(XGrafWorks.getExchanger());
                } else {
                    try {
                        adminsFrame.setLookAndFeel(XGrafWorks.readProperty("LookAndFeel",
                                UIManager.getSystemLookAndFeelClassName()));
                    } catch (Exception ex) {
                    }
                    adminsFrame.setVisible(true);
                }
            }
        });

        exitButton.addActionListener(new AbstractAction() {

            @Override
            public void actionPerformed(ActionEvent e) {
                exit();
            }
        });

        centerOnScreen();
        setResizable(false);
        //centerWindow(this);
    }

    protected void initBackground() {
        XGrafWorks.setWindowIcon(this, XGrafWorks.WIN_ICON);
        //addWindowListener(new DashBoard.WinListener(this));
        controlsPanel = new JPanel(new BorderLayout());

        setLayout(new BorderLayout());

        LayerPanel layers = new LayerPanel();
        main = new TexturedPanel(getBackGroundImage());
        controlsPanel.add(main, BorderLayout.CENTER);
        addNotify();
        ImagePanel img = new ImagePanel(XGrafWorks.loadImage(getBackGroundImage(), this));
        Insets insets = getInsets();
        dashWidth = img.getWidth();
        dashHeight = img.getHeight();
        this.setMinimumSize(new Dimension(dashWidth + insets.left + insets.right, dashHeight + insets.top + insets.bottom));
        layers.add(controlsPanel, JLayeredPane.DEFAULT_LAYER);

        getContentPane().add(layers, BorderLayout.CENTER);
    }

    protected String getBackGroundImage() {
        return BACKGROUNDIMAGE;
    }

    public void lowLevelInit() {
        XGrafWorks.readProperty("junk", ""); // just to init properties
    }

    public void centerOnScreen() {
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(d.width / 2 - getWidth() / 2, d.height / 2 - getHeight() / 2);
    }

    public static void centerWindow(JFrame frame) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension frameSize = frame.getSize();
        if (frameSize.height > screenSize.height) {
            frameSize.height = screenSize.height;
        }
        if (frameSize.width > screenSize.width) {
            frameSize.width = screenSize.width;
        }
        frame.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height) / 2);
        frame.setExtendedState(Frame.NORMAL);
        frame.validate();
    }

    public static float getXratio(JFrame frame) {
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        return (float) frame.getWidth() / d.width;
    }

    public static float getYratio(JFrame frame) {
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        return (float) frame.getHeight() / d.height;
    }

    public static void setSizes(JFrame frame, double x, double y) {
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setSize((int) (x * d.width), (int) (y * d.height));
    }

    public void setVisible(boolean show) {
        super.setVisible(show);
    }
}
