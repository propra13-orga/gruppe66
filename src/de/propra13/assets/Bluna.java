package de.propra13.assets;

import java.awt.image.BufferedImage;

public class Bluna {

    private int frames;
    private BufferedImage[] images;

    public Bluna(BufferedImage image, int directions, int frames) {
        images = readBlunas(image, directions, frames);
        this.frames = frames;
    }

    private BufferedImage[] readBlunas(BufferedImage image, int directions,
            int frames) {
        BufferedImage[] animation = new BufferedImage[frames * directions];

        new BufferedImage(frames, frames, frames);
        int width = image.getWidth() / frames;
        int height = image.getHeight() / directions;

        for (int i = 0; i < frames * directions; i++) {
            int x = (i % frames) * width;
            int y = (i / frames) * height;
            animation[i] = image.getSubimage(x, y, width, height);
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

    public BufferedImage getBluna(int vx, int vy, int frameIndex) {
        int vx3 = abstractNumber(vx) + 1;
        int vy3 = abstractNumber(vy) + 1;
        int v3 = 3 * vx3 + vy3;
        if (v3 > 4)
            v3--;
        return images[v3 * frames + frameIndex];
    }
}
