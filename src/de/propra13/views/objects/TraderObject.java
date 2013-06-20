package de.propra13.views.objects;

import de.propra13.assets.Theme;
import de.propra13.assets.animations.Animation;
import de.propra13.models.Trader;

public class TraderObject extends NpcObject {

    public TraderObject(Trader trader, int x, int y, Theme theme) {
        super(trader, new Animation(theme.getTraderBlunas().get("stands")
                .get("default")), x, y);

        addAnimations("default", theme.getTraderBlunas());
    }
}
