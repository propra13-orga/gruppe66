package de.propra13.controllers;

import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JFrame;

public class NetworkGameMenuController extends Controller {

    private static final String HOST = "host";
    private static final String JOIN = "join";
    private static final String BACK = "back";

    public NetworkGameMenuController(ControllerFactory cf, JFrame root) {
        super(cf, root);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
        case BACK:
            transitionTo(MenuController.class.getSimpleName());
            break;
        case HOST:
            break;
        case JOIN:
            String addr = getInputDialog("Bitte Serveradresse eingeben");
            transitionTo(ClientLobbyController.class.getSimpleName(), addr);
            break;
        }
    }

    @Override
    protected void initialize() {

        JButton serverButton = new JButton("Spiel hosten");
        serverButton.setActionCommand(HOST);

        JButton clientButton = new JButton("Am Spiel teilnehmen");
        clientButton.setActionCommand(JOIN);

        JButton backButton = new JButton("Zum Startbildschirm");
        backButton.setActionCommand(BACK);

        backButton.addActionListener(this);
        serverButton.addActionListener(this);
        clientButton.addActionListener(this);

        view.add(serverButton);
        view.add(clientButton);
        view.add(backButton);
    }
}
