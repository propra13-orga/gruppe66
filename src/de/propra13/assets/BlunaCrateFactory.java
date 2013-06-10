package de.propra13.assets;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

public class BlunaCrateFactory {

    private static Map<BufferedImage, BlunaCrate> lorry = new HashMap<>();

    public static BlunaCrate getBlunaCrate(BufferedImage image, int directions,
            int frames, int shadowRGB) {

        if (lorry.containsKey(image)) {
            return lorry.get(image);
        } else {
            BlunaCrate blunaCrate = new BlunaCrate(image, directions, frames,
                    shadowRGB);
            lorry.put(image, blunaCrate);
            return blunaCrate;
        }
    }

    public static void clearCache() {
        lorry.clear();
    }
}
