package de.propra13.views.objects;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.ImageObserver;

import de.propra13.views.GameFieldView;

public class GameObject {
    protected int x;
    protected int y;

    protected int width;
    protected int height;

    protected Image image;

    public GameObject(int x, int y, int width, int height) {
        this.x = Math
                .max(scale(x), scale(x) - (width - GameFieldView.GRID) / 2);
        this.y = Math.max(scale(y), scale(y) - (height - GameFieldView.GRID)
                / 2);
        this.width = width;
        this.height = height;
    }

    public GameObject(Image image, int x, int y) {
        this(x, y, image.getWidth(null), image.getHeight(null));
        this.image = image;
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

    public Image getImage() {
        return image;
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

    protected int scale(int x) {
        return (x * GameFieldView.GRID);
    }
}
