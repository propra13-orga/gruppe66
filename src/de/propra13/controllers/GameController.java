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
import javax.swing.SwingUtilities;

import org.json.JSONException;

import de.propra13.Main;
import de.propra13.models.Level;
import de.propra13.models.Player;
import de.propra13.models.Room;
import de.propra13.models.Theme;
import de.propra13.views.GameFieldView;
import de.propra13.views.objects.FireballObject;

public class GameController extends Controller implements KeyListener,
        ComponentListener, Runnable {

    static final String CONTROLLERTAG = "gameController";

    private GameFieldView game;

    private Theme theme;

    private ArrayList<Level> levels;
    private int currentLevel;
    private int currentRoom;

    private boolean gameHasStarted = false;

    private Player player;

    private int delay = 10;

    private volatile boolean running;
    private Thread animator;

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

        game.setTheme(getCurrentLevel().getTheme());
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
            if (player.hasLives()) {
                player.die();
                getCurrentRoom().movePlayerToStart();
            } else {
                resetGame();
                showView(LostController.CONTROLLERTAG);
            }
        }
    }

    public boolean isRunning() {
        return running;
    }

    private void resetGame() {
        stop();
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
        switch (event.getKeyCode()) {
        case KeyEvent.VK_ESCAPE:
            showView(MenuController.CONTROLLERTAG);
            break;
        case KeyEvent.VK_L:
            advanceLevel();
            break;
        case KeyEvent.VK_R:
            advanceRoom();
            break;
        case KeyEvent.VK_H:
            player.addHealth(10);
            break;
        default:
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
        stop();
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

        start();
    }

    public void stop() {
        running = false;
        if (animator != null)
            animator.interrupt();
    }

    public void start() {
        game.requestFocusInWindow();
        running = true;

        animator = new Thread(this);
        animator.start();
    }

    public void turn() {
        game.getPlayerObject().move(game.getSize(), getCurrentRoom());

        for (FireballObject ball : game.getBalls()) {
            ball.move(game.getSize(), getCurrentRoom(), game.getPlayerObject());
        }
    }

    @Override
    public void run() {
        long wait, oldTime = System.currentTimeMillis();
        while (running) {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    turn();
                    game.repaint();
                    checkHealthOfPlayer();
                }

            });

            wait = Math.max(delay - (System.currentTimeMillis() - oldTime), 2);

            oldTime = System.currentTimeMillis();
            try {
                Thread.sleep(wait);
            } catch (InterruptedException e) {
                return;
            }
        }
    }
}
