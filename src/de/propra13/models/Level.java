package de.propra13.models;

import java.io.IOException;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import de.propra13.controllers.GameController;
import de.propra13.views.objects.Theme;

public class Level extends Model {

    private String name;
    private String themeName;
    private JSONArray roomsConfig;

    private Player player;
    private Theme theme;

    private GameController controller;
    private ArrayList<Room> rooms;

    public Level(String levelFileName, GameController controller, Player player)
            throws IOException, JSONException {
        this.player = player;
        this.controller = controller;

        String config = readStringFromFile(levelFileName);

        JSONObject configObject = new JSONObject(config);

        name = configObject.getString("name");
        themeName = configObject.getString("theme");
        roomsConfig = configObject.getJSONArray("rooms");

        initThemeFrom(themeName);
        initRoomsFrom(roomsConfig);
    }

    private void initThemeFrom(String themename) {
        theme = new Theme(themename);
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

}
