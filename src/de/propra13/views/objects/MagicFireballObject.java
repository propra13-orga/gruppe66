package de.propra13.views.objects;

import java.awt.Dimension;

import de.propra13.assets.Theme;
import de.propra13.assets.animations.Animation;
import de.propra13.models.MagicFireball;
import de.propra13.models.Room;

public class MagicFireballObject extends MoveableGameObject {

    private MagicFireball magicFireball;

    private long birthTime;

    public MagicFireballObject(MagicFireball magicFireball,
            Direction direction, int x, int y, Theme theme) {
        super(new Animation(theme.getMagicFireballImage(), 1, 16, 0), x, y);

        this.magicFireball = magicFireball;
        this.direction = direction;

        birthTime = System.currentTimeMillis();
    }

    @Override
    public void act(Dimension gameFieldSize, Room room) {
        super.act(gameFieldSize, room);
        if (System.currentTimeMillis() - birthTime < 3000) {
            for (EnemyObject enemyObject : room.getEnemies()) {
                if (enemyObject.getBounds().contains(getBounds()))
                    magicFireball.inflictDamageOn(enemyObject.getAgressor());
            }
        } else {
            room.removeMagics(this);
        }
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
