package de.propra13.views.objects;

import java.awt.event.KeyEvent;

import de.propra13.assets.Theme;
import de.propra13.assets.animations.Animation;
import de.propra13.assets.animations.AnimationManager;
import de.propra13.models.Max;
import de.propra13.models.Npc;
import de.propra13.models.Room;

public class MaxObject extends NpcObject {

    private Max max;

    public MaxObject(Max max, int x, int y, Theme theme) {
        super(new Animation(theme.getMaxBlunas().get("stands").get("default")),
                x, y);

        this.max = max;
        addAnimation(
                "crouches",
                new Animation(theme.getMaxBlunas().get("crouches")
                        .get("default")));
        addAnimation("talks", new Animation(theme.getMaxBlunas().get("talks")
                .get("default")));

        setGlowRadius(100);
        direction = new Direction(0, 0);
    }

    @Override
    public void idle() {
        max.reset();
        setCurrentAnimation(AnimationManager.DEFAULT_ANIMATION);
    }

    @Override
    public void randomIdle() {
        triggerAnimation("crouches");
    }

    @Override
    public void keyPressed(KeyEvent event) {
        super.keyPressed(event);

        switch (event.getKeyCode()) {
        case KeyEvent.VK_N:
            max.advanceMessage();
            break;
        }
    }

    @Override
    public void isNearPlayers(Room room) {
        max.startTalking();
        lookAt(room.getPlayerObject());
        setCurrentAnimation("talks");
    }

    @Override
    public Npc getNpc() {
        return max;
    }
}
