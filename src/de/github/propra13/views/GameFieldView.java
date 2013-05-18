package de.github.propra13.views;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Toolkit;

import javax.swing.JPanel;

import de.github.propra13.objects.Player;

public class GameFieldView extends JPanel implements Runnable {

    private static final long serialVersionUID = 7383103785685757479L;

    private Player player;

    /**
     * delay in ms between animation loops. Defaults to 40 ms which is 25 fps.
     */
    private int delay = 40;

    private volatile boolean running;
    private Thread animator;

    private RenderingHints rh;

    public GameFieldView(int width, int height) {
        super();

        animator = new Thread(this);

        initComponent(width, height);
        initRenderingHints();
    }

    private void initComponent(int width, int height) {
        setPreferredSize(new Dimension(width, height));
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

    @Override
    public void removeNotify() {
        super.removeNotify();
        stop();
    }

    public void turn() {
        player.move();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponents(g);

        Graphics2D gfx = (Graphics2D) g;

        clearAndSetRenderingHints(gfx);

        gfx.drawImage(player.getImage(), player.getX(), player.getY(), this);

        Toolkit.getDefaultToolkit().sync();
        g.dispose();
    }

    private void clearAndSetRenderingHints(Graphics2D gfx) {
        Dimension dim = getSize();
        gfx.setBackground(Color.white);
        gfx.clearRect(0, 0, dim.width, dim.height);

        gfx.setRenderingHints(rh);
    }

    public void stop() {
        running = false;
        animator.interrupt();
    }

    public void start() {
        requestFocusInWindow();
        running = true;
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
            }
        }
    }
}
