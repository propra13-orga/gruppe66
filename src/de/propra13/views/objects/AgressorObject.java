package de.propra13.views.objects;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.ImageObserver;

import de.propra13.assets.animations.Animation;
import de.propra13.models.Agressor;

public abstract class AgressorObject extends MoveableGameObject {

    public AgressorObject(Animation defaultAnimation, int x, int y) {
        super(defaultAnimation, x, y);
    }

    @Override
    public void draw(Graphics2D gfx, ImageObserver ob) {
        super.draw(gfx, ob);
        if (getAgressor().isWounded())
            drawAgressorHealthBar(gfx);

    }

    protected abstract Agressor getAgressor();

    private void drawAgressorHealthBar(Graphics2D gfx) {
        double health = getAgressor().getHealth();
        int width = (int) (getWidth() * (health / 100));
        int height = 2;

        gfx.setPaint(Color.black);
        gfx.fillRect(getX(), getY() - height, getWidth(), height);

        gfx.setPaint(healthColor(health));
        gfx.fillRect(getX(), getY() - height, width, height);
    }

    private static Color healthColor(double health) {
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

    private static Color mixColor(Color c1, Color c2, double health, int high,
            int low) {
        double ratio = (health - low) / (high - low);

        int r = (int) (c1.getRed() * ratio + c2.getRed() * (1 - ratio));
        int g = (int) (c1.getGreen() * ratio + c2.getGreen() * (1 - ratio));
        int b = (int) (c1.getBlue() * ratio + c2.getBlue() * (1 - ratio));

        return new Color(r, g, b);
    }

}
