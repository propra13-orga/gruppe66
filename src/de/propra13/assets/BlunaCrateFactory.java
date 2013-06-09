package de.propra13.assets;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

public class BlunaCrateFactory {

    private static Map<BufferedImage, BlunaCrate> cache = new HashMap<>();

    public static BlunaCrate getBlunaCrate(BufferedImage image, int directions,
            int frames, int shadowRGB) {

        if (cache.containsKey(image)) {
            return cache.get(image);
        } else {
            BlunaCrate blunaCrate = new BlunaCrate(image, directions, frames,
                    shadowRGB);
            cache.put(image, blunaCrate);
            return blunaCrate;
        }
    }

    public static void clearCache() {
        cache.clear();
    }
}
