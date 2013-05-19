package de.github.propra13;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import de.github.propra13.controllers.GameController;
import de.github.propra13.controllers.MenuController;
import de.github.propra13.objects.Player;
import de.github.propra13.objects.Wall;

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
        getContentPane().setPreferredSize(new Dimension(Main.WIDTH, Main.HEIGHT));
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
        menuController.appendTo(panel);

        Player player = new Player(1, 1);
        ArrayList<Wall> walls = new ArrayList<Wall>();
        walls.add(new Wall(0, 0));
        walls.add(new Wall(1, 0));
        walls.add(new Wall(2, 0));
        walls.add(new Wall(3, 0));
        walls.add(new Wall(4, 0));
        walls.add(new Wall(5, 0));
        walls.add(new Wall(6, 0));
        walls.add(new Wall(7, 0));
        walls.add(new Wall(8, 0));
        walls.add(new Wall(9, 0));
        walls.add(new Wall(10, 0));
        walls.add(new Wall(11, 0));
        walls.add(new Wall(12, 0));
        walls.add(new Wall(13, 0));
        walls.add(new Wall(14, 0));
        walls.add(new Wall(15, 0));

        walls.add(new Wall(1, 11));
        walls.add(new Wall(2, 11));
        walls.add(new Wall(3, 11));
        walls.add(new Wall(4, 11));
        walls.add(new Wall(5, 11));
        walls.add(new Wall(6, 11));
        walls.add(new Wall(7, 11));
        walls.add(new Wall(8, 11));
        walls.add(new Wall(9, 11));
        walls.add(new Wall(10, 11));
        walls.add(new Wall(11, 11));
        walls.add(new Wall(12, 11));
        walls.add(new Wall(13, 11));
        walls.add(new Wall(14, 11));
        walls.add(new Wall(15, 11));

        walls.add(new Wall(0, 0));
        walls.add(new Wall(0, 1));
        walls.add(new Wall(0, 2));
        walls.add(new Wall(0, 3));
        walls.add(new Wall(0, 4));
        walls.add(new Wall(0, 5));
        walls.add(new Wall(0, 6));
        walls.add(new Wall(0, 7));
        walls.add(new Wall(0, 8));
        walls.add(new Wall(0, 9));
        walls.add(new Wall(0, 10));
        walls.add(new Wall(0, 11));

        walls.add(new Wall(15, 1));
        walls.add(new Wall(15, 2));
        walls.add(new Wall(15, 3));
        walls.add(new Wall(15, 4));
        walls.add(new Wall(15, 5));
        walls.add(new Wall(15, 6));
        walls.add(new Wall(15, 7));
        walls.add(new Wall(15, 8));
        walls.add(new Wall(15, 9));
        walls.add(new Wall(15, 10));
        walls.add(new Wall(15, 11));

        walls.add(new Wall(3, 1));
        walls.add(new Wall(3, 2));
        walls.add(new Wall(3, 3));
        walls.add(new Wall(3, 4));
        walls.add(new Wall(3, 5));
        walls.add(new Wall(3, 6));
        walls.add(new Wall(3, 7));
        walls.add(new Wall(3, 8));
        walls.add(new Wall(3, 9));
        walls.add(new Wall(5, 2));
        walls.add(new Wall(5, 3));
        walls.add(new Wall(5, 4));
        walls.add(new Wall(5, 5));
        walls.add(new Wall(5, 6));
        walls.add(new Wall(5, 7));
        walls.add(new Wall(5, 8));
        walls.add(new Wall(5, 9));
        walls.add(new Wall(5, 10));

        walls.add(new Wall(7, 1));
        walls.add(new Wall(7, 2));
        walls.add(new Wall(7, 3));
        walls.add(new Wall(7, 4));
        walls.add(new Wall(7, 5));
        walls.add(new Wall(7, 6));
        walls.add(new Wall(7, 7));
        walls.add(new Wall(7, 8));
        walls.add(new Wall(7, 9));
        walls.add(new Wall(9, 2));
        walls.add(new Wall(9, 3));
        walls.add(new Wall(9, 4));
        walls.add(new Wall(9, 5));
        walls.add(new Wall(9, 6));
        walls.add(new Wall(9, 7));
        walls.add(new Wall(9, 8));
        walls.add(new Wall(9, 9));
        walls.add(new Wall(9, 10));

        walls.add(new Wall(11, 1));
        walls.add(new Wall(11, 2));
        walls.add(new Wall(11, 3));
        walls.add(new Wall(11, 4));
        walls.add(new Wall(11, 5));
        walls.add(new Wall(11, 6));
        walls.add(new Wall(11, 7));
        walls.add(new Wall(11, 8));
        walls.add(new Wall(11, 9));
        walls.add(new Wall(13, 2));
        walls.add(new Wall(13, 3));
        walls.add(new Wall(13, 4));
        walls.add(new Wall(13, 5));
        walls.add(new Wall(13, 6));
        walls.add(new Wall(13, 7));
        walls.add(new Wall(13, 8));
        walls.add(new Wall(13, 9));
        walls.add(new Wall(13, 10));

        GameController gameController = new GameController(this);
        gameController.setPlayer(player);
        gameController.setWalls(walls);
        gameController.appendTo(panel);
    }
}
