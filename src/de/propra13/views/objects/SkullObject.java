package de.propra13.views.objects;

import java.awt.Dimension;

import de.propra13.assets.Theme;
import de.propra13.assets.animations.Animation;
import de.propra13.models.Room;
import de.propra13.models.Skull;

public class SkullObject extends MoveableGameObject {

    private Skull skull;

    public SkullObject(Skull skull, int x, int y, Theme theme) {
        super(new Animation(theme.getFireballBluna(), 8, 10, 0x1f160d), x, y);

        this.skull = skull;

        direction = new Direction(-1, -1);
    }

    @Override
    public void move(Dimension size, Room room) {
        super.move(size, room);

        if (Math.random() > 0.9995) {
            direction.bounceX();
        }

        if (Math.random() > 0.9775) {
            direction.bounceY();
        }

        if (room.getPlayerObject().getBounds().contains(getBounds()))
            skull.inflictDamageOn(room.getPlayerObject().getPlayer());
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
