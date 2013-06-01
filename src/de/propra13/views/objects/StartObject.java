package de.propra13.views.objects;

import de.propra13.assets.animations.Animation;
import de.propra13.models.Theme;

public class StartObject extends GameObject {

    public StartObject(int x, int y, Theme theme) {
        super(new Animation(theme.getStartImage(), 1, 1), x, y);
    }

}
