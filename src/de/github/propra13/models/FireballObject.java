package de.github.propra13.models;

import de.github.propra13.objects.MoveableGameObject;

public class FireballObject extends MoveableGameObject {

    public FireballObject(int x, int y) {
        super("res/fireball.png", x, y);

        vx = -2;
        vy = -2;
    }

    @Override
    protected void collidedLeft(int oldx) {
        super.collidedLeft(oldx);

        vx *= -1;
    }

    @Override
    protected void collidedRight(int oldx) {
        super.collidedRight(oldx);

        vx *= -1;
    }

    @Override
    protected void collidedTop(int oldy) {
        super.collidedTop(oldy);

        vy *= -1;
    }

    @Override
    protected void collidedBottom(int oldy) {
        super.collidedBottom(oldy);

        vy *= -1;
    }
}
