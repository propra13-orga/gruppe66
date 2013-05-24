package de.propra13.views.objects;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.ImageObserver;

import de.propra13.controllers.GameController;
import de.propra13.models.Player;
import de.propra13.models.Room;

public class PlayerObject extends MoveableGameObject {

    private boolean leftSpawnPoint = false;
    private GameController controller;

    private Player player;

    public PlayerObject(Player player, int x, int y, Theme theme) {
        super(theme.getPlayerImage(), x, y);
        this.player = player;
    }

    public void move(Dimension size, Room room) {
        super.move(size, room);

        if (isOnGoalIn(room) && leftSpawnPoint)
            controller.advanceRoom();
        if (isOnStartIn(room) && leftSpawnPoint)
            controller.retreatRoom();

        if (!leftSpawnPoint && !isOnStartIn(room) && !isOnGoalIn(room))
            leftSpawnPoint = true;
    }

    @Override
    public void draw(Graphics2D gfx, ImageObserver ob) {
        super.draw(gfx, ob);
        if (getPlayer().isWounded())
            drawPlayerHealthBar(gfx);
    }

    private void drawPlayerHealthBar(Graphics2D gfx) {
        double health = getPlayer().getHealth();
        int width = (int) (getWidth() * (health / 100));
        int height = 2;

        gfx.setPaint(Color.black);
        gfx.fillRect(getX(), getY() - height, getWidth(), height);

        gfx.setPaint(healthColor(health));
        gfx.fillRect(getX(), getY() - height, width, height);
    }

    private Color healthColor(double health) {
        health = Math.max(0, health);
        Color green = Color.green;
        Color yellow = Color.yellow;
        Color orange = new Color(0xff, 0x99, 0x00);
        Color red = Color.red;

        if (health >= 70)
            return mixColor(green, yellow, health, 100, 70);
        if (health < 70 && health >= 40)
            return mixColor(yellow, orange, health, 70, 40);

        return mixColor(orange, red, health, 40, 0);
    }

    private Color mixColor(Color c1, Color c2, double health, int high, int low) {
        double ratio = (health - low) / (high - low);

        int r = (int) (c1.getRed() * ratio + c2.getRed() * (1 - ratio));
        int g = (int) (c1.getGreen() * ratio + c2.getGreen() * (1 - ratio));
        int b = (int) (c1.getBlue() * ratio + c2.getBlue() * (1 - ratio));

        return new Color(r, g, b);
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

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
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
