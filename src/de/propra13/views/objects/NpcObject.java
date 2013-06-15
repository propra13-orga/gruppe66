package de.propra13.views.objects;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.ImageObserver;

import de.propra13.assets.Theme;
import de.propra13.assets.animations.Animation;
import de.propra13.assets.animations.AnimationManager;
import de.propra13.models.Npc;
import de.propra13.models.Room;
import de.propra13.views.GameFieldView;

public class NpcObject extends MoveableGameObject {

    private static final int SHADOW = 0x2b1f15;
    private Npc npc;

    public NpcObject(int x, int y, Theme theme) {
        super(new Animation(theme.getSalesmanImage(), 8, 1, SHADOW), x, y);
        addAnimation("crouches", new Animation(
                theme.getSalesmanCrouchesImage(), 8, 9, SHADOW));
        addAnimation("talks", new Animation(theme.getSalesmanTalksImage(), 8,
                9, SHADOW));
        npc = new Npc();
        setGlowRadius(100);
        direction = new Direction(0, 0);
    }

    @Override
    public void act(Dimension gameFieldSize, Room room) {
        super.act(gameFieldSize, room);
        if (Math.random() > 0.999 && !isCloseToPlayersIn(room)) {
            triggerAnimation("crouches");
        } else if (canAct()) {
            greetPlayersIn(room);
        }
    }

    private boolean isCloseToPlayersIn(Room room) {
        return isCloseTo(room.getPlayerObject(), GameFieldView.GRID * 3);
    }

    private void greetPlayersIn(Room room) {
        if (isCloseToPlayersIn(room)) {
            lookAt(room.getPlayerObject());
            setCurrentAnimation("talks");
            if (room.getPlayerObject().getNpc() == null) {
                room.getPlayerObject().setNpc(npc);
            }
        } else if (room.getPlayerObject().getNpc() == npc) {
            setCurrentAnimation(AnimationManager.DEFAULT_ANIMATION);
            npc.setTalking(false);
            room.getPlayerObject().setNpc(null);
        }
    }

    @Override
    public void draw(Graphics2D gfx, ImageObserver ob) {
        super.draw(gfx, ob);

        if (npc.isTalking()) {
            gfx.setPaint(Color.red);
            gfx.drawString(npc.getCurrentMessage(), (int) x, (int) y);
        }
    }
}
