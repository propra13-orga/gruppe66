package de.github.propra13.views;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JFrame;

import de.github.propra13.Main;

public class MenuView extends View {

    private static final long serialVersionUID = -4747365268577807859L;

    private static final String NEWGAME = "newGame";
    private static final String CLOSE = "close";

    public MenuView(JFrame rootWindow) {
        super(rootWindow);
    }

    protected void createGui() {
        JButton new_game = new JButton("Neues Spiel");
        new_game.setMnemonic(KeyEvent.VK_N);
        new_game.setActionCommand(NEWGAME);

        JButton close = new JButton("Beenden");
        close.setMnemonic(KeyEvent.VK_B);
        close.setActionCommand(CLOSE);

        addButton(new_game);
        addButton(close);
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        if (CLOSE.equals(event.getActionCommand())) {
            dispose();
        } else if (NEWGAME.equals(event.getActionCommand())) {
            showView(Main.GAMEVIEW);
        }
    }
}
