package de.propra13.views.objects;

import java.awt.Dimension;
import java.awt.geom.Point2D;

import javax.swing.SwingUtilities;

import de.propra13.assets.Theme;
import de.propra13.assets.animations.Animation;
import de.propra13.assets.animations.AnimationManager;
import de.propra13.assets.animations.AnimationStateListener;
import de.propra13.models.Agressor;
import de.propra13.models.Dragon;
import de.propra13.models.Room;
import de.propra13.views.GameFieldView;

public class DragonObject extends EnemyObject {

    private Dragon dragon;

    private boolean dies = false;

    private static final int SHADOW = 0x1f160d;

    public DragonObject(Dragon dragon, int x, int y, Theme theme) {
        super(new Animation(theme.getDragonBluna(), 8, 1, SHADOW), x, y);
        this.dragon = dragon;

        animationManager.addAnimation("walking",
                new Animation(theme.getDragonWalksBluna(), 8, 9, SHADOW));
        getAnimationManager().addAnimation("dies",
                new Animation(theme.getDragonDiesBluna(), 8, 11, SHADOW));
    }

    @Override
    public void move(Dimension gameFieldSize, final Room room) {
        super.move(gameFieldSize, room);

        if (getAgressor().isDead()) {
            if (!dies) {
                dies = true;
                final DragonObject me = this;
                getAnimationManager().triggerAnimation("dies",
                        new AnimationStateListener() {
                            private boolean died = false;

                            @Override
                            public void willStart() {
                            }

                            @Override
                            public void didEnd() {
                                if (!died) {
                                    SwingUtilities.invokeLater(new Runnable() {
                                        @Override
                                        public void run() {
                                            room.removeEnemy(me);
                                        }
                                    });
                                    died = true;
                                }
                            }
                        });
            }
            return;
        }

        Point2D.Double p = room.getPlayerObject().getCenter();
        Direction newDirection = new Direction((int) (p.x - getCenter().x),
                (int) (p.y - getCenter().y));

        if (p.distance(getCenter()) > GameFieldView.GRID * 2) {
            vx = newDirection.getNormalizedVx();
            vy = newDirection.getNormalizedVy();
            getAnimationManager().setCurrentAnimation("walking");
        } else {
            vx = vy = 0;
            dragon.inflictDamageOn(room.getPlayerObject().getPlayer());
            getAnimationManager().setCurrentAnimation(
                    AnimationManager.DEFAULT_ANIMATION);
        }

        getAnimationManager().setDirection(newDirection);
    }

    @Override
    protected Agressor getAgressor() {
        return dragon;
    }

}