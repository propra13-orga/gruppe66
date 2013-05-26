package de.propra13.controllers;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class MenuController extends Controller {

    private static final String NEWGAME = "newGame";
    private static final String CLOSE = "close";
    private static final String NEWNETWORK = "newNetworkGame";

    protected JPanel buttonPanel;

    public MenuController(ControllerFactory cf, JFrame rootWindow) {
        super(cf, rootWindow);
    }

    protected void initialize() {
        view.setLayout(new BoxLayout(view, BoxLayout.Y_AXIS));

        buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.white);
        view.add(buttonPanel);

        JButton newGameButton = new JButton("Neues Spiel");
        newGameButton.setMnemonic(KeyEvent.VK_N);
        newGameButton.setActionCommand(NEWGAME);

        JButton networkGameButton = new JButton("Neues Netzwerkspiel");
        networkGameButton.setMnemonic(KeyEvent.VK_S);
        networkGameButton.setActionCommand(NEWNETWORK);

        JButton closeButton = new JButton("Beenden");
        closeButton.setMnemonic(KeyEvent.VK_B);
        closeButton.setActionCommand(CLOSE);

        addButton(newGameButton);
        addButton(closeButton);
        addButton(networkGameButton);
    }

    protected void addButton(JButton button) {
        button.addActionListener(this);
        buttonPanel.add(button);
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        switch (event.getActionCommand()) {
        case CLOSE:
            dispose();
            break;
        case NEWGAME:
            transitionTo(GameController.class.getSimpleName());
            break;
        case NEWNETWORK:
            transitionTo(NetworkGameMenuController.class.getSimpleName());
            break;
        }
    }
}
