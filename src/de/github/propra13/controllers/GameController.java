package de.github.propra13.controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.JFrame;

import de.github.propra13.objects.Wall;
import de.github.propra13.objects.Player;
import de.github.propra13.views.GameFieldView;

public class GameController extends Controller implements KeyListener,
        ComponentListener {

    static final String CONTROLLERTAG = "gameController";

    private GameFieldView game;

    private Player player;

    private ArrayList<Wall> walls;

    public GameController(JFrame rootWindow) {
        super(rootWindow);
    }

    protected void initialize() {
        game = new GameFieldView();
        game.addKeyListener(this);

        view.add(game);
        view.addComponentListener(this);
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
        game.setPlayer(player);
    }

    public ArrayList<Wall> getWalls() {
        return walls;
    }

    public void setWalls(ArrayList<Wall> walls) {
        this.walls = walls;
        game.setWalls(walls);
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
            player.keyPressed(event);
        }
    }

    @Override
    public void keyReleased(KeyEvent event) {
        player.keyReleased(event);
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
        game.start();
    }
}
