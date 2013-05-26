package de.propra13.views.objects;

import java.awt.Image;

import de.propra13.models.Item;

public abstract class ItemObject extends GameObject {

    protected Item item;

    public ItemObject(Image image, int x, int y) {
        super(image, x, y);
    }

    public Item getItem() {
        return item;
    }

}
