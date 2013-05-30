package de.propra13.assets;

import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.HashSet;

public class Bluna {

    private BufferedImage image;
    private HashSet<Point> pointMask;

    public Bluna(BufferedImage image) {
        this.image = image;
        this.pointMask = createPointMaskFrom(image);
    }

    private HashSet<Point> createPointMaskFrom(BufferedImage image) {
        HashSet<Point> pointMask = new HashSet<>();
        int pixel, alpha, i, j;

        for (i = 0; i < image.getWidth(); i++) {
            for (j = 0; j < image.getHeight(); j++) {
                pixel = image.getRGB(i, j);
                alpha = (pixel >> 24) & 0xff;

                if (alpha != 0) {
                    pointMask.add(new Point(i, j));
                }
            }
        }
        return pointMask;
    }

    public BufferedImage getImage() {
        return image;
    }

    public HashSet<Point> getPointMask() {
        return pointMask;
    }
}
