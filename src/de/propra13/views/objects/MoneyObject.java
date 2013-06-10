package de.propra13.views.objects;

import de.propra13.assets.Theme;
import de.propra13.assets.animations.Animation;
import de.propra13.models.Money;

public class MoneyObject extends ItemObject {

    public MoneyObject(Money money, int x, int y, Theme theme) {
        super(new Animation(theme.getOnehundretImage(), 1, 8, 0x1f160d), x, y);
        this.item = money;
    }

}