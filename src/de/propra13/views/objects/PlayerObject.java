package de.propra13.views.objects;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
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
import de.propra13.models.Player;
import de.propra13.models.Room;
import de.propra13.views.GameFieldView;

public class PlayerObject extends BioAgressorObject {

    public final static double ENTERDOORTHRESHOLD = 75.0;
    private final static String DEFAULTANIMATIONTYPE = Club.class
            .getSimpleName();

    private boolean leftSpawnPoint = false;
    private GameController controller;

    private Player player;
    private Theme theme;

    public PlayerObject(Player player, int x, int y, Theme theme,
            GameController controller) {
        super(new Animation(DEFAULTANIMATIONTYPE, theme.getPlayerBlunas().get(
                "stands")), x, y);
        this.player = player;
        this.theme = theme;
        this.controller = controller;
        direction = new Direction(0, 0);

        addAnimations(DEFAULTANIMATIONTYPE, theme.getPlayerBlunas());
        setCurrentAnimationType(DEFAULTANIMATIONTYPE);
    }

    @Override
    public void act(Dimension size, Room room) {
        super.act(size, room);

        if (canEnter(room.getGoal()) && leftSpawnPoint)
            controller.advanceRoom();
        if (canEnter(room.getStart()) && leftSpawnPoint)
            controller.retreatRoom();

        if (!leftSpawnPoint && !isOn(room.getStart()) && !isOn(room.getGoal()))
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

    private boolean canEnter(DoorObject door) {
        return isOn(door) && door.isOpen();
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
        double armor = player.getArmor();
        int width = (int) (getWidth() * (armor / Player.MAXARMOR));

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

    public void setMoved(boolean moved) {
        this.leftSpawnPoint = moved;
    }

    private boolean isOn(DoorObject door) {
        return getIntersectionPercentage(door) >= ENTERDOORTHRESHOLD;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    @Override
    public void keyPressed(KeyEvent event, boolean paused) {
        super.keyPressed(event, paused);
        if (!paused) {
            switch (event.getKeyCode()) {
            case KeyEvent.VK_W:
                direction.setVy(-1);
                break;
            case KeyEvent.VK_D:
                direction.setVx(1);
                break;
            case KeyEvent.VK_S:
                direction.setVy(1);
                break;
            case KeyEvent.VK_A:
                direction.setVx(-1);
                break;
            case KeyEvent.VK_SPACE:
                controller.getCurrentRoom().getPlayerObject()
                        .attackAll(controller.getCurrentRoom().getEnemies());
                break;
            }
            velocity = 1;
        }
    }

    @Override
    public void keyReleased(KeyEvent event, boolean paused) {
        super.keyReleased(event, paused);

        switch (event.getKeyCode()) {
        case KeyEvent.VK_W:
            if (direction.getVy() == -1)
                direction.setVy(0);
            break;
        case KeyEvent.VK_S:
            if (direction.getVy() == 1)
                direction.setVy(0);
            break;
        case KeyEvent.VK_D:
            if (direction.getVx() == 1)
                direction.setVx(0);
            break;
        case KeyEvent.VK_A:
            if (direction.getVx() == -1)
                direction.setVx(0);
            break;
        }

        if (!isMoving()) {
            velocity = 0;
        }
    }

    @Override
    protected BioAgressor getBioAgressor() {
        return player;
    }

    @Override
    public void takeHit(final double damage) {
        player.sufferDamage(damage);
        if (!player.isDead()) {
            triggerAnimation("takes_hit");
        }
    }

    public void attackAll(ArrayList<EnemyObject> enemies) {
        if (canAct()) {
            triggerAnimation("attacks");

            for (EnemyObject enemy : enemies) {
                if (enemy.getCenter().distance(getCenter()) <= 1.5 * GameFieldView.GRID)
                    attack(enemy);
            }
        }
    }

    public void attack(BioAgressorObject bioAgressor) {
        bioAgressor.takeHit(player.getDamage());
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
