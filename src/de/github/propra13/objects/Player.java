package de.github.propra13.objects;

import java.awt.Image;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;

public class Player {

    private String player = "res/player.png";

    private int vx;
    private int vy;
    private int x;
    private int y;

    private Image image;

    public Player() {
        ImageIcon icon = new ImageIcon(player);
        image = icon.getImage();
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Image getImage() {
        return image;
    }

    public void move() {
        x += vx;
        y += vy;
    }

    public void keyPressed(KeyEvent event) {
        switch (event.getKeyCode()) {
        case KeyEvent.VK_UP:
            vy = -1;
            break;
        case KeyEvent.VK_RIGHT:
            vx = 1;
            break;
        case KeyEvent.VK_DOWN:
            vy = 1;
            break;
        case KeyEvent.VK_LEFT:
            vx = -1;
            break;
        }
    }

    public void keyReleased(KeyEvent event) {
        switch (event.getKeyCode()) {
        case KeyEvent.VK_UP:
        case KeyEvent.VK_DOWN:
            vy = 0;
            break;
        case KeyEvent.VK_RIGHT:
        case KeyEvent.VK_LEFT:
            vx = 0;
            break;
        }
    }
}
