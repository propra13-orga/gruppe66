package de.github.propra13.models;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import de.github.propra13.objects.Goal;
import de.github.propra13.objects.Player;
import de.github.propra13.objects.Start;
import de.github.propra13.objects.Wall;

public class Room {

    private Start start;
    private Goal goal;
    private Player player;
    private ArrayList<Wall> walls;

    public Room(Player player, ArrayList<Wall> walls) {
        this.player = player;
        this.walls = walls;
    }

    public Room(Player player, String fileName) {
        this.player = player;
        ArrayList<Wall> walls = new ArrayList<Wall>();
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
                        walls.add(new Wall(x, y));
                        break;
                    case 'S':
                        start = new Start(x, y);
                        player.moveTo(x, y);
                        break;
                    case 'G':
                        goal = new Goal(x, y);
                        break;
                    }
                    x++;
                }
                y++;
            } while ((line = reader.readLine()) != null);

            this.walls = walls;
            reader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public ArrayList<Wall> getWalls() {
        return walls;
    }

    public void setWalls(ArrayList<Wall> walls) {
        this.walls = walls;
    }

    public Start getStart() {
        return start;
    }

    public void setStart(Start start) {
        this.start = start;
    }

    public Goal getGoal() {
        return goal;
    }

    public void setGoal(Goal goal) {
        this.goal = goal;
    }

}
