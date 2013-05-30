package de.propra13.assets;

import java.awt.image.BufferedImage;

import de.propra13.views.objects.Direction;

public class BlunaCrate {

    private int frames;
    private Bluna[] blunas;

    public BlunaCrate(BufferedImage image, int directions, int frames) {
        blunas = readBlunas(image, directions, frames);
        this.frames = frames;
    }

    private Bluna[] readBlunas(BufferedImage image, int directions, int frames) {
        Bluna[] animation = new Bluna[frames * directions];

        int width = image.getWidth() / frames;
        int height = image.getHeight() / directions;

        for (int i = 0; i < frames * directions; i++) {
            int x = (i % frames) * width;
            int y = (i / frames) * height;
            BufferedImage subImage = image.getSubimage(x, y, width, height);
            animation[i] = new Bluna(subImage);
        }
        return animation;
    }

    private int abstractNumber(int x) {
        if (x == 0) {
            return 0;
        }
        if (x < 0) {
            return -1;
        }
        return 1;
    }

    public Bluna getBluna(Direction direction, int frameIndex) {
        int vx3 = abstractNumber(direction.getVx()) + 1;
        int vy3 = abstractNumber(direction.getVy()) + 1;
        int v3 = 3 * vx3 + vy3;
        if (v3 > 4)
            v3--;
        return blunas[v3 * frames + frameIndex];
    }

    public Bluna getBluna(Direction direction) {
        return getBluna(direction, 0);
    }

    public Bluna getBluna(int index) {
        return blunas[index];
    }

    public Bluna getFirstBluna() {
        return getBluna(0);
    }
}
