package de.propra13.views.objects;

import java.awt.Dimension;

import de.propra13.assets.Theme;
import de.propra13.assets.animations.Animation;
import de.propra13.assets.animations.AnimationManager;
import de.propra13.assets.animations.AnimationStateListener;
import de.propra13.models.BioAgressor;
import de.propra13.models.Dragon;
import de.propra13.models.Player;
import de.propra13.models.Room;
import de.propra13.views.GameFieldView;

public class DragonObject extends EnemyObject {

    private Dragon dragon;

    private static final int SHADOW = 0x1f160d;

    public DragonObject(Dragon dragon, int x, int y, Theme theme) {
        super(new Animation(theme.getDragonBluna(), 8, 1, SHADOW), x, y);
        this.dragon = dragon;

        direction = new Direction(0, 0);
        initAnimations(theme);
    }

    private void initAnimations(Theme theme) {
        addAnimation("walking", new Animation(theme.getDragonWalksBluna(), 8,
                9, SHADOW));
        addAnimation("dies", new Animation(theme.getDragonDiesBluna(), 8, 11,
                SHADOW));
        addAnimation("dead", new Animation(theme.getDragonDeadBluna(), 8, 1,
                SHADOW));
        addAnimation("attacks", new Animation(theme.getDragonAttacksBluna(), 8,
                9, SHADOW));
    }

    @Override
    public void act(Dimension gameFieldSize, final Room room) {
        super.act(gameFieldSize, room);

        if (getAgressor().isDead()) {
            triggerAnimation("dies", new AnimationStateListener() {

                @Override
                public void willStart() {
                }

                @Override
                public void didEnd() {
                    setCurrentAnimation("dead");
                    setCanAct(false);
                    dimGlowing(1, 20);
                }
            });

            return;
        }

        if (canAct()) {
            direction = directionTo(room.getPlayerObject());
            if (direction.length() > GameFieldView.GRID * 2) {
                velocity = .75;
                setCurrentAnimation("walking");
            } else {
                setCurrentAnimation(AnimationManager.DEFAULT_ANIMATION);
                velocity = 0;
                attack(room.getPlayerObject().getPlayer());
            }
        }
    }

    private void attack(final Player player) {
        if (!dragon.isReloading()) {
            triggerAnimation("attacks", new AnimationStateListener() {
                @Override
                public void willStart() {
                }

                @Override
                public void didEnd() {
                    dragon.inflictDamageOn(player);
                }
            });
        }
    }

    @Override
    protected BioAgressor getAgressor() {
        return dragon;
    }

}