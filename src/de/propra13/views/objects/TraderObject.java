package de.propra13.views.objects;

import de.propra13.assets.Theme;
import de.propra13.assets.animations.Animation;
import de.propra13.assets.animations.AnimationManager;
import de.propra13.models.Npc;
import de.propra13.models.Room;
import de.propra13.models.Trader;

public class TraderObject extends NpcObject {

    private Trader trader;

    public TraderObject(Trader trader, int x, int y, Theme theme) {
        super(new Animation(theme.getTraderBlunas().get("stands")
                .get("default")), x, y);

        this.trader = trader;
        addAnimations("default", theme.getTraderBlunas());

        setGlowRadius(100);
        direction = new Direction(0, 0);
    }

    @Override
    public void idle() {
        setCurrentAnimation(AnimationManager.DEFAULT_ANIMATION);
    }

    @Override
    public void randomIdle() {
        triggerAnimation("idles");
    }

    @Override
    public void isNearPlayers(Room room) {
        lookAt(room.getPlayerObject());
        setCurrentAnimation("talks");
    }

    @Override
    public Npc getNpc() {
        return trader;
    }
}
