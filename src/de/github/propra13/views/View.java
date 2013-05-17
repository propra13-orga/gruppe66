package de.github.propra13.views;

import java.awt.CardLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public abstract class View extends JPanel implements ActionListener {

    private static final long serialVersionUID = 8897807168223893189L;

    protected JFrame rootWindow;

    public View(JFrame root) {
        rootWindow = root;
        createGui();
    }

    protected void createGui() {
    }

    protected void addButton(JButton button) {
        button.addActionListener(this);
        add(button);
    }

    protected void dispose() {
        rootWindow.dispose();
    }

    protected void showView(String view) {
        JPanel rootPanel = (JPanel) rootWindow.getContentPane();
        CardLayout layout = (CardLayout) rootPanel.getLayout();
        layout.show(rootPanel, view);
    }
}
