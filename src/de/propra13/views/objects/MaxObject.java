package de.propra13.views.objects;

import de.propra13.assets.Theme;
import de.propra13.assets.animations.Animation;
import de.propra13.models.Max;

public class MaxObject extends NpcObject<Max> {

    public MaxObject(Max max, int x, int y, Theme theme) {
        super(max, new Animation(theme.getMaxBlunas().get("stands")
                .get("default")), x, y);

        addAnimations("default", theme.getMaxBlunas());
    }
}
