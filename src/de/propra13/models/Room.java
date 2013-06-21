package de.propra13.models;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.propra13.assets.Theme;
import de.propra13.controllers.GameController;
import de.propra13.views.objects.DragonObject;
import de.propra13.views.objects.EnemyObject;
import de.propra13.views.objects.GameObject;
import de.propra13.views.objects.GoalObject;
import de.propra13.views.objects.HealthObject;
import de.propra13.views.objects.ItemObject;
import de.propra13.views.objects.ManaObject;
import de.propra13.views.objects.MaxObject;
import de.propra13.views.objects.MoneyObject;
import de.propra13.views.objects.MoveableGameObject;
import de.propra13.views.objects.NpcObject;
import de.propra13.views.objects.PlayerObject;
import de.propra13.views.objects.SkullObject;
import de.propra13.views.objects.StartObject;
import de.propra13.views.objects.SwordObject;
import de.propra13.views.objects.TraderObject;
import de.propra13.views.objects.WallObject;

public class Room extends Model {

    private String name;

    private Player player;

    private StartObject start;
    private GoalObject goal;
    private PlayerObject playerObject;
    private ArrayList<WallObject> walls;
    private ArrayList<SkullObject> balls;
    private ArrayList<ItemObject<?>> items;
    private ArrayList<EnemyObject> enemies;
    private ArrayList<MoveableGameObject> magics;
    private ArrayList<NpcObject<?>> npcs;

    public Room(PlayerObject playerObject, ArrayList<WallObject> walls) {
        this.playerObject = playerObject;
        this.walls = walls;
    }

    public Room(Player player, String name, String fileName, Theme theme,
            GameController controller) throws IOException {
        this.name = name;
        this.player = player;

        walls = new ArrayList<>();
        balls = new ArrayList<>();
        items = new ArrayList<>();
        enemies = new ArrayList<>();
        magics = new ArrayList<>();
        npcs = new ArrayList<>();

        String roomString = readStringFromFile(fileName);

        boolean isBossRoom = false;
        String[] lines = roomString.split("\\n");
        int y = 0;
        for (String line : lines) {
            char[] chars = line.toCharArray();
            int x = 0;
            for (char c : chars) {
                switch (c) {
                case '#':
                    walls.add(new WallObject(x, y, theme));
                    break;
                case 'S':
                    start = new StartObject(x, y, theme);
                    playerObject = new PlayerObject(this.player, x, y, theme,
                            controller);
                    break;
                case 'G':
                    goal = new GoalObject(x, y, theme);
                    break;
                case 'F':
                    balls.add(new SkullObject(new Skull(), x, y, theme));
                    break;
                case 'W':
                    items.add(new SwordObject(new Sword(100), x, y, theme));
                    break;
                case 'H':
                    items.add(new HealthObject(new Health(25), x, y, theme));
                    break;
                case 'D':
                    enemies.add(new DragonObject(new Dragon(1000), x, y, theme));
                    isBossRoom = true;
                    break;
                case 'M':
                    items.add(new MoneyObject(new Money(100), x, y, theme));
                    break;
                case 'm':
                    npcs.add(new MaxObject(new Max(), x, y, theme));
                    break;
                case 't':
                    npcs.add(new TraderObject(new Trader(), x, y, theme,
                            controller));
                    break;
                case 'E':
                    items.add(new ManaObject(new Mana(33), x, y, theme));
                    break;
                case 'A':
                    items.add(new ArmorObject(new Armor(50), x, y, theme));
                    break;
                }
                x++;
            }
            y++;
        }

        if (isBossRoom) {
            closeDoors();
        }
    }

    public ArrayList<NpcObject<?>> getNpcs() {
        return npcs;
    }

    public PlayerObject getPlayerObject() {
        return playerObject;
    }

    public void setPlayerObject(PlayerObject playerObject) {
        this.playerObject = playerObject;
    }

    public ArrayList<WallObject> getWalls() {
        return walls;
    }

    public void setWalls(ArrayList<WallObject> walls) {
        this.walls = walls;
    }

    public ArrayList<SkullObject> getBalls() {
        return balls;
    }

    public void setBalls(ArrayList<SkullObject> balls) {
        this.balls = balls;
    }

    public ArrayList<ItemObject<?>> getItems() {
        return items;
    }

    public ArrayList<EnemyObject> getEnemies() {
        return enemies;
    }

    public StartObject getStart() {
        return start;
    }

    public void setStart(StartObject start) {
        this.start = start;
    }

    public GoalObject getGoal() {
        return goal;
    }

    public void setGoal(GoalObject goal) {
        this.goal = goal;
    }

    public String getName() {
        return name;
    }

    public ArrayList<MoveableGameObject> getMagics() {
        return magics;
    }

    public void movePlayerToStart() {
        playerObject.setMoved(false);
        playerObject.moveTo(start);
    }

    public void removeItem(ItemObject<?> item) {
        items.remove(item);
    }

    public ArrayList<GameObject> getAllObjects() {
        ArrayList<GameObject> objects = new ArrayList<>();
        objects.add(start);
        objects.add(goal);
        objects.addAll(npcs);
        objects.add(playerObject);
        objects.addAll(enemies);
        objects.addAll(balls);
        objects.addAll(walls);
        objects.addAll(items);
        objects.addAll(magics);
        return objects;
    }

    public List<MoveableGameObject> getMoveableGameObjects() {
        List<MoveableGameObject> list = new ArrayList<>();
        list.add(getPlayerObject());
        list.addAll(npcs);
        list.addAll(getBalls());
        list.addAll(getEnemies());
        list.addAll(getMagics());
        return list;
    }

    public ArrayList<GameObject> getObjectsAt(int x, int y) {
        ArrayList<GameObject> objects = new ArrayList<>();
        for (GameObject object : getAllObjects()) {
            if (object.getBounds().contains(x, y))
                objects.add(object);
        }
        return objects;
    }

    public void removeEnemy(EnemyObject enemy) {
        enemies.remove(enemy);
    }

    public void addMagic(MoveableGameObject magic) {
        magics.add(magic);
    }

    public void removeMagics(MoveableGameObject magic) {
        magics.remove(magic);
    }

    public List<GameObject> getAnimatables() {
        List<GameObject> animatables = new ArrayList<>();
        animatables.add(playerObject);
        animatables.addAll(npcs);
        animatables.addAll(items);
        animatables.addAll(balls);
        animatables.addAll(enemies);
        animatables.addAll(magics);
        return animatables;
    }

    public void openDoors() {
        start.open();
        goal.open();
    }

    public void closeDoors() {
        start.close();
        goal.close();
    }
}
