package de.propra13.views.objects;

import de.propra13.assets.Theme;
import de.propra13.assets.animations.Animation;

public class WallObject extends GameObject {

    public WallObject(int x, int y, Theme theme) {
        super(new Animation(theme.getWallBluna()), x, y);
    }

}
