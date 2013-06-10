package de.propra13.models;

import java.io.IOException;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import de.propra13.assets.Theme;
import de.propra13.assets.ThemeFactory;
import de.propra13.controllers.GameController;

public class Level extends Model {

    private String name;
    private Player player;
    private Theme theme;

    private GameController controller;
    private ArrayList<Room> rooms;

    public Level(String levelFileName, GameController controller, Player player)
            throws IOException, JSONException {

        this.player = player;
        this.controller = controller;

        JSONObject config = new JSONObject(readStringFromFile(levelFileName));

        name = config.getString("name");
        String themeName = config.getString("theme");
        JSONArray roomsConfig = config.getJSONArray("rooms");

        theme = ThemeFactory.getTheme(themeName);
        initRoomsFrom(roomsConfig);
    }

    private void initRoomsFrom(JSONArray roomsConfig) throws JSONException,
            IOException {
        rooms = new ArrayList<Room>();
        for (int i = 0; i < roomsConfig.length(); i++) {
            JSONObject roomConfig = roomsConfig.getJSONObject(i);
            addRoomFrom(roomConfig);
        }
    }

    private void addRoomFrom(JSONObject roomConfig) throws JSONException,
            IOException {
        String uri = "rooms/" + roomConfig.getString("file");
        Room room = new Room(player, roomConfig.getString("name"), uri, theme);
        rooms.add(room);
        room.getPlayerObject().setController(controller);
    }

    public String getName() {
        return name;
    }

    public ArrayList<Room> getRooms() {
        return rooms;
    }

    public Theme getTheme() {
        return theme;
    }

}
