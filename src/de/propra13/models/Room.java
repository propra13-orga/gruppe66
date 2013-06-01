package de.propra13.models;

import java.io.IOException;
import java.util.ArrayList;

import de.propra13.assets.Theme;
import de.propra13.views.objects.SkullObject;
import de.propra13.views.objects.GoalObject;
import de.propra13.views.objects.HerbObject;
import de.propra13.views.objects.ItemObject;
import de.propra13.views.objects.PlayerObject;
import de.propra13.views.objects.StartObject;
import de.propra13.views.objects.WallObject;
import de.propra13.views.objects.WeaponObject;

public class Room extends Model {

    private String name;

    private Player player;

    private StartObject start;
    private GoalObject goal;
    private PlayerObject playerObject;
    private ArrayList<WallObject> walls;
    private ArrayList<SkullObject> balls;
    private ArrayList<ItemObject> items;

    public Room(PlayerObject playerObject, ArrayList<WallObject> walls) {
        this.playerObject = playerObject;
        this.walls = walls;
    }

    public Room(Player player, String name, String fileName, Theme theme)
            throws IOException {
        this.name = name;
        this.player = player;

        ArrayList<WallObject> walls = new ArrayList<WallObject>();
        ArrayList<SkullObject> balls = new ArrayList<SkullObject>();
        ArrayList<ItemObject> items = new ArrayList<ItemObject>();
        String roomString = readStringFromFile(fileName);

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
                    playerObject = new PlayerObject(this.player, x, y, theme);
                    break;
                case 'G':
                    goal = new GoalObject(x, y, theme);
                    break;
                case 'F':
                    balls.add(new SkullObject(new Skull(), x, y, theme));
                    break;
                case 'W':
                    items.add(new WeaponObject(new Weapon(), x, y, theme));
                    break;
                case 'H':
                    items.add(new HerbObject(x, y, theme));
                }
                x++;
            }
            y++;
        }

        this.walls = walls;
        this.balls = balls;
        this.items = items;
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

    public ArrayList<ItemObject> getItems() {
        return items;
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

    public void movePlayerToStart() {
        playerObject.setMoved(false);
        playerObject.moveTo(start);
    }

    public void removeItem(ItemObject item) {
        items.remove(item);
    }

}
