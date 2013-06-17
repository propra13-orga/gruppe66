package de.propra13.views.objects;

import java.awt.Dimension;
import java.awt.Rectangle;

import de.propra13.assets.Theme;
import de.propra13.assets.animations.Animation;
import de.propra13.assets.animations.AnimationStateListener;
import de.propra13.models.MagicFireball;
import de.propra13.models.Room;

public class MagicFireballObject extends AgressorObject {

    private MagicFireball magicFireball;

    public MagicFireballObject(MagicFireball magicFireball,
            Direction direction, int x, int y, Theme theme) {
        super(new Animation(theme.getMagicFireballImage(), 1, 16, 0), x, y);

        addAnimation("explodes",
                new Animation(theme.getMagicFireballExplosionImage(), 1, 20,
                        new Rectangle(1, 1)));

        this.magicFireball = magicFireball;
        this.direction = direction;

        setGlowRadius(100);
    }

    @Override
    public void act(Dimension gameFieldSize, final Room room) {
        super.act(gameFieldSize, room);
        if (magicFireball.isAlive()) {
            for (EnemyObject enemyObject : room.getEnemies()) {
                if (enemyObject.getBounds().contains(getBounds())) {
                    attack(enemyObject);

                    triggerAnimation("explodes", new AnimationStateListener() {

                        @Override
                        public void willStart() {
                        }

                        @Override
                        public void didEnd() {
                            room.removeMagics(MagicFireballObject.this);
                        }
                    });
                }
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

    public void attack(BioAgressorObject bioAgressor) {
        if (!magicFireball.isReloading()) {
            bioAgressor.takeHit(magicFireball.getDamage());
            magicFireball.reload();
        }
    }

}
