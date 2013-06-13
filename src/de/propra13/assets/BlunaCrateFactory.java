package de.propra13.assets;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

public class BlunaCrateFactory {

    private static Map<BufferedImage, BlunaCrate> lorry = new HashMap<>();

    public static BlunaCrate getBlunaCrate(BufferedImage image, int directions,
            int frames, Rectangle bounds) {
        return getBlunaCrate(image, directions, frames, 0, bounds);
    }

    public static BlunaCrate getBlunaCrate(BufferedImage image, int directions,
            int frames, int shadowRGB) {
        return getBlunaCrate(image, directions, frames, shadowRGB, null);
    }

    private static BlunaCrate getBlunaCrate(BufferedImage image,
            int directions, int frames, int shadowRGB, Rectangle bounds) {
        if (!lorry.containsKey(image)) {
            createBlunaCrate(image, directions, frames, shadowRGB, bounds);
        }

        return lorry.get(image);
    }

    public static void createBlunaCrate(BufferedImage image, int directions,
            int frames, Rectangle bounds) {
        createBlunaCrate(image, directions, frames, 0, bounds);
    }

    public static void createBlunaCrate(BufferedImage image, int directions,
            int frames, int shadowRGB) {
        createBlunaCrate(image, directions, frames, shadowRGB, null);
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

    public static void clearCache() {
        lorry.clear();
    }
}
