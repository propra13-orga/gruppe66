package de.propra13.views.objects;

import de.propra13.assets.animations.Animation;
import de.propra13.models.Theme;

public class WallObject extends GameObject {

    public WallObject(int x, int y, Theme theme) {
        super(new Animation(theme.getWallImage(), 1, 1, 0x1f160d), x, y);
    }

}
