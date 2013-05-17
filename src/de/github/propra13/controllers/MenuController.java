package de.github.propra13.controllers;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JFrame;

public class MenuController extends Controller {

    static final String CONTROLLERTAG = "menuController";

    private static final String NEWGAME = "newGame";
    private static final String CLOSE = "close";

    public MenuController(JFrame rootWindow) {
        super(rootWindow);
    }

    protected void initialize() {
        JButton newGameButton = new JButton("Neues Spiel");
        newGameButton.setMnemonic(KeyEvent.VK_N);
        newGameButton.setActionCommand(NEWGAME);

        JButton closeButton = new JButton("Beenden");
        closeButton.setMnemonic(KeyEvent.VK_B);
        closeButton.setActionCommand(CLOSE);

        addButton(newGameButton);
        addButton(closeButton);
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        if (CLOSE.equals(event.getActionCommand())) {
            dispose();
        } else if (NEWGAME.equals(event.getActionCommand())) {
            showView(GameController.CONTROLLERTAG);
        }
    }

    @Override
    protected String getTag() {
        return CONTROLLERTAG;
    }
}
