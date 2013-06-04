package de.propra13.views.objects;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import de.propra13.assets.Theme;
import de.propra13.assets.animations.Animation;
import de.propra13.assets.animations.AnimationManager;
import de.propra13.assets.animations.AnimationStateListener;
import de.propra13.controllers.GameController;
import de.propra13.models.Agressor;
import de.propra13.models.Player;
import de.propra13.models.Room;
import de.propra13.models.Weapon;
import de.propra13.views.GameFieldView;

public class PlayerObject extends AgressorObject {

    private boolean leftSpawnPoint = false;
    private GameController controller;

    private static final int SHADOW = 0x271b11;

    private Player player;
    private boolean reloads;

    public PlayerObject(Player player, int x, int y, Theme theme) {
        super(new Animation(theme.getPlayerBluna(), theme.getPlayerBlunaSet(),
                8, 1, SHADOW), x, y);
        this.player = player;

        animationManager.addAnimation(
                "walking",
                new Animation(theme.getPlayerWalksBluna(), theme
                        .getPlayerWalksBlunaSet(), 8, 9, SHADOW));
        animationManager.addAnimation(
                "attacks",
                new Animation(theme.getPlayerAttacksBluna(), theme
                        .getPlayerAttacksBlunaSet(), 8, 13, SHADOW));
    }

    public void move(Dimension size, Room room) {
        super.move(size, room);

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
    }

    @Override
    public void animate() {
        switch (player.getWeaponType()) {
        case Weapon.SWORD:
            animationManager.setCurrentAnimationType(Weapon.SWORD);
            break;
        default:
            animationManager
                    .setCurrentAnimationType(AnimationManager.DEFAULT_ANIMATION_TYPE);
        }
        super.animate();
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
        Rectangle biggerGoal = room.getGoal().getBounds();
        biggerGoal.grow(2, 2);
        return biggerGoal.contains(getBounds());
    }

    private boolean isOnStartIn(Room room) {
        Rectangle biggerStart = room.getStart().getBounds();
        biggerStart.grow(2, 2);
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
            vy = -1;
            break;
        case KeyEvent.VK_RIGHT:
            vx = 1;
            break;
        case KeyEvent.VK_DOWN:
            vy = 1;
            break;
        case KeyEvent.VK_LEFT:
            vx = -1;
            break;
        }

        animationManager.setDirection(new Direction(vx, vy));
        animationManager.setCurrentAnimation("walking");
    }

    public void keyReleased(KeyEvent event) {
        switch (event.getKeyCode()) {
        case KeyEvent.VK_UP:
            if (vy == -1)
                vy = 0;
            break;
        case KeyEvent.VK_DOWN:
            if (vy == 1)
                vy = 0;
            break;
        case KeyEvent.VK_RIGHT:
            if (vx == 1)
                vx = 0;
            break;
        case KeyEvent.VK_LEFT:
            if (vx == -1)
                vx = 0;
            break;
        }

        if (!isMoving())
            animationManager
                    .setCurrentAnimation(AnimationManager.DEFAULT_ANIMATION);
    }

    @Override
    protected Agressor getAgressor() {
        return player;
    }

    public void inflictDamageOn(ArrayList<EnemyObject> enemies) {
        if (!reloads) {
            animationManager.triggerAnimation("attacks",
                    new AnimationStateListener() {

                        @Override
                        public void willStart() {
                        }

                        @Override
                        public void didEnd() {
                            reloads = false;
                        }
                    });

            for (EnemyObject enemy : enemies) {
                if (enemy.getCenter().distance(getCenter()) <= 1.5 * GameFieldView.GRID)
                    player.inflictDamageOn(enemy.getAgressor());
            }

            reloads = true;
        }
    }
}
