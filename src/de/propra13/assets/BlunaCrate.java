package de.propra13.assets;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.HashSet;

public class BlunaCrate {

    private BufferedImage[] blunas;
    private Rectangle bounds;

    public BlunaCrate(BufferedImage image, int subsets, int frames) {
        blunas = readBlunas(image, subsets, frames);
        initBounds();
    }

    public void initBounds() {
        HashSet<Point> pointMask = new HashSet<>();
        for (BufferedImage bluna : blunas) {
            pointMask.addAll(createPointMaskFrom(bluna));
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

    private static HashSet<Point> createPointMaskFrom(BufferedImage image) {
        HashSet<Point> pointMask = new HashSet<>();
        int pixel, alpha, i, j;
        long rgb;

        for (i = 0; i < image.getWidth(); i++) {
            for (j = 0; j < image.getHeight(); j++) {
                pixel = image.getRGB(i, j);
                alpha = (pixel >> 24) & 0xff;
                rgb = pixel & (0xffffff);

                if (alpha != 0 && rgb != 0x1f160d) {
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

}
