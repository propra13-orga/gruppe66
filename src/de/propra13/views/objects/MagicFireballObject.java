package de.propra13.views.objects;

import java.awt.Rectangle;

import de.propra13.assets.Theme;
import de.propra13.assets.animations.Animation;
import de.propra13.models.MagicFireball;

public class MagicFireballObject extends MoveableGameObject {

    public static final Rectangle BOUNDS = new Rectangle(23, 25, 16, 16);

    public MagicFireballObject(MagicFireball magicFireball,
            Direction direction, int x, int y, Theme theme) {
        super(new Animation(theme.getFlyingMagicImage(), 8, 21, BOUNDS), x, y);
        this.direction = direction;
    }

    @Override
    protected void collidedLeft(double oldx) {
        super.collidedLeft(oldx);

        direction.bounceX();
    }

    @Override
    protected void collidedRight(double oldx) {
        super.collidedRight(oldx);

        direction.bounceX();
    }

    @Override
    protected void collidedTop(double oldy) {
        super.collidedTop(oldy);

        direction.bounceY();
    }

    @Override
    protected void collidedBottom(double oldy) {
        super.collidedBottom(oldy);

        direction.bounceY();
    }
}
