package de.github.propra13.controllers;

import java.awt.CardLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public abstract class Controller implements ActionListener {

    protected JFrame rootWindow;

    protected JPanel view;

    public JPanel getView() {
        return view;
    }

    public Controller(JFrame root) {
        rootWindow = root;
        view = new JPanel();
        initialize();
    }

    abstract protected void initialize();
    abstract protected String getTag();

    protected void addButton(JButton button) {
        button.addActionListener(this);
        view.add(button);
    }

    protected void dispose() {
        rootWindow.dispose();
    }

    protected void showView(String view) {
        JPanel rootPanel = (JPanel) rootWindow.getContentPane();
        CardLayout layout = (CardLayout) rootPanel.getLayout();
        layout.show(rootPanel, view);
    }

    public void appendTo(JPanel panel) {
        panel.add(view, getTag());
    }
}
