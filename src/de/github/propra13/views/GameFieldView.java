package de.github.propra13.views;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JComponent;

public class GameFieldView extends JComponent {

    private static final long serialVersionUID = 7383103785685757479L;

    private int x = 0;
    private int y = 0;
    private int vx = 0;
    private int vy = 0;

    public GameFieldView(int width, int height) {
        setPreferredSize(new Dimension(width, height));
        setFocusable(true);
        setDoubleBuffered(true);

        Timer t = new Timer();
        t.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                repaint(10);
            }
        }, 0, 10);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponents(g);
        requestFocusInWindow();

        Dimension dim = getSize();
        BufferedImage buffer = (BufferedImage) this.createImage(dim.width,
                dim.height);
        Graphics2D gfx = buffer.createGraphics();

        gfx.setPaint(Color.blue);
        gfx.setBackground(Color.white);
        gfx.clearRect(0, 0, dim.width, dim.height);

        gfx.fill(new Rectangle2D.Double(x, y, 20, 20));

        g.drawImage(buffer, 0, 0, this);

        int cVx = vx, cVy = vy;
        if (x + 20 + vx > dim.width || x + vx < 0)
            cVx = 0;
        if (y + 20 + vy > dim.height || y + vy < 0)
            cVy = 0;

        x += cVx;
        y += cVy;
    }

    public void setVx(int vx) {
        this.vx = vx * 3;
    }

    public void setVy(int vy) {
        this.vy = vy * 3;
    }
}
