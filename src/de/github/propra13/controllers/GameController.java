package de.github.propra13.controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;

import de.github.propra13.views.GameFieldView;

public class GameController extends Controller implements KeyListener, ComponentListener {

    static final String CONTROLLERTAG = "gameController";

    private GameFieldView game;

    public GameController(JFrame rootWindow) {
        super(rootWindow);
    }

    protected void initialize() {
        game = new GameFieldView(800, 600);
        game.addKeyListener(this);

        view.add(game);
        view.addComponentListener(this);
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
        case KeyEvent.VK_UP:
            game.setVy(-1);
            break;
        case KeyEvent.VK_RIGHT:
            game.setVx(1);
            break;
        case KeyEvent.VK_DOWN:
            game.setVy(1);
            break;
        case KeyEvent.VK_LEFT:
            game.setVx(-1);
            break;
        case KeyEvent.VK_ESCAPE:
            showView(MenuController.CONTROLLERTAG);
        }
    }

    @Override
    public void keyReleased(KeyEvent event) {
        switch (event.getKeyCode()) {
        case KeyEvent.VK_UP:
        case KeyEvent.VK_DOWN:
            game.setVy(0);
            break;
        case KeyEvent.VK_RIGHT:
        case KeyEvent.VK_LEFT:
            game.setVx(0);
            break;
        }
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
