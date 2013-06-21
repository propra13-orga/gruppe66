package de.propra13.models;

import de.propra13.assets.Theme;
import de.propra13.assets.animations.Animation;
import de.propra13.views.objects.ItemObject;

public class ArmorObject extends ItemObject<Armor> {

    public ArmorObject(Armor armor, int x, int y, Theme theme) {
        super(armor, new Animation(theme.getArmorBluna()), x, y);
    }

}
