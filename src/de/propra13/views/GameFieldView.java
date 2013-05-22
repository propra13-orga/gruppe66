package de.propra13.views;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import de.propra13.Main;
import de.propra13.controllers.GameController;
import de.propra13.models.Room;
import de.propra13.views.objects.FireballObject;
import de.propra13.views.objects.GameObject;
import de.propra13.views.objects.GoalObject;
import de.propra13.views.objects.PlayerObject;
import de.propra13.views.objects.StartObject;
import de.propra13.views.objects.WallObject;

public class GameFieldView extends JPanel implements Runnable {

    public static final int GRID = 50;

    private static final long serialVersionUID = 7383103785685757479L;

    private StartObject start;
    private GoalObject goal;
    private PlayerObject playerObject;

    private ArrayList<WallObject> walls;
    private ArrayList<FireballObject> balls;

    private Room currentRoom;

    private int delay = 10;

    private volatile boolean running;
    private Thread animator;

    private RenderingHints rh;

    private boolean drawsGrid = false;

    private GameController controller;

    public GameFieldView(GameController controller) {
        super();

        initComponent();
        initRenderingHints();

        this.controller = controller;
    }

    private void initComponent() {
        setFocusable(true);
        setDoubleBuffered(true);
    }

    private void initRenderingHints() {
        rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        rh.put(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY);
    }

    public Room getCurrentRoom() {
        return currentRoom;
    }

    public void setCurrentRoom(Room room) {
        this.currentRoom = room;
        this.start = currentRoom.getStart();
        this.goal = currentRoom.getGoal();
        this.playerObject = currentRoom.getPlayerObject();
        this.walls = currentRoom.getWalls();
        this.balls = currentRoom.getBalls();

        this.playerObject.setMoved(false);
    }

    @Override
    public void removeNotify() {
        super.removeNotify();
        stop();
    }

    public void turn() {
        playerObject.move(getSize(), currentRoom);

        for (FireballObject ball : balls) {
            ball.move(getSize(), currentRoom, playerObject);
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponents(g);
        if (running) {
            Graphics2D gfx = (Graphics2D) g;

            clearAndSetRenderingHints(gfx);

            drawStart(gfx);
            drawGoal(gfx);
            drawWalls(gfx);

            drawPlayer(gfx);
            drawBalls(gfx);

            if (drawsGrid)
                drawGrid(gfx);

            Toolkit.getDefaultToolkit().sync();
        }
        g.dispose();
    }

    private void drawStart(Graphics2D gfx) {
        drawGameObject(gfx, start);
    }

    private void drawGoal(Graphics2D gfx) {
        drawGameObject(gfx, goal);
    }

    private void clearAndSetRenderingHints(Graphics2D gfx) {
        Dimension dim = getSize();
        gfx.setBackground(Color.white);
        gfx.clearRect(0, 0, dim.width, dim.height);

        gfx.setRenderingHints(rh);

        drawFloor(gfx);
    }

    private void drawFloor(Graphics2D gfx) {
        Dimension dim = getSize();
        ImageIcon icon = new ImageIcon("res/floor.jpg");
        Image image = icon.getImage();
        int width = image.getWidth(this);
        int height = image.getHeight(this);

        for (int x = 0; x <= dim.width / width; x++) {
            for (int y = 0; y <= dim.height / height; y++) {
                gfx.drawImage(image, x * width, y * height, this);
            }
        }
    }

    private void drawPlayer(Graphics2D gfx) {
        drawGameObject(gfx, playerObject);
        if (playerObject.getPlayer().isWounded())
            drawPlayerHealthBar(gfx, playerObject);
    }

    private void drawPlayerHealthBar(Graphics2D gfx, PlayerObject playerObject) {
        double health = playerObject.getPlayer().getHealth();
        int width = (int) (playerObject.getWidth() * (health / 100));
        int height = 2;

        gfx.setPaint(Color.black);
        gfx.fillRect(playerObject.getX(), playerObject.getY() - height,
                playerObject.getWidth(), height);

        gfx.setPaint(healthColor(health));
        gfx.fillRect(playerObject.getX(), playerObject.getY() - height, width,
                height);
    }

    private Color healthColor(double health) {
        health = Math.max(0, health);
        Color green = Color.green;
        Color yellow = Color.yellow;
        Color orange = new Color(0xff, 0x99, 0x00);
        Color red = Color.red;

        if (health >= 70) {
            return mixColor(green, yellow, (health - 70) / (100 - 70));
        }

        if (health < 70 && health >= 40) {
            return mixColor(yellow, orange, (health - 30) / (70 - 40));
        }

        return mixColor(orange, red, health / 40);
    }

    private Color mixColor(Color c1, Color c2, double ratio) {

        int r = (int) (c1.getRed() * ratio + c2.getRed() * (1 - ratio));
        int g = (int) (c1.getGreen() * ratio + c2.getGreen() * (1 - ratio));
        int b = (int) (c1.getBlue() * ratio + c2.getBlue() * (1 - ratio));

        return new Color(r, g, b);
    }

    private void drawGameObject(Graphics2D gfx, GameObject o) {
        gfx.drawImage(o.getImage(), o.getX(), o.getY(), this);
    }

    private void drawWalls(Graphics2D gfx) {
        for (WallObject wall : walls) {
            drawGameObject(gfx, wall);
        }
    }

    private void drawBalls(Graphics2D gfx) {
        for (FireballObject ball : balls) {
            drawGameObject(gfx, ball);
        }
    }

    private void drawGrid(Graphics2D gfx) {
        gfx.setPaint(Color.WHITE);
        Dimension dim = getSize();
        for (int x = GRID; x < dim.width; x += GRID)
            gfx.drawLine(x, 0, x, Main.HEIGHT);
        for (int y = GRID; y < dim.height; y += GRID)
            gfx.drawLine(0, y, Main.WIDTH, y);
    }

    public void stop() {
        running = false;
        if (animator != null)
            animator.interrupt();
    }

    public void start() {
        requestFocusInWindow();
        running = true;

        animator = new Thread(this);
        animator.start();
    }

    @Override
    public void run() {
        long wait, oldTime = System.currentTimeMillis();
        while (running) {
            turn();
            repaint();

            controller.checkHealthOfPlayer();

            wait = Math.max(delay - (System.currentTimeMillis() - oldTime), 2);

            oldTime = System.currentTimeMillis();
            try {
                Thread.sleep(wait);
            } catch (InterruptedException e) {
                return;
            }
        }
    }
}
