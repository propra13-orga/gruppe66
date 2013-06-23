package de.propra13.views.objects;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
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

    private Font messageFont;

    public NpcObject(N npc, Animation animation, int x, int y, Font messageFont) {
        super(animation, x, y);
        this.npc = npc;

        setGlowRadius(100);
        direction = new Direction(0, 0);

        this.messageFont = messageFont;
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

    public Font getMessageFont() {
        return messageFont;
    }

    public boolean isCloseToPlayersIn(Room room) {
        return isCloseTo(room.getPlayerObject(), GameFieldView.GRID * 3);
    }

    public void drawText(Graphics2D gfx, GameFieldView view) {
        if (getNpc().hasMessage()) {
            String message = getNpc().getMessage();
            gfx.setFont(messageFont.deriveFont(14f));

            Rectangle messageBounds = calculateBoundsFor(message, gfx);

            fitBoundsInto(view, messageBounds);
            drawThoughtBubbleFor(gfx, messageBounds, 15, 15, 25);
            drawTextAt(gfx, message, messageBounds.x, messageBounds.y);
        }
    }

    private Rectangle calculateBoundsFor(String string, Graphics2D gfx) {
        Rectangle2D.Double union = new Rectangle2D.Double();
        String[] lines = string.split("\\r?\\n");
        for (int i = 0; i < lines.length; i++) {
            Rectangle2D bounds = gfx.getFontMetrics().getStringBounds(lines[i],
                    gfx);
            Rectangle2D.union(union, bounds, union);
        }

        int width = (int) Math.ceil(union.width);
        int height = (int) Math.ceil(union.height) * lines.length;
        int x = (int) Math.round(getCenter().x - width / 2);
        int y = (int) Math.round(this.y - height - 10);

        return new Rectangle(x, y, width, height);
    }

    private static void fitBoundsInto(GameFieldView view, Rectangle bounds) {
        bounds.x = Math.min(Math.max(bounds.x, 25),
                (view.getWidth() - 25 - bounds.width));
        bounds.y = Math.min(Math.max(bounds.y, 25),
                (view.getHeight() - 25 - bounds.height));
    }

    private static void drawThoughtBubbleFor(Graphics2D gfx, Rectangle bounds,
            int paddingX, int paddingY, int radius) {
        drawThoughtBubbleFor(gfx, bounds, paddingX, paddingY, radius, false);
    }

    private static void drawThoughtBubbleFor(Graphics2D gfx, Rectangle bounds,
            int paddingX, int paddingY, int radius, boolean debug) {
        int x = bounds.x - paddingX;
        int y = bounds.y - paddingY;
        int width = bounds.width + paddingX * 2;
        int height = bounds.height + paddingY * 2;

        gfx.setPaint(new Color(255, 243, 215, 180));
        gfx.fillRoundRect(x, y, width, height, radius, radius);

        gfx.setPaint(new Color(105, 93, 105, 230));
        gfx.setStroke(new BasicStroke(4));
        gfx.drawRoundRect(x, y, width, height, radius, radius);

        if (debug) {
            gfx.setPaint(Color.black);
            gfx.setStroke(new BasicStroke(1));
            gfx.drawRect(bounds.x, bounds.y, bounds.width, bounds.height);
        }
    }

    private static void drawTextAt(Graphics2D gfx, String text, int x, int y) {
        gfx.setPaint(new Color(69, 63, 57));
        String[] lines = text.split("\\r?\\n");
        for (int i = 0; i < lines.length; i++) {
            FontMetrics metrics = gfx.getFontMetrics();
            int ty = y + metrics.getAscent() + metrics.getHeight() * i;
            gfx.drawString(lines[i], x, ty);
        }

    }
}
