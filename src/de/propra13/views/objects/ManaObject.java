package de.propra13.views.objects;

import de.propra13.assets.Theme;
import de.propra13.assets.animations.Animation;
import de.propra13.models.Mana;

public class ManaObject extends ItemObject<Mana> {

    public ManaObject(Mana mana, int x, int y, Theme theme) {
        super(mana, new Animation(theme.getManaBluna()), x, y);
    }
}
