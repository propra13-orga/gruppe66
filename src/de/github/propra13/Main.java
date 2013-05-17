package de.github.propra13;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.HashSet;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class Main extends JPanel implements ActionListener {

    private static final long serialVersionUID = 1337L;

    private static JFrame rootWindow;

    public static final int WIDTH = 1024;
    public static final int HEIGHT = 768;

    public static HashSet<String> views = new HashSet<String>();

    private JPanel rootPanel;

    public Main(JPanel panel) {
        rootPanel = panel;
        createGui();
    }

    private void createGui() {
        JButton new_game = new JButton("Neues Spiel");
        new_game.setMnemonic(KeyEvent.VK_N);
        new_game.setActionCommand("new_game");

        JButton close = new JButton("Beenden");
        close.setMnemonic(KeyEvent.VK_B);
        close.setActionCommand("close");

        addButton(new_game);
        addButton(close);
    }

    public void addButton(JButton button) {
        button.addActionListener(this);
        add(button);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if ("close".equals(e.getActionCommand())) {
            rootWindow.dispose();
        } else if ("new_game".equals(e.getActionCommand())) {
            if (!Main.views.contains("game_window")) {
                Game g = new Game(rootPanel);
                rootPanel.add(g, "game_window");
                Main.views.add("game_window");
            }
            Main.showView("game_window");
        }
    }

    public static void showView(String view) {
        JPanel rootPanel = (JPanel) rootWindow.getContentPane();
        CardLayout l = (CardLayout) rootPanel.getLayout();
        l.show(rootPanel, view);
    }

    public static void main(String args[]) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                rootWindow = new JFrame();
                rootWindow.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                rootWindow.setTitle("Keiler Crawler");
                rootWindow.setSize(Main.WIDTH, Main.HEIGHT);

                JPanel panel = new JPanel(new CardLayout());
                panel.setOpaque(true);
                rootWindow.setContentPane(panel);

                Main m = new Main(panel);
                panel.add(m, "root_window");

                rootWindow.setVisible(true);
            }
        });
    }
}
