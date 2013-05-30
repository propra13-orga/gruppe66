package de.propra13.views.objects;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.util.HashSet;

import de.propra13.assets.Bluna;
import de.propra13.assets.BlunaCrate;
import de.propra13.views.GameFieldView;

public class GameObject {
    protected int x;
    protected int y;

    protected int width;
    protected int height;

    protected BlunaCrate blunaCrate;
    protected int frames;
    protected int currentFrame = -1;

    protected Bluna currentBluna;

    public GameObject(int x, int y, int width, int height) {
        this.x = Math
                .max(scale(x), scale(x) - (width - GameFieldView.GRID) / 2);
        this.y = Math.max(scale(y), scale(y) - (height - GameFieldView.GRID)
                / 2);
        this.width = width;
        this.height = height;
    }

    public GameObject(BufferedImage image, int x, int y, int directions,
            int frames) {
        this(x, y, image.getWidth() / frames, image.getHeight() / directions);
        this.frames = frames;

        blunaCrate = new BlunaCrate(image, directions, frames);

        currentBluna = blunaCrate.getFirstBluna();
    }

    public void animate() {
        increaseFrame();
        currentBluna = blunaCrate.getBluna(currentFrame);
    }

    protected void increaseFrame() {
        currentFrame = ++currentFrame % frames;
    }

    public void draw(Graphics2D gfx, ImageObserver ob) {
        gfx.drawImage(getImage(), getX(), getY(), ob);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public BufferedImage getImage() {
        return currentBluna.getImage();
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

    protected int scale(int x) {
        return (x * GameFieldView.GRID);
    }

    public HashSet<Point> getPointMask() {
        return getPointMaskFrom(currentBluna);
    }

    protected HashSet<Point> getPointMaskFrom(Bluna bluna) {
        HashSet<Point> mask = new HashSet<>(bluna.getPointMask().size());
        for (Point p : bluna.getPointMask()) {
            mask.add(new Point(p.x + x, p.y + y));
        }
        return mask;
    }
}
