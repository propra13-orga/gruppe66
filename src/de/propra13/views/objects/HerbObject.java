package de.propra13.views.objects;

import de.propra13.assets.Theme;
import de.propra13.assets.animations.Animation;
import de.propra13.models.Herb;

public class HerbObject extends ItemObject {

    public HerbObject(Herb herb, int x, int y, Theme theme) {
        super(new Animation(theme.getHerbBluna()), x, y);
        this.item = herb;
    }
}
