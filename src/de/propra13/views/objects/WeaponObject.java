package de.propra13.views.objects;

import de.propra13.models.Theme;
import de.propra13.models.Weapon;

public class WeaponObject extends ItemObject {

    public WeaponObject(Weapon weapon, int x, int y, Theme theme) {
        super(theme.getWeaponImage(), x, y);
        this.item = weapon;
    }

}
