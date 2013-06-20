package de.propra13.views;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Toolkit;

import javax.swing.JPanel;

import de.propra13.assets.Theme;
import de.propra13.controllers.GameController;

public abstract class AbstractGameView extends JPanel {

    private static final long serialVersionUID = 7383103785685757479L;

    protected Theme theme;

    private RenderingHints rh;

    protected GameController controller;

    public AbstractGameView(GameController controller, Theme theme) {
        super();
        this.theme = theme;
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

    public Theme getTheme() {
        return theme;
    }

    @Override
    public void removeNotify() {
        super.removeNotify();
        controller.stop();
    }

    protected abstract void render(Graphics2D gfx);

    @Override
    public void paintComponent(Graphics g) {

        if (controller.isRunning()) {
            Graphics2D gfx = (Graphics2D) g;
            clearAndSetRenderingHints(gfx);
            render(gfx);

            super.paintComponents(g);
            Toolkit.getDefaultToolkit().sync();
        }
        g.dispose();
    }

    private void clearAndSetRenderingHints(Graphics2D gfx) {
        Dimension dim = getSize();
        gfx.setBackground(Color.white);
        gfx.clearRect(0, 0, dim.width, dim.height);

        gfx.setRenderingHints(rh);
    }

    public void setTheme(Theme theme) {
        this.theme = theme;
    }
}
