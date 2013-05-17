package de.github.propra13.controllers;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JFrame;

import de.github.propra13.views.GameFieldView;

public class GameController extends Controller implements KeyListener {

    static final String CONTROLLERTAG = "gameController";

    private static final String BACK = "back";

    private GameFieldView game;

    public GameController(JFrame rootWindow) {
        super(rootWindow);
    }

    protected void initialize() {
        JButton button = new JButton("Zurück");
        button.setMnemonic(KeyEvent.VK_Z);
        button.setActionCommand(BACK);

        game = new GameFieldView(500, 300);
        game.addKeyListener(this);

        view.add(game);
        addButton(button);
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        if (BACK.equals(event.getActionCommand())) {
            showView(MenuController.CONTROLLERTAG);
        }
    }

    @Override
    protected String getTag() {
        return CONTROLLERTAG;
    }

    @Override
    public void keyPressed(KeyEvent event) {
        switch(event.getKeyCode()) {
        case KeyEvent.VK_UP: game.setVy(-1); break;
        case KeyEvent.VK_RIGHT: game.setVx(1); break;
        case KeyEvent.VK_DOWN: game.setVy(1); break;
        case KeyEvent.VK_LEFT: game.setVx(-1); break;
        }
    }

    @Override
    public void keyReleased(KeyEvent event) {
        switch(event.getKeyCode()) {
        case KeyEvent.VK_UP:
        case KeyEvent.VK_DOWN:
            game.setVy(0); break;
        case KeyEvent.VK_RIGHT:
        case KeyEvent.VK_LEFT:
            game.setVx(0); break;
        }
    }

    @Override
    public void keyTyped(KeyEvent event) {
    }
}
