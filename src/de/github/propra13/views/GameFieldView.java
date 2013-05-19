package de.github.propra13.views;

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

import de.github.propra13.Main;
import de.github.propra13.objects.Player;
import de.github.propra13.objects.Wall;

public class GameFieldView extends JPanel implements Runnable {

    public static final int GRID = 50;

    private static final long serialVersionUID = 7383103785685757479L;

    private Player player;

    private ArrayList<Wall> walls;

    /**
     * delay in ms between animation loops. Defaults to 40 ms which is 25 fps.
     */
    private int delay = 40;

    private volatile boolean running;
    private Thread animator;

    private RenderingHints rh;

    private boolean drawsGrid = false;

    public GameFieldView() {
        super();

        initComponent();
        initRenderingHints();
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

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public ArrayList<Wall> getWalls() {
        return walls;
    }

    public void setWalls(ArrayList<Wall> walls) {
        this.walls = walls;
    }

    @Override
    public void removeNotify() {
        super.removeNotify();
        stop();
    }

    public void turn() {
        player.move(walls);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponents(g);

        Graphics2D gfx = (Graphics2D) g;

        clearAndSetRenderingHints(gfx);
        drawFloor(gfx);

        drawPlayer(gfx);
        drawWalls(gfx);

        if (drawsGrid)
            drawGrid(gfx);

        Toolkit.getDefaultToolkit().sync();
        g.dispose();
    }

    private void clearAndSetRenderingHints(Graphics2D gfx) {
        Dimension dim = getSize();
        gfx.setBackground(Color.white);
        gfx.clearRect(0, 0, dim.width, dim.height);

        gfx.setRenderingHints(rh);
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
        gfx.drawImage(player.getImage(), player.getX(), player.getY(), this);
    }

    private void drawWalls(Graphics2D gfx) {
        for (Wall wall : walls) {
            gfx.drawImage(wall.getImage(), wall.getX(), wall.getY(), this);
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

            wait = Math.max(delay - System.currentTimeMillis() - oldTime, 2);

            oldTime = System.currentTimeMillis();
            try {
                Thread.sleep(wait);
            } catch (InterruptedException e) {
                return;
            }
        }
    }
}
