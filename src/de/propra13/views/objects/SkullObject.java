package de.propra13.views.objects;

import java.awt.Dimension;

import de.propra13.assets.animations.Animation;
import de.propra13.models.Room;
import de.propra13.models.Skull;
import de.propra13.models.Theme;

public class SkullObject extends MoveableGameObject {

    private Skull skull;

    public SkullObject(Skull skull, int x, int y, Theme theme) {
        super(new Animation(theme.getFireballBluna(), 8, 10, 0x1f160d), x, y);

        this.skull = skull;

        vx = 0;
        vy = 0;
    }

    public void move(Dimension size, Room room, PlayerObject playerObject) {
        move(size, room);

        if (Math.random() > 0.9975) {
            vx *= -1;
        }

        if (Math.random() > 0.9775) {
            vy *= -1;
        }

        if (playerObject.getBounds().contains(getBounds()))
            skull.inflictDamageOn(playerObject.getPlayer());

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
