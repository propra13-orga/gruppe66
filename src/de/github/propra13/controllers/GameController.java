package de.github.propra13.controllers;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.ViewportLayout;

import de.github.propra13.views.GameFieldView;

public class GameController extends Controller implements KeyListener {

    static final String CONTROLLERTAG = "gameController";

    private GameFieldView game;

    public GameController(JFrame rootWindow) {
        super(rootWindow);

        view.setLayout(new ViewportLayout());
    }

    protected void initialize() {
        game = new GameFieldView();
        game.addKeyListener(this);

        view.add(game);
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
}
