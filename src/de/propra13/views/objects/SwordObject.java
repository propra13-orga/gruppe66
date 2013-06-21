package de.propra13.views.objects;

import de.propra13.assets.Theme;
import de.propra13.assets.animations.Animation;
import de.propra13.models.Sword;

public class SwordObject extends ItemObject<Sword> {

    public SwordObject(Sword sword, int x, int y, Theme theme) {
        super(sword, new Animation(theme.getSwordBluna()), x, y);
    }
}
