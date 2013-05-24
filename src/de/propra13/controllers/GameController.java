package de.propra13.controllers;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.JFrame;

import de.propra13.Main;
import de.propra13.models.Player;
import de.propra13.models.Room;
import de.propra13.views.GameFieldView;
import de.propra13.views.objects.Theme;

public class GameController extends Controller implements KeyListener,
        ComponentListener {

    static final String CONTROLLERTAG = "gameController";

    private GameFieldView game;

    private Theme theme;

    private ArrayList<Room> rooms;
    private int currentRoom;

    private boolean gameHasStarted = false;

    private Player player;

    public GameController(JFrame rootWindow) {
        super(rootWindow);
    }

    protected void initialize() {
        theme = new Theme("dungeon");
        initPlayerAndRooms();

        game = new GameFieldView(this, theme);
        game.addKeyListener(this);
        game.setPreferredSize(new Dimension(Main.WIDTH, Main.HEIGHT));

        view.add(game);
        view.addComponentListener(this);
    }

    private void initPlayerAndRooms() {
        player = new Player();

        rooms = new ArrayList<Room>();
        addRoom(new Room(player, "res/leveltheme/dungeon/rooms/room1.krm",
                theme));
        addRoom(new Room(player, "res/leveltheme/dungeon/rooms/room2.krm",
                theme));
        addRoom(new Room(player, "res/leveltheme/dungeon/rooms/room3.krm",
                theme));
    }

    public ArrayList<Room> getRooms() {
        return rooms;
    }

    public void addRoom(Room room) {
        this.rooms.add(room);
        room.getPlayerObject().setController(this);
    }

    public void advanceRoom() {
        if (currentRoom + 1 < rooms.size())
            setRoom(currentRoom + 1);
        else {
            resetGame();
            showView(WinController.CONTROLLERTAG);
        }
    }

    public void retreatRoom() {
        if (currentRoom > 0)
            setRoom(currentRoom - 1);
    }

    private void setRoom(int room) {
        Room current = rooms.get(currentRoom);
        Room next = rooms.get(room);

        next.getPlayerObject().setVx(current.getPlayerObject().getVx());
        next.getPlayerObject().setVy(current.getPlayerObject().getVy());

        currentRoom = room;
        game.setCurrentRoom(next);
    }

    public void checkHealthOfPlayer() {
        if (player.isDead()) {
            resetGame();
            showView(LostController.CONTROLLERTAG);
        }
    }

    private void resetGame() {
        game.stop();
        gameHasStarted = false;
        initPlayerAndRooms();
        currentRoom = 0;
    }

    @Override
    public void actionPerformed(ActionEvent event) {
    }

    @Override
    protected String getTag() {
        return CONTROLLERTAG;
    }

    @Override
    public void keyPressed(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.VK_ESCAPE) {
            showView(MenuController.CONTROLLERTAG);
        } else {
            rooms.get(currentRoom).getPlayerObject().keyPressed(event);
        }
    }

    @Override
    public void keyReleased(KeyEvent event) {
        rooms.get(currentRoom).getPlayerObject().keyReleased(event);
    }

    @Override
    public void keyTyped(KeyEvent event) {
    }

    @Override
    public void componentHidden(ComponentEvent event) {
        game.stop();
    }

    @Override
    public void componentMoved(ComponentEvent event) {
    }

    @Override
    public void componentResized(ComponentEvent event) {
    }

    @Override
    public void componentShown(ComponentEvent event) {
        if (!gameHasStarted) {
            game.setCurrentRoom(rooms.get(currentRoom));
            gameHasStarted = true;
        }

        game.start();
    }
}
