package de.propra13.views.objects;

import de.propra13.models.Theme;
import de.propra13.models.Weapon;

public class WeaponObject extends ItemObject {

    private Weapon weapon;

    public WeaponObject(Weapon weapon, int x, int y, Theme theme) {
        super(theme.getWeaponImage(), x, y);
        this.weapon = weapon;
    }

    public Weapon getWeapon() {
        return weapon;
    }

}
