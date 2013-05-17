package de.github.propra13.views;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JFrame;

public class GameView extends View {

    private static final long serialVersionUID = -8793507774953311269L;

    public GameView(JFrame rootWindow) {
        super(rootWindow);
    }

    protected void createGui() {
        JButton button = new JButton("Zurück");
        button.setMnemonic(KeyEvent.VK_Z);
        button.setActionCommand("back");

        addButton(button);
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        if ("back".equals(event.getActionCommand())) {
            showView("rootView");
        }
    }
}
