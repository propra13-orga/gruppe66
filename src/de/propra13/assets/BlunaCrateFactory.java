package de.propra13.assets;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

class BlunaCrateFactory {

    private static Map<BufferedImage, BlunaCrate> lorry = new HashMap<>();

    static BlunaCrate getSimpleBlunaCrate(BufferedImage image) {
        return getBlunaCrate(image, 1, 1, 0);
    }

    static BlunaCrate getSimpleBlunaCrate(BufferedImage image, Rectangle bounds) {
        return getBlunaCrate(image, 1, 1, bounds);
    }

    static BlunaCrate getSimpleBlunaCrate(BufferedImage image, int shadowRGB) {
        return getBlunaCrate(image, 1, 1, shadowRGB);
    }

    static BlunaCrate getBlunaCrate(BufferedImage image, int directions,
            int frames, Rectangle bounds) {
        return getBlunaCrate(image, directions, frames, 0, bounds);
    }

    static BlunaCrate getBlunaCrate(BufferedImage image, int directions,
            int frames, int shadowRGB) {
        return getBlunaCrate(image, directions, frames, shadowRGB, null);
    }

    static BlunaCrate getBlunaCrate(BufferedImage image, int directions,
            int frames, int shadowRGB, Rectangle bounds) {
        if (!lorry.containsKey(image)) {
            createBlunaCrate(image, directions, frames, shadowRGB, bounds);
        }

        return lorry.get(image);
    }

    private static void createBlunaCrate(BufferedImage image, int directions,
            int frames, int shadowRGB, Rectangle bounds) {
        BlunaCrate blunaCrate = new BlunaCrate(image, directions, frames);
        if (bounds == null)
            blunaCrate.calculateBounds(shadowRGB);
        else
            blunaCrate.setBounds(bounds);
        lorry.put(image, blunaCrate);
    }

    static void clearCache() {
        lorry.clear();
    }
}
