package de.propra13.views.objects;

import de.propra13.assets.Theme;
import de.propra13.assets.animations.Animation;
import de.propra13.models.Health;

public class HealthObject extends ItemObject {

    public HealthObject(Health health, int x, int y, Theme theme) {
        super(new Animation(theme.getHealthBluna()), x, y);
        this.item = health;
    }
}
