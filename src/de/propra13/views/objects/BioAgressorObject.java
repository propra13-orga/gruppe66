package de.propra13.views.objects;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.ImageObserver;

import de.propra13.assets.animations.Animation;
import de.propra13.models.BioAgressor;

public abstract class BioAgressorObject extends MoveableGameObject {

    public BioAgressorObject(Animation defaultAnimation, int x, int y) {
        super(defaultAnimation, x, y);
        velocity = 0;
    }

    @Override
    public void draw(Graphics2D gfx, ImageObserver ob) {
        super.draw(gfx, ob);
        if (getAgressor().isWounded() && !getAgressor().isDead())
            drawAgressorHealthBar(gfx);

    }

    protected abstract BioAgressor getAgressor();

    private void drawAgressorHealthBar(Graphics2D gfx) {
        double health = getAgressor().getHealth();
        int width = (int) (getWidth() * (health / getAgressor().MAXHEALTH));

        drawBar(gfx, 1, width, healthColor(health, getAgressor().MAXHEALTH));
    }

    protected void drawBar(Graphics2D gfx, int level, int width, Color color) {
        int height = 2, offset = 5;
        gfx.setPaint(Color.black);
        gfx.fillRect((int) getX(), (int) (getY() - height * level - offset),
                (int) getWidth(), height);

        gfx.setPaint(color);
        gfx.fillRect((int) getX(), (int) (getY() - height * level - offset),
                width, height);
    }

    private static Color healthColor(double health, double maxhealth) {
        health = Math.max(0, health);
        Color green = Color.green;
        Color yellow = Color.yellow;
        Color orange = new Color(0xff, 0x99, 0x00);
        Color red = Color.red;

        double step = maxhealth / 3;
        double step1 = maxhealth - step;
        double step2 = maxhealth - 2 * step;

        if (health >= step1)
            return mixColor(green, yellow, health, maxhealth, maxhealth - step);
        if (health < step1 && health >= step2)
            return mixColor(yellow, orange, health, step1, step2);

        return mixColor(orange, red, health, step2, 0);
    }

    private static Color mixColor(Color c1, Color c2, double health,
            double high, double low) {
        double ratio = (health - low) / (high - low);

        int r = (int) (c1.getRed() * ratio + c2.getRed() * (1 - ratio));
        int g = (int) (c1.getGreen() * ratio + c2.getGreen() * (1 - ratio));
        int b = (int) (c1.getBlue() * ratio + c2.getBlue() * (1 - ratio));

        return new Color(r, g, b);
    }

}
