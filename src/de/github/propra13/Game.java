package de.github.propra13;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JPanel;

public class Game extends JPanel implements ActionListener {

    private static final long serialVersionUID = -8793507774953311269L;

    private JPanel rootPanel;

    public Game(JPanel panel) {
        rootPanel = panel;
        this.setOpaque(true);

        JButton button = new JButton("Zurück");
        button.setText("BLABLABAL");
        button.addActionListener(this);
        button.setMnemonic(KeyEvent.VK_Z);
        button.setActionCommand("back");
        add(button);
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        if ("back".equals(event.getActionCommand())) {
            Main.showView("root_window");
        }
    }
}
