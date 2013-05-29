package de.propra13.views.objects;

import java.awt.Dimension;

import de.propra13.models.Fireball;
import de.propra13.models.Room;
import de.propra13.models.Theme;

public class FireballObject extends MoveableGameObject {

    private Fireball fireball;

    public FireballObject(Fireball fireball, int x, int y, Theme theme) {
        super(theme.getFireballBluna(), x, y, 8, 10);

        this.fireball = fireball;

        vx = -2;
        vy = -2;
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
            fireball.inflictDamageOn(playerObject.getPlayer());

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
