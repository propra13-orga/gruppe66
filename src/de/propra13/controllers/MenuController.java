package de.propra13.controllers;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class MenuController extends Controller {

    static final String CONTROLLERTAG = "menuController";

    private static final String NEWGAME = "newGame";
    private static final String CLOSE = "close";

    protected JPanel buttonPanel;

    public MenuController(JFrame rootWindow) {
        super(rootWindow);
    }

    protected void initialize() {
        view.setLayout(new BoxLayout(view, BoxLayout.Y_AXIS));

        buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.white);
        view.add(buttonPanel);

        JButton newGameButton = new JButton("Neues Spiel");
        newGameButton.setMnemonic(KeyEvent.VK_N);
        newGameButton.setActionCommand(NEWGAME);

        JButton closeButton = new JButton("Beenden");
        closeButton.setMnemonic(KeyEvent.VK_B);
        closeButton.setActionCommand(CLOSE);

        addButton(newGameButton);
        addButton(closeButton);
    }

    protected void addButton(JButton button) {
        button.addActionListener(this);
        buttonPanel.add(button);
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
