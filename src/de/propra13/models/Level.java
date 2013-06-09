package de.propra13.models;

import java.io.IOException;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import de.propra13.assets.Theme;
import de.propra13.controllers.GameController;

public class Level extends Model {

    private String name;
    private String levelFileName;
    private String themeName;
    private JSONArray roomsConfig;

    private Player player;
    private Theme theme;

    private GameController controller;
    private ArrayList<Room> rooms;

    public Level(String levelFileName, GameController controller, Player player)
            throws IOException, JSONException {
        this(levelFileName, controller, player, null);
    }

    public Level(Level otherLevel, GameController controller, Player player)
            throws IOException, JSONException {
        this(otherLevel.getLevelFileName(), controller, player, otherLevel
                .getTheme());
    }

    public Level(String levelFileName, GameController controller,
            Player player, Theme theme) throws IOException, JSONException {
        this.player = player;
        this.levelFileName = levelFileName;
        this.controller = controller;

        String config = readStringFromFile(levelFileName);

        JSONObject configObject = new JSONObject(config);

        name = configObject.getString("name");
        themeName = configObject.getString("theme");
        roomsConfig = configObject.getJSONArray("rooms");

        if (theme != null)
            this.theme = theme;
        else
            initThemeFrom(themeName);

        initRoomsFrom(roomsConfig);
    }

    public String getLevelFileName() {
        return levelFileName;
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

    public Theme getTheme() {
        return theme;
    }

}
