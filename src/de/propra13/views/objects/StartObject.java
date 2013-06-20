package de.propra13.views.objects;

import de.propra13.assets.Theme;
import de.propra13.assets.animations.Animation;

public class StartObject extends DoorObject {

    public StartObject(int x, int y, Theme theme) {
        super(new Animation(theme.getStartBluna()), x, y);
    }
}
