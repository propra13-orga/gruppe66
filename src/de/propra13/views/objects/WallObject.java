package de.propra13.views.objects;

public class WallObject extends GameObject {

    public WallObject(int x, int y, Theme theme) {
        super(theme.getWallImage(), x, y);
    }

}
