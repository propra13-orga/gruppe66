package de.github.propra13.objects;

import java.awt.Dimension;
import java.awt.event.KeyEvent;

import de.github.propra13.controllers.GameController;
import de.github.propra13.models.Room;

public class PlayerObject extends MoveableGameObject {

    private boolean leftSpawnPoint = false;
    private GameController controller;

    public PlayerObject(int x, int y) {
        super("res/player.png", x, y);
    }

    public void move(Dimension size, Room room) {
        super.move(size, room);

        if (isOnGoalIn(room) && leftSpawnPoint)
            controller.advanceLevel();
        if (isOnStartIn(room) && leftSpawnPoint)
            controller.retreatLevel();

        if (!leftSpawnPoint && !isOnStartIn(room) && !isOnGoalIn(room))
            leftSpawnPoint = true;
    }

    public void setController(GameController controller) {
        this.controller = controller;
    }

    public void setMoved(boolean moved) {
        this.leftSpawnPoint = moved;
    }

    private boolean isOnGoalIn(Room room) {
        return room.getGoal().getBounds().contains(getBounds());
    }

    private boolean isOnStartIn(Room room) {
        return room.getStart().getBounds().contains(getBounds());
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
            if (vy == -1)
                vy = 0;
            break;
        case KeyEvent.VK_DOWN:
            if (vy == 1)
                vy = 0;
            break;
        case KeyEvent.VK_RIGHT:
            if (vx == 1)
                vx = 0;
            break;
        case KeyEvent.VK_LEFT:
            if (vx == -1)
                vx = 0;
            break;
        }
    }
}
