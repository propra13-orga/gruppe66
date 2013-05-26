package de.propra13.controllers;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class ControllerFactory {

    private final Map<String, Controller> controllers = new LinkedHashMap<>();

    public void initControllers(JFrame root) {
        addController(new MenuController(this, root));
        addController(new NetworkGameMenuController(this, root));
        addController(new ClientLobbyController(this, root));
        addController(new GameController(this, root));
        addController(new LostController(this, root));
        addController(new WinController(this, root));
    }

    public void appendAllToPanel(JPanel panel) {
        for (Controller c : controllers.values()) {
            c.appendViewTo(panel);
        }
    }

    public void addController(Controller controller) {
        controllers.put(controller.getClass().getSimpleName(), controller);
    }

    public void removeController(String name) {
        controllers.remove(name);
    }

    public Controller get(String name) {
        return controllers.get(name);
    }
}
