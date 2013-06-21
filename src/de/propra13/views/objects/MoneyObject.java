package de.propra13.views.objects;

import de.propra13.assets.Theme;
import de.propra13.assets.animations.Animation;
import de.propra13.models.Money;

public class MoneyObject extends ItemObject<Money> {

    public MoneyObject(Money money, int x, int y, Theme theme) {
        super(money, new Animation(theme.getOnehundretBluna()), x, y);
    }

}
