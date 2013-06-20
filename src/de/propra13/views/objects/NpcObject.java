package de.propra13.views.objects;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.geom.Rectangle2D;

import de.propra13.assets.animations.Animation;
import de.propra13.assets.animations.AnimationManager;
import de.propra13.models.Npc;
import de.propra13.models.Room;
import de.propra13.views.GameFieldView;

public class NpcObject extends MoveableGameObject {

    private Npc npc;

    public NpcObject(Npc npc, Animation animation, int x, int y) {
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

    public void idle() {
        npc.reset();
        setCurrentAnimation(AnimationManager.DEFAULT_ANIMATION);
    }

    public void randomIdle() {
        triggerAnimation("idles");
    }

    @Override
    public void keyPressed(KeyEvent event) {
        super.keyPressed(event);

        switch (event.getKeyCode()) {
        case KeyEvent.VK_N:
            npc.advanceMessage();
            break;
        }
    }

    public void isNearPlayers(Room room) {
        npc.startTalking();
        lookAt(room.getPlayerObject());
        setCurrentAnimation("talks");
    }

    public Npc getNpc() {
        return npc;
    }

    public boolean isCloseToPlayersIn(Room room) {
        return isCloseTo(room.getPlayerObject(), GameFieldView.GRID * 3);
    }

    public void drawText(Graphics2D gfx, GameFieldView view) {
        if (getNpc().hasMessage()) {
            String[] lines = getNpc().getMessage().split("\\r?\\n");
            Rectangle2D.Double union = new Rectangle2D.Double();

            for (int i = 0; i < lines.length; i++) {
                Rectangle2D bounds = gfx.getFontMetrics().getStringBounds(
                        lines[i], gfx);
                Rectangle2D.Double translatedBounds = new Rectangle2D.Double(
                        bounds.getX(), bounds.getY() + bounds.getHeight() * i,
                        bounds.getWidth(), bounds.getHeight());
                Rectangle2D.union(union, translatedBounds, union);
            }

            double tx = getCenter().x - union.getWidth() / 2;
            double ty = y - union.getHeight() - 10;

            if (tx + union.getWidth() > view.getWidth()) {
                tx -= (tx + union.getWidth()) - view.getWidth() + 25;
            } else if (tx < 0) {
                tx = 25;
            }

            if (ty + union.getHeight() > view.getHeight()) {
                ty -= (ty + union.getHeight()) - view.getHeight() + 25;
            } else if (ty < 0) {
                ty = 25;
            }

            gfx.setPaint(new Color(255, 243, 215));
            gfx.setStroke(new BasicStroke(2));
            gfx.fillRoundRect((int) tx - 10, (int) ty - 25,
                    (int) union.getWidth() + 20, (int) union.getHeight() + 20,
                    (int) 25, (int) 25);

            gfx.fillPolygon(new int[] { (int) getCenter().x - 20,
                    (int) getCenter().x + 5, (int) getCenter().x },
                    new int[] { (int) (ty + union.getHeight()) + 20 - 27,
                            (int) (ty + union.getHeight()) + 20 - 27,
                            (int) (ty + union.getHeight()) + 40 - 27 }, 3);

            gfx.setPaint(new Color(69, 63, 57));
            for (int i = 0; i < lines.length; i++) {
                gfx.drawString(lines[i], (int) tx, (int) (-22 + ty + gfx
                        .getFontMetrics().getHeight() * (i + 1)));
            }
        }
    }
}
