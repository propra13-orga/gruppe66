package de.github.propra13.views;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.geom.Ellipse2D;

import javax.swing.JPanel;

public class GameFieldView extends JPanel implements Runnable {

    private static final long serialVersionUID = 7383103785685757479L;

    private int x = 0;
    private int y = 0;
    private int vx = 0;
    private int vy = 0;

    /**
     * delay in ms between animation loops. Defaults to 40 ms which is 25 fps.
     */
    private int delay = 40;

    private boolean running;
    private Thread animator;

    public GameFieldView() {
        setFocusable(true);
        setDoubleBuffered(true);
        animator = new Thread(this);
    }

    @Override
    public void addNotify() {
        super.addNotify();
        running = true;
        animator.start();
    }

    @Override
    public void removeNotify() {
        running = false;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponents(g);
        requestFocusInWindow();

        Dimension dim = getSize();
        Graphics2D gfx = (Graphics2D) g;

        RenderingHints rh = new RenderingHints(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        rh.put(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY);

        gfx.setRenderingHints(rh);

        gfx.setPaint(Color.blue);
        gfx.setBackground(Color.white);
        gfx.clearRect(0, 0, dim.width, dim.height);

        gfx.fill(new Ellipse2D.Double(x, y, 20, 20));

        Toolkit.getDefaultToolkit().sync();

        int cVx = vx, cVy = vy;
        if (x + 20 + vx > dim.width || x + vx < 0)
            cVx = 0;
        if (y + 20 + vy > dim.height || y + vy < 0)
            cVy = 0;

        x += cVx;
        y += cVy;
    }

    public void setVx(int vx) {
        this.vx = vx;
    }

    public void setVy(int vy) {
        this.vy = vy;
    }

    public void stop() {
        running = false;
    }

    @Override
    public void run() {
        long wait, oldTime = System.currentTimeMillis();
        while (running) {
            repaint();

            wait = Math.max(delay - System.currentTimeMillis() - oldTime, 2);
            try {
                Thread.sleep(wait);
            } catch (InterruptedException e) {
            }

            oldTime = System.currentTimeMillis();
        }
    }
}
