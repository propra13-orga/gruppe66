package de.github.propra13.objects;

import java.awt.event.KeyEvent;

public class Player extends MoveableGameObject {

    public Player(int x, int y) {
        super("res/player.png", x, y);
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