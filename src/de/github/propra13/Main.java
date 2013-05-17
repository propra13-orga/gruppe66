package de.github.propra13;

import java.awt.CardLayout;
import java.util.HashSet;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import de.github.propra13.controllers.GameController;
import de.github.propra13.controllers.MenuController;

public class Main {

    private static JFrame rootWindow;

    public static final int WIDTH = 1024;
    public static final int HEIGHT = 768;

    public static final String ROOTVIEW = "rootView";
    public static final String GAMEVIEW = "gameView";

    public static HashSet<String> views = new HashSet<String>();

    public static void main(String args[]) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createRootWindow();

                JPanel panel = createRootPanel();
                rootWindow.setContentPane(panel);

                createViewsFor(panel);

                rootWindow.setVisible(true);
            }
        });
    }

    private static JPanel createRootPanel() {
        JPanel panel = new JPanel(new CardLayout());
        panel.setOpaque(true);
        return panel;
    }

    private static void createRootWindow() {
        rootWindow = new JFrame();
        rootWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        rootWindow.setTitle("Keiler Crawler");
        rootWindow.setSize(Main.WIDTH, Main.HEIGHT);
    }

    private static void createViewsFor(JPanel panel) {
        MenuController menuController = new MenuController(rootWindow);
        menuController.appendTo(panel);

        GameController gameController = new GameController(rootWindow);
        gameController.appendTo(panel);
    }
}
