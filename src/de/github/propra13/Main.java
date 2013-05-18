package de.github.propra13;

import java.awt.CardLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import de.github.propra13.controllers.GameController;
import de.github.propra13.controllers.MenuController;

public class Main extends JFrame {

    private static final long serialVersionUID = -4373574916623551121L;

    public static final int WIDTH = 1024;
    public static final int HEIGHT = 768;

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
        initRootWindow();
        initRootPanel();

        createViewsFor((JPanel) getContentPane());
    }

    private void initRootWindow() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle("Keiler Crawler");
        setSize(Main.WIDTH, Main.HEIGHT);
        setResizable(false);
        setVisible(true);
    }

    private void initRootPanel() {
        JPanel panel = new JPanel(new CardLayout());
        panel.setOpaque(true);
        setContentPane(panel);
    }

    private void createViewsFor(JPanel panel) {
        MenuController menuController = new MenuController(this);
        menuController.appendTo(panel);

        GameController gameController = new GameController(this);
        gameController.appendTo(panel);
    }
}
