package de.propra13.assets;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.HashSet;

public class BlunaCrate {

    private int spriteWidth, spriteHeight;
    private BufferedImage[] blunas;
    private int frames;
    private Rectangle bounds;
    private boolean directable;

    BlunaCrate(BufferedImage image, int directions, int frames) {
        blunas = readBlunas(image, directions, frames);
        spriteWidth = image.getWidth() / frames;
        spriteHeight = image.getHeight() / directions;
        this.frames = frames;
        directable = checkDirectable(directions);
    }

    private static boolean checkDirectable(int directions) {
        return directions == 8;
    }

    public int getSpriteHeight() {
        return spriteHeight;
    }

    public int getSpriteWidth() {
        return spriteWidth;
    }

    public int getFrames() {
        return frames;
    }

    public void setBounds(Rectangle bounds) {
        this.bounds = bounds;
    }

    public void calculateBounds(int shadowRGB) {
        HashSet<Point> pointMask = new HashSet<>();
        for (BufferedImage bluna : blunas) {
            pointMask.addAll(createPointMaskFrom(bluna, shadowRGB));
        }
        bounds = createBoundsFrom(pointMask);
    }

    private static BufferedImage[] readBlunas(BufferedImage image, int subsets,
            int frames) {
        BufferedImage[] blunas = new BufferedImage[frames * subsets];

        int width = image.getWidth() / frames;
        int height = image.getHeight() / subsets;

        for (int i = 0; i < frames * subsets; i++) {
            int x = (i % frames) * width;
            int y = (i / frames) * height;
            BufferedImage subImage = image.getSubimage(x, y, width, height);
            blunas[i] = subImage;
        }
        return blunas;
    }

    private static HashSet<Point> createPointMaskFrom(BufferedImage image,
            int shadowRGB) {
        HashSet<Point> pointMask = new HashSet<>();
        int pixel, alpha, i, j;
        long rgb;

        for (i = 0; i < image.getWidth(); i++) {
            for (j = 0; j < image.getHeight(); j++) {
                pixel = image.getRGB(i, j);
                alpha = (pixel >> 24) & 0xff;
                rgb = pixel & (0xffffff);

                if (alpha != 0 && rgb != shadowRGB) {
                    pointMask.add(new Point(i, j));
                }
            }
        }
        return pointMask;
    }

    public BufferedImage getBluna(int index) {
        return blunas[index];
    }

    public BufferedImage getFirstBluna() {
        return getBluna(0);
    }

    public static Rectangle createBoundsFrom(HashSet<Point> pointMask) {
        int xmin = Integer.MAX_VALUE;
        int ymin = Integer.MAX_VALUE;
        int xmax = -1;
        int ymax = -1;
        for (Point p : pointMask) {
            if (p.x < xmin)
                xmin = p.x;
            if (p.y < ymin)
                ymin = p.y;
            if (p.x > xmax)
                xmax = p.x;
            if (p.y > ymax)
                ymax = p.y;
        }
        return new Rectangle(xmin, ymin, xmax - xmin, ymax - ymin);
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public boolean isDirectable() {
        return directable;
    }

}
