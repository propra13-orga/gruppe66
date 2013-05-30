package de.propra13.assets;

import java.awt.image.BufferedImage;

public class BlunaCrate {

    private Bluna[] blunas;

    public BlunaCrate(BufferedImage image, int subsets, int frames) {
        blunas = readBlunas(image, subsets, frames);
    }

    private Bluna[] readBlunas(BufferedImage image, int subsets, int frames) {
        Bluna[] blunas = new Bluna[frames * subsets];

        int width = image.getWidth() / frames;
        int height = image.getHeight() / subsets;

        for (int i = 0; i < frames * subsets; i++) {
            int x = (i % frames) * width;
            int y = (i / frames) * height;
            BufferedImage subImage = image.getSubimage(x, y, width, height);
            blunas[i] = new Bluna(subImage);
        }
        return blunas;
    }

    public Bluna getBluna(int index) {
        return blunas[index];
    }

    public Bluna getFirstBluna() {
        return getBluna(0);
    }
}
