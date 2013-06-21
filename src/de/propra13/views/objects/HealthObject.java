package de.propra13.views.objects;

import de.propra13.assets.Theme;
import de.propra13.assets.animations.Animation;
import de.propra13.models.Health;

public class HealthObject extends ItemObject<Health> {

    public HealthObject(Health health, int x, int y, Theme theme) {
        super(health, new Animation(theme.getHealthBluna()), x, y);
    }
}
