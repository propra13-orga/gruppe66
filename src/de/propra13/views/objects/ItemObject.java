package de.propra13.views.objects;

import de.propra13.assets.animations.Animation;
import de.propra13.models.Item;

public abstract class ItemObject<I extends Item> extends GameObject {

    private I item;

    public ItemObject(I item, Animation defaultAnimation, int x, int y) {
        super(defaultAnimation, x, y);
        this.item = item;
    }

    public I getItem() {
        return item;
    }

}
