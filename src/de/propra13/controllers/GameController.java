package de.propra13.controllers;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JFrame;

import org.json.JSONException;

import de.propra13.Main;
import de.propra13.models.Level;
import de.propra13.models.Player;
import de.propra13.models.Room;
import de.propra13.views.GameFieldView;
import de.propra13.views.objects.Theme;

public class GameController extends Controller implements KeyListener,
        ComponentListener {

    static final String CONTROLLERTAG = "gameController";

    private GameFieldView game;

    private Theme theme;

    private ArrayList<Level> levels;
    private int currentLevel;
    private int currentRoom;

    private boolean gameHasStarted = false;

    private Player player;

    public GameController(JFrame rootWindow) {
        super(rootWindow);
    }

    protected void initialize() {
        theme = new Theme("dungeon");
        initPlayerAndLevels();

        game = new GameFieldView(this, theme);
        game.addKeyListener(this);
        game.setPreferredSize(new Dimension(Main.WIDTH, Main.HEIGHT));

        view.add(game);
        view.addComponentListener(this);
    }

    private void initPlayerAndLevels() {
        player = new Player();

        levels = new ArrayList<Level>();
        try {
            addLevel(new Level("level1.json", this, player));
            addLevel(new Level("level2.json", this, player));
            addLevel(new Level("level3.json", this, player));
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Level> getLevels() {
        return levels;
    }

    public void addLevel(Level level) {
        levels.add(level);
    }

    public void advanceRoom() {
        if (currentRoom + 1 < getCurrentLevel().getRooms().size())
            setRoom(currentRoom + 1);
        else {
            advanceLevel();
        }
    }

    public void advanceLevel() {
        if (currentLevel + 1 < levels.size()) {
            setLevel(currentLevel + 1);
        } else {
            resetGame();
            showView(WinController.CONTROLLERTAG);
        }
    }

    public void retreatRoom() {
        if (currentRoom > 0)
            setRoom(currentRoom - 1);
    }

    private Level getCurrentLevel() {
        return levels.get(currentLevel);
    }

    private Room getCurrentRoom() {
        return getCurrentLevel().getRooms().get(currentRoom);
    }

    private void setLevel(int level) {
        currentLevel = level;
        setRoom(0);
    }

    private void setRoom(int room) {
        Room current = getCurrentRoom();
        Room next = getCurrentLevel().getRooms().get(room);

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
        initPlayerAndLevels();
        currentLevel = 0;
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
            getCurrentRoom().getPlayerObject().keyPressed(event);
        }
    }

    @Override
    public void keyReleased(KeyEvent event) {
        getCurrentRoom().getPlayerObject().keyReleased(event);
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
            game.setCurrentRoom(getCurrentRoom());
            gameHasStarted = true;
        }

        game.start();
    }
}
