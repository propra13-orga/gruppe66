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
        start.draw(gfx, this);
    }

    private void drawGoal(Graphics2D gfx) {
        goal.draw(gfx, this);
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
        playerObject.draw(gfx, this);

    }

    private void drawWalls(Graphics2D gfx) {
        for (WallObject wall : walls) {
            wall.draw(gfx, this);
        }
    }

    private void drawBalls(Graphics2D gfx) {
        for (FireballObject ball : balls) {
            ball.draw(gfx, this);
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
