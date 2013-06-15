package de.propra13.controllers;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import org.json.JSONException;

import de.propra13.Main;
import de.propra13.assets.Theme;
import de.propra13.assets.ThemeFactory;
import de.propra13.assets.animations.AnimationStateListener;
import de.propra13.models.Club;
import de.propra13.models.Level;
import de.propra13.models.Player;
import de.propra13.models.Room;
import de.propra13.views.GameFieldView;
import de.propra13.views.HUDView;
import de.propra13.views.objects.GameObject;
import de.propra13.views.objects.MoveableGameObject;

public class GameController extends Controller implements KeyListener,
        ComponentListener, MouseListener, Runnable {

    private GameFieldView game;
    private HUDView hud;

    private Theme theme;

    private ArrayList<Level> levels;
    private int currentLevel;
    private int currentRoom;

    private boolean gameHasStarted = false;

    private Player player;

    private volatile boolean running;
    private Thread gameRunner;
    private Timer objectAnimator;

    private final int runningDelay = 10;
    private final int animationDelay = 45;

    private volatile boolean playerDies;

    public GameController(ControllerFactory cf, JFrame rootWindow) {
        super(cf, rootWindow);
    }

    @Override
    protected void initialize() {
        theme = ThemeFactory.getTheme("dungeon");
        initPlayerAndLevels();

        game = new GameFieldView(this, theme);
        game.addKeyListener(this);
        game.addMouseListener(this);
        game.setPreferredSize(new Dimension(Main.GAMEFIELDWIDTH,
                Main.GAMEFIELDHEIGHT));

        try {
            hud = new HUDView(this, theme);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
        hud.setPreferredSize(new Dimension(Main.GAMEFIELDWIDTH, Main.HUDHEIGHT));

        view.add(hud);
        view.add(game);
        view.addComponentListener(this);
    }

    private void initPlayerAndLevels() {
        player = new Player(new Club(5), 100);
        initLevels();
    }

    private void initLevels() {
        try {
            levels = new ArrayList<Level>();
            addLevel(new Level("level1.json", this, player));
            addLevel(new Level("level2.json", this, player));
            addLevel(new Level("level3.json", this, player));
        } catch (JSONException | IOException e) {
            e.printStackTrace();
            System.out.println("Could not parse json level files");
            System.exit(1);
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
            transitionTo(WinController.class.getSimpleName());
        }
    }

    public void retreatRoom() {
        if (currentRoom > 0)
            setRoom(currentRoom - 1);
    }

    public Level getCurrentLevel() {
        return levels.get(currentLevel);
    }

    public Room getCurrentRoom() {
        return getCurrentLevel().getRooms().get(currentRoom);
    }

    private void setLevel(int level) {
        currentLevel = level;

        hud.setTheme(getCurrentLevel().getTheme());
        game.setTheme(getCurrentLevel().getTheme());

        setRoom(0);
    }

    private void setRoom(int room) {
        Room current = getCurrentRoom();
        Room next = getCurrentLevel().getRooms().get(room);

        next.getPlayerObject().setDirection(
                current.getPlayerObject().getDirection());

        next.getPlayerObject().setMoved(false);

        currentRoom = room;
    }

    public void checkHealthOfPlayer() {
        if (player.isDead() && !playerDies) {
            playerDies = true;
            getCurrentRoom().getPlayerObject().triggerDeath(
                    new AnimationStateListener() {

                        @Override
                        public void willStart() {
                        }

                        @Override
                        public void didEnd() {
                            if (player.hasLifes()) {
                                player.die();
                                getCurrentRoom().movePlayerToStart();
                                playerDies = false;
                            } else {
                                resetGame();
                                transitionTo(LostController.class
                                        .getSimpleName());
                            }
                        }

                    });
        }
    }

    public boolean isRunning() {
        return running;
    }

    private void resetGame() {
        stop();
        gameHasStarted = false;
        playerDies = false;
        initPlayerAndLevels();
        currentLevel = 0;
        currentRoom = 0;

        game.setTheme(getCurrentLevel().getTheme());
        hud.setTheme(getCurrentLevel().getTheme());
    }

    @Override
    public void actionPerformed(ActionEvent event) {
    }

    @Override
    public void keyPressed(KeyEvent event) {
        switch (event.getKeyCode()) {
        case KeyEvent.VK_ESCAPE:
            transitionTo(MenuController.class.getSimpleName());
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
        case KeyEvent.VK_SPACE:
            getCurrentRoom().getPlayerObject().inflictDamageOn(
                    getCurrentRoom().getEnemies());
            break;
        default:
            for (MoveableGameObject object : getCurrentRoom()
                    .getMoveableGameObjects()) {
                object.keyPressed(event);
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent event) {
        switch (event.getKeyCode()) {
        case KeyEvent.VK_SPACE:
            break;
        default:
            for (MoveableGameObject object : getCurrentRoom()
                    .getMoveableGameObjects()) {
                object.keyReleased(event);
            }
        }
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
            gameHasStarted = true;
        }

        start();
    }

    public void stop() {
        running = false;
        if (gameRunner != null)
            gameRunner.interrupt();

        if (objectAnimator != null)
            objectAnimator.stop();
    }

    public void start() {
        game.requestFocusInWindow();
        running = true;

        gameRunner = new Thread(this);
        gameRunner.start();

        objectAnimator = new Timer(animationDelay, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                animate();
            }
        });
        objectAnimator.start();
    }

    public void turn() {
        for (MoveableGameObject object : getCurrentRoom()
                .getMoveableGameObjects()) {
            object.act(game.getSize(), getCurrentRoom());
        }
    }

    public void animate() {
        for (GameObject object : getCurrentRoom().getAnimatables()) {
            object.animate();
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
                    hud.repaint();
                    game.repaint();
                    checkHealthOfPlayer();
                }

            });

            wait = Math.max(runningDelay
                    - (System.currentTimeMillis() - oldTime), 2);

            oldTime = System.currentTimeMillis();
            try {
                Thread.sleep(wait);
            } catch (InterruptedException e) {
                return;
            }
        }
    }

    @Override
    protected void willAppear(Object... params) {
    }

    @Override
    protected void willDisappear() {
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        for (GameObject object : getCurrentRoom().getObjectsAt(e.getX(),
                e.getY()))
            object.toggleDebug();

        getCurrentRoom().getPlayerObject().performMagicIn(getCurrentRoom(),
                e.getX(), e.getY());
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
}
