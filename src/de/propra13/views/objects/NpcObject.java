package de.propra13.views.objects;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.geom.Rectangle2D;

import de.propra13.assets.animations.Animation;
import de.propra13.assets.animations.AnimationManager;
import de.propra13.models.Npc;
import de.propra13.models.Room;
import de.propra13.views.GameFieldView;

public class NpcObject<N extends Npc> extends MoveableGameObject {

    private N npc;
    private PlayerObject nearPlayer = null;

    public NpcObject(N npc, Animation animation, int x, int y) {
        super(animation, x, y);
        this.npc = npc;

        setGlowRadius(100);
        direction = new Direction(0, 0);
    }

    @Override
    public final void act(Dimension gameFieldSize, Room room) {
        super.act(gameFieldSize, room);
        if (isCloseToPlayersIn(room) && canAct()) {
            isNearPlayers(room);
        } else {
            idle();
            if (Math.random() > 0.999) {
                randomIdle();
            }
        }
    }

    public boolean isNearPlayer() {
        return nearPlayer != null;
    }

    public PlayerObject getNearPlayer() {
        return nearPlayer;
    }

    public void idle() {
        nearPlayer = null;
        npc.reset();
        setCurrentAnimation(AnimationManager.DEFAULT_ANIMATION);
    }

    public void randomIdle() {
        triggerAnimation("idles");
    }

    @Override
    public void keyPressed(KeyEvent event, boolean paused) {
        super.keyPressed(event, paused);

        switch (event.getKeyCode()) {
        case KeyEvent.VK_N:
            npc.advanceMessage();
            break;
        }
    }

    public void isNearPlayers(Room room) {
        npc.startTalking();
        lookAt(nearPlayer = room.getPlayerObject());
        setCurrentAnimation("talks");
    }

    public N getNpc() {
        return npc;
    }

    public boolean isCloseToPlayersIn(Room room) {
        return isCloseTo(room.getPlayerObject(), GameFieldView.GRID * 3);
    }

    public void drawText(Graphics2D gfx, GameFieldView view) {
        if (getNpc().hasMessage()) {
            String message = getNpc().getMessage();
            Rectangle messageBounds = calculateBoundsFor(message, gfx);

            fitBoundsInto(view, messageBounds);
            drawThoughtBubbleFor(gfx, messageBounds);
            drawTextInBounds(gfx, message, messageBounds);
        }
    }

    private Rectangle calculateBoundsFor(String string, Graphics2D gfx) {
        Rectangle2D.Double union = new Rectangle2D.Double();
        String[] lines = string.split("\\r?\\n");
        for (int i = 0; i < lines.length; i++) {
            Rectangle2D bounds = gfx.getFontMetrics().getStringBounds(lines[i],
                    gfx);
            Rectangle2D.Double translatedBounds = new Rectangle2D.Double(
                    bounds.getX(), bounds.getY() + bounds.getHeight() * i,
                    bounds.getWidth(), bounds.getHeight());
            Rectangle2D.union(union, translatedBounds, union);
        }

        union.x = getCenter().x - union.width / 2;
        union.y = y - union.height - 10;

        return new Rectangle((int) union.x, (int) union.y, (int) union.width,
                (int) union.height);
    }

    private void fitBoundsInto(GameFieldView view, Rectangle bounds) {
        bounds.x = Math.min(Math.max(bounds.x, 25),
                (view.getWidth() - 25 - bounds.width));
        bounds.y = Math.min(Math.max(bounds.y, 25),
                (view.getHeight() - 25 - bounds.height));
    }

    private void drawThoughtBubbleFor(Graphics2D gfx, Rectangle bounds) {
        gfx.setPaint(new Color(255, 243, 215));
        gfx.fillRoundRect((int) bounds.x - 10, (int) bounds.y - 25,
                (int) bounds.width + 20, (int) bounds.height + 20, 25, 25);
        gfx.setPaint(new Color(105, 93, 105));
        gfx.setStroke(new BasicStroke(4));
        gfx.drawRoundRect((int) bounds.x - 10, (int) bounds.y - 25,
                (int) bounds.width + 20, (int) bounds.height + 20, 25, 25);
    }

    private void drawTextInBounds(Graphics2D gfx, String text, Rectangle bounds) {
        String[] lines = text.split("\\r?\\n");
        gfx.setPaint(new Color(69, 63, 57));
        for (int i = 0; i < lines.length; i++) {
            gfx.drawString(lines[i], bounds.x, -22 + bounds.y
                    + gfx.getFontMetrics().getHeight() * (i + 1));
        }

    }
}
