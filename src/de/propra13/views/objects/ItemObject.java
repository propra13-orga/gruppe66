package de.propra13.views.objects;

import de.propra13.assets.animations.Animation;
import de.propra13.models.Item;

public abstract class ItemObject extends GameObject {

    public ItemObject(Animation defaultAnimation, int x, int y) {
        super(defaultAnimation, x, y);
    }

    protected Item item;

    public Item getItem() {
        return item;
    }

}
