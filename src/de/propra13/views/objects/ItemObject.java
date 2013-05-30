package de.propra13.views.objects;

import java.awt.image.BufferedImage;

import de.propra13.models.Item;

public abstract class ItemObject extends GameObject {

    protected Item item;

    public ItemObject(BufferedImage image, int x, int y) {
        super(image, x, y, 1, 1);
    }

    public ItemObject(BufferedImage image, int x, int y, int directions,
            int frames) {
        super(image, x, y, directions, frames);
    }

    public Item getItem() {
        return item;
    }

}
