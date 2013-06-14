package de.propra13.views.objects;

import de.propra13.assets.Theme;
import de.propra13.assets.animations.Animation;
import de.propra13.models.Sword;

public class SwordObject extends ItemObject {

    public SwordObject(Sword weapon, int x, int y, Theme theme) {
        super(new Animation(theme.getWeaponImage(), 1, 1, 0x1f160d), x, y);
        this.item = weapon;
    }

}
