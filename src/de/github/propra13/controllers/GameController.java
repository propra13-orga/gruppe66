package de.github.propra13.controllers;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JFrame;

import de.github.propra13.views.GameFieldView;

public class GameController extends Controller {

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
}
