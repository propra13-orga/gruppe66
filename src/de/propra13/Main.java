package de.propra13;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.Toolkit;

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
            @Override
            public void run() {
                Main main = new Main();
                ControllerFactory factory = new ControllerFactory();

                factory.initControllers(main);
                factory.appendAllToPanel((JPanel) main.getContentPane());
            }
        });
    }

    public Main() {
        getContentPane().setLayout(new CardLayout());
        getContentPane().setPreferredSize(
                new Dimension(Main.GAMEFIELDWIDTH, Main.HEIGHT));
        initRootWindow();
    }

    private void initRootWindow() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle("Keiler Crawler");
        setMinimumSize(new Dimension(Main.GAMEFIELDWIDTH, Main.HEIGHT));
        setResizable(false);
        setVisible(true);
        pack();

        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - getWidth()) / 2);
        int y = (int) ((dimension.getHeight() - getHeight()) / 2);
        setLocation(x, y);
    }
}
