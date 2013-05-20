package de.github.propra13.controllers;

import java.awt.CardLayout;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JViewport;

public abstract class Controller implements ActionListener {

    protected JFrame rootWindow;

    protected JViewport view;

    public JViewport getView() {
        return view;
    }

    public Controller(JFrame root) {
        rootWindow = root;

        initView();
        initialize();
    }

    abstract protected void initialize();

    abstract protected String getTag();

    protected void initView() {
        view = new JViewport();
        view.setFocusable(true);
    }

    protected void dispose() {
        rootWindow.dispose();
    }

    protected void showView(String view) {
        JPanel rootPanel = (JPanel) rootWindow.getContentPane();
        CardLayout layout = (CardLayout) rootPanel.getLayout();
        layout.show(rootPanel, view);
    }

    public void appendViewTo(JPanel panel) {
        panel.add(view, getTag());
    }
}
