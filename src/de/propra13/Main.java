package de.propra13;

import java.awt.CardLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import de.propra13.controllers.ControllerFactory;

public class Main extends JFrame {

    private static final long serialVersionUID = -4373574916623551121L;

    public static final int GAMEFIELDWIDTH = 800;
    public static final int GAMEFIELDHEIGHT = 600;
    public static final int HUDHEIGHT = 50;
    public static final int HEIGHT = GAMEFIELDHEIGHT + HUDHEIGHT;

    public static final String ROOTVIEW = "rootView";
    public static final String GAMEVIEW = "gameView";

    public static void main(String args[]) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Main(new ControllerFactory());
            }
        });
    }

    public Main(ControllerFactory factory) {
        getContentPane().setLayout(new CardLayout());
        getContentPane().setPreferredSize(
                new Dimension(Main.GAMEFIELDWIDTH, Main.HEIGHT));

        factory.initControllers(this);
        factory.appendAllToPanel((JPanel) getContentPane());

        initRootWindow();
    }

    private void initRootWindow() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle("Keiler Crawler");
        setMinimumSize(new Dimension(Main.GAMEFIELDWIDTH, Main.HEIGHT));
        setResizable(false);
        setVisible(true);
        pack();
    }
}
