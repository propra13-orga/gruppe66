package de.propra13.views.objects;

import java.awt.Image;
import java.awt.Rectangle;

import javax.swing.ImageIcon;

import de.propra13.views.GameFieldView;

public class GameObject {
    protected int x;
    protected int y;

    protected int width;
    protected int height;

    protected Image image;

    public GameObject(String imageName) {
        this(imageName, 1, 1);
    }

    public GameObject(String imageName, int x, int y) {
        ImageIcon icon = new ImageIcon(imageName);
        image = icon.getImage();

        width = image.getWidth(null);
        height = image.getHeight(null);

        this.x = Math
                .max(scale(x), scale(x) - (width - GameFieldView.GRID) / 2);
        this.y = Math.max(scale(y), scale(y) - (height - GameFieldView.GRID)
                / 2);
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
