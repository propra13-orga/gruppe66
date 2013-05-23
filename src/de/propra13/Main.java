package de.propra13;

import java.awt.CardLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import de.propra13.controllers.GameController;
import de.propra13.controllers.LostController;
import de.propra13.controllers.MenuController;
import de.propra13.controllers.WinController;

public class Main extends JFrame {

    private static final long serialVersionUID = -4373574916623551121L;

    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;

    public static final String ROOTVIEW = "rootView";
    public static final String GAMEVIEW = "gameView";

    public static void main(String args[]) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Main();
            }
        });
    }

    public Main() {
        getContentPane().setLayout(new CardLayout());
        getContentPane().setPreferredSize(
                new Dimension(Main.WIDTH, Main.HEIGHT));
        createViewsFor((JPanel) getContentPane());
        initRootWindow();
    }

    private void initRootWindow() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle("Keiler Crawler");
        setMinimumSize(new Dimension(Main.WIDTH, Main.HEIGHT));
        setResizable(false);
        setVisible(true);
        pack();
    }

    private void createViewsFor(JPanel panel) {
        MenuController menuController = new MenuController(this);
        menuController.appendViewTo(panel);

        GameController gameController = new GameController(this);
        gameController.appendViewTo(panel);

        LostController lostController = new LostController(this);
        lostController.appendViewTo(panel);

        WinController winController = new WinController(this);
        winController.appendViewTo(panel);
    }
}