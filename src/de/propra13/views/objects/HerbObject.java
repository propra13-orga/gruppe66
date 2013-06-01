package de.propra13.views.objects;

import de.propra13.assets.animations.Animation;
import de.propra13.models.Theme;

public class HerbObject extends ItemObject {

    public HerbObject(int x, int y, Theme theme) {
        super(new Animation(theme.getHerbImage(), 1, 8), x, y);
    }
}
