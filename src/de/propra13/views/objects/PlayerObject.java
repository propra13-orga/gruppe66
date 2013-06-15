package de.propra13.views.objects;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.geom.Point2D;
import java.awt.image.ImageObserver;
import java.util.ArrayList;

import de.propra13.assets.Theme;
import de.propra13.assets.animations.Animation;
import de.propra13.assets.animations.AnimationManager;
import de.propra13.assets.animations.AnimationStateListener;
import de.propra13.controllers.GameController;
import de.propra13.models.BioAgressor;
import de.propra13.models.Club;
import de.propra13.models.MagicFireball;
import de.propra13.models.Npc;
import de.propra13.models.Player;
import de.propra13.models.Room;
import de.propra13.views.GameFieldView;

public class PlayerObject extends BioAgressorObject {

    private final static String DEFAULTANIMATIONTYPE = Club.class
            .getSimpleName();

    private boolean leftSpawnPoint = false;
    private GameController controller;

    private static final int SHADOW = 0x271b11;

    private Player player;
    private Npc npc;
    private Theme theme;

    public PlayerObject(Player player, int x, int y, Theme theme) {
        super(new Animation(DEFAULTANIMATIONTYPE, theme.getPlayerBlunaSet(), 8,
                1, SHADOW), x, y);
        this.player = player;
        this.theme = theme;

        direction = new Direction(0, 0);

        addAnimation(
                "walks",
                new Animation(DEFAULTANIMATIONTYPE, theme
                        .getPlayerWalksBlunaSet(), 8, 9, SHADOW));
        addAnimation(
                "attacks",
                new Animation(DEFAULTANIMATIONTYPE, theme
                        .getPlayerAttacksBlunaSet(), 8, 13, SHADOW));
        addAnimation(
                "dies",
                new Animation(DEFAULTANIMATIONTYPE, theme
                        .getPlayerDiesBlunaSet(), 8, 9, SHADOW));
        addAnimation(
                "magics",
                new Animation(DEFAULTANIMATIONTYPE, theme
                        .getPlayerMagicsBlunaSet(), 8, 7, SHADOW));
        setCurrentAnimationType(DEFAULTANIMATIONTYPE);
    }

    public void setNpc(Npc npc) {
        this.npc = npc;
    }

    public Npc getNpc() {
        return npc;
    }

    @Override
    public void act(Dimension size, Room room) {
        super.act(size, room);

        if (isOnGoalIn(room) && leftSpawnPoint)
            controller.advanceRoom();
        if (isOnStartIn(room) && leftSpawnPoint)
            controller.retreatRoom();

        if (!leftSpawnPoint && !isOnStartIn(room) && !isOnGoalIn(room))
            leftSpawnPoint = true;

        ArrayList<ItemObject> items = searchItemsIn(room);
        for (ItemObject item : items) {
            player.pickUpItem(item.getItem());
        }
        if (isMoving())
            setCurrentAnimation("walks");
        else
            setCurrentAnimation(AnimationManager.DEFAULT_ANIMATION);
    }

    @Override
    public void animate() {
        setCurrentAnimationType(player.getWeapon().getClass().getSimpleName());
        super.animate();
    }

    @Override
    public void draw(Graphics2D gfx, ImageObserver ob) {
        super.draw(gfx, ob);
        drawArmorBar(gfx);
    }

    private void drawArmorBar(Graphics2D gfx) {
        double armor = ((Player) getAgressor()).getArmor();
        int width = (int) (getWidth() * (armor / getAgressor().MAXHEALTH));

        if (armor < Player.MAXARMOR)
            drawBar(gfx, 2, width, Color.cyan);
    }

    private ArrayList<ItemObject> searchItemsIn(Room room) {
        ArrayList<ItemObject> items = new ArrayList<ItemObject>();
        for (ItemObject item : room.getItems()) {
            if (item.getBounds().intersects(getBounds())) {
                items.add(item);
            }
        }

        room.getItems().removeAll(items);
        return items;
    }

    public void setController(GameController controller) {
        this.controller = controller;
    }

    public void setMoved(boolean moved) {
        this.leftSpawnPoint = moved;
    }

    private boolean isOnGoalIn(Room room) {
        Rectangle.Double biggerGoal = room.getGoal().getBounds();
        biggerGoal.x -= 1;
        biggerGoal.y -= 1;
        biggerGoal.width += 2;
        biggerGoal.height += 2;
        return biggerGoal.contains(getBounds());
    }

    private boolean isOnStartIn(Room room) {
        Rectangle.Double biggerStart = room.getStart().getBounds();
        biggerStart.x -= 1;
        biggerStart.y -= 1;
        biggerStart.width += 2;
        biggerStart.height += 2;
        return biggerStart.contains(getBounds());
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void keyPressed(KeyEvent event) {
        switch (event.getKeyCode()) {
        case KeyEvent.VK_UP:
            direction.setVy(-1);
            break;
        case KeyEvent.VK_RIGHT:
            direction.setVx(1);
            break;
        case KeyEvent.VK_DOWN:
            direction.setVy(1);
            break;
        case KeyEvent.VK_LEFT:
            direction.setVx(-1);
            break;
        case KeyEvent.VK_T:
            if (npc != null) {
                npc.setTalking(!npc.isTalking());
            }
            break;
        }
        velocity = 1;
    }

    public void keyReleased(KeyEvent event) {
        switch (event.getKeyCode()) {
        case KeyEvent.VK_UP:
            if (direction.getVy() == -1)
                direction.setVy(0);
            break;
        case KeyEvent.VK_DOWN:
            if (direction.getVy() == 1)
                direction.setVy(0);
            break;
        case KeyEvent.VK_RIGHT:
            if (direction.getVx() == 1)
                direction.setVx(0);
            break;
        case KeyEvent.VK_LEFT:
            if (direction.getVx() == -1)
                direction.setVx(0);
            break;
        }

        if (!isMoving()) {
            velocity = 0;
        }
    }

    @Override
    protected BioAgressor getAgressor() {
        return player;
    }

    public void inflictDamageOn(ArrayList<EnemyObject> enemies) {
        if (canAct()) {
            triggerAnimation("attacks");

            for (EnemyObject enemy : enemies) {
                if (enemy.getCenter().distance(getCenter()) <= 1.5 * GameFieldView.GRID)
                    player.inflictDamageOn(enemy.getAgressor());
            }
        }
    }

    public void triggerDeath(AnimationStateListener listener) {
        triggerAnimation("dies", listener);
    }

    public void performMagicIn(Room room, int x, int y) {
        if (canAct()) {
            MagicFireball magicFireball = player.createFireball();
            if (magicFireball != null) {
                Point gridCenter = getGridCenter();
                Point2D center = getCenter();
                Direction direction = new Direction(x - center.getX(), y
                        - center.getY());
                setAnimationDirection(direction.getAnimationDirection());
                MagicFireballObject magicFireballObject = new MagicFireballObject(
                        magicFireball, direction, gridCenter.x, gridCenter.y,
                        theme);
                room.addMagic(magicFireballObject);

                triggerAnimation("magics");
            }
        }
    }
}
