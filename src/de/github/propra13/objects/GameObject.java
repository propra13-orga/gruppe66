package de.github.propra13.objects;

import java.awt.Image;
import java.awt.Rectangle;

import javax.swing.ImageIcon;

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

        this.x = x * width;
        this.y = y * height;
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
}
