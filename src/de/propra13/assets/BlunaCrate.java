package de.propra13.assets;

import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.HashSet;

public class BlunaCrate {

    private BufferedImage[] blunas;
    private HashSet<Point> pointMask;
    private Rectangle bounds;

    public BlunaCrate(BufferedImage image, int subsets, int frames) {
        pointMask = new HashSet<>();
        blunas = readBlunas(image, subsets, frames);
        createBounds();
    }

    private BufferedImage[] readBlunas(BufferedImage image, int subsets,
            int frames) {
        BufferedImage[] blunas = new BufferedImage[frames * subsets];

        int width = image.getWidth() / frames;
        int height = image.getHeight() / subsets;

        for (int i = 0; i < frames * subsets; i++) {
            int x = (i % frames) * width;
            int y = (i / frames) * height;
            BufferedImage subImage = image.getSubimage(x, y, width, height);
            pointMask.addAll(createPointMaskFrom(subImage));
            blunas[i] = subImage;
        }
        return blunas;
    }

    private HashSet<Point> createPointMaskFrom(BufferedImage image) {
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

    public void createBounds() {
        Polygon shape = new Polygon();
        for (Point p : pointMask) {
            shape.addPoint(p.x, p.y);
        }
        bounds = shape.getBounds();
    }

    public Rectangle getBounds() {
        return bounds;
    }

}
