package de.propra13.views.objects;

import java.awt.Dimension;

import de.propra13.assets.Theme;
import de.propra13.assets.animations.Animation;
import de.propra13.assets.animations.AnimationManager;
import de.propra13.assets.animations.AnimationStateListener;
import de.propra13.models.BioAgressor;
import de.propra13.models.Dragon;
import de.propra13.models.Room;
import de.propra13.views.GameFieldView;

public class DragonObject extends EnemyObject {

    private Dragon dragon;

    public DragonObject(Dragon dragon, int x, int y, Theme theme) {
        super(new Animation(theme.getDragonBlunas().get("stands")
                .get("default")), x, y);
        this.dragon = dragon;

        direction = new Direction(0, 0);
        addAnimations("default", theme.getDragonBlunas());
    }

    @Override
    public void act(Dimension gameFieldSize, final Room room) {
        super.act(gameFieldSize, room);

        if (getBioAgressor().isDead()) {
            triggerAnimation("dies", new AnimationStateListener() {

                @Override
                public void willStart() {
                }

                @Override
                public void didEnd() {
                    setCurrentAnimation("dead");
                    setCanAct(false);
                    dimGlowing(1, 20);

                    room.openDoors();
                }
            });

            return;
        }

        if (canAct()) {
            direction = directionTo(room.getPlayerObject());
            if (direction.length() > GameFieldView.GRID * 2) {
                velocity = .75;
                setCurrentAnimation("walks");
            } else {
                setCurrentAnimation(AnimationManager.DEFAULT_ANIMATION);
                velocity = 0;
                attack(room.getPlayerObject());
            }
        }
    }

    @Override
    protected BioAgressor getBioAgressor() {
        return dragon;
    }

    @Override
    public void takeHit(final double damage) {
        triggerAnimation("takes_hit", new AnimationStateListener() {
            @Override
            public void willStart() {
            }

            @Override
            public void didEnd() {
                dragon.sufferDamage(damage);
            }
        });
    }

    public void attack(final BioAgressorObject bioAgressor) {
        if (!dragon.isReloading()) {
            triggerAnimation("attacks", new AnimationStateListener() {
                @Override
                public void willStart() {
                }

                @Override
                public void didEnd() {
                    bioAgressor.takeHit(dragon.getDamage());
                    dragon.reload();
                }
            });
        }
    }

}