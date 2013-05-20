package de.github.propra13.models;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import de.github.propra13.objects.GoalObject;
import de.github.propra13.objects.PlayerObject;
import de.github.propra13.objects.StartObject;
import de.github.propra13.objects.WallObject;

public class Room {

    private StartObject start;
    private GoalObject goal;
    private PlayerObject player;
    private ArrayList<WallObject> walls;
    private ArrayList<FireballObject> balls;

    public Room(PlayerObject player, ArrayList<WallObject> walls) {
        this.player = player;
        this.walls = walls;
    }

    public Room(String fileName) {
        ArrayList<WallObject> walls = new ArrayList<WallObject>();
        ArrayList<FireballObject> balls = new ArrayList<FireballObject>();
        try {
            FileReader file = new FileReader(fileName);
            BufferedReader reader = new BufferedReader(file);

            String line = reader.readLine();
            int y = 0;
            do {
                char[] chars = line.toCharArray();
                int x = 0;
                for (char c : chars) {
                    switch (c) {
                    case '#':
                        walls.add(new WallObject(x, y));
                        break;
                    case 'S':
                        start = new StartObject(x, y);
                        player = new PlayerObject(x, y);
                        break;
                    case 'G':
                        goal = new GoalObject(x, y);
                        break;
                    case 'F':
                        balls.add(new FireballObject(x, y));
                        break;
                    }
                    x++;
                }
                y++;
            } while ((line = reader.readLine()) != null);

            this.walls = walls;
            this.balls = balls;
            reader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public PlayerObject getPlayer() {
        return player;
    }

    public void setPlayer(PlayerObject player) {
        this.player = player;
    }

    public ArrayList<WallObject> getWalls() {
        return walls;
    }

    public void setWalls(ArrayList<WallObject> walls) {
        this.walls = walls;
    }

    public ArrayList<FireballObject> getBalls() {
        return balls;
    }

    public void setBalls(ArrayList<FireballObject> balls) {
        this.balls = balls;
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

}
