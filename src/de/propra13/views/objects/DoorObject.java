package de.propra13.views.objects;

import de.propra13.assets.animations.Animation;

public class DoorObject extends GameObject {

    private boolean open = true;

    public DoorObject(Animation defaultAnimation, int x, int y) {
        super(defaultAnimation, x, y);
    }

    public boolean isOpen() {
        return open;
    }

    public void open() {
        open = true;
    }

    public void close() {
        open = false;
    }
}
