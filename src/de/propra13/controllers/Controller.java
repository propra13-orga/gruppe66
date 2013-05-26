package de.propra13.controllers;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public abstract class Controller implements ActionListener {

    private ControllerFactory controllerFactory;

    protected JFrame rootWindow;

    protected JPanel view;

    public JPanel getView() {
        return view;
    }

    public Controller(ControllerFactory controllerFactory, JFrame root) {
        this.controllerFactory = controllerFactory;
        rootWindow = root;

        initView();
        initialize();
    }

    abstract protected void initialize();

    protected void initView() {
        view = new JPanel();
        view.setFocusable(true);
        view.setBackground(Color.white);
        ((FlowLayout) view.getLayout()).setVgap(0);
        ((FlowLayout) view.getLayout()).setHgap(0);
    }

    public ControllerFactory getControllerFactory() {
        return controllerFactory;
    }

    protected void dispose() {
        rootWindow.dispose();
    }

    protected void willAppear(Object... params) {
    }

    protected void willDisappear() {

    }

    protected void transitionTo(String controller, Object... params) {
        JPanel rootPanel = (JPanel) rootWindow.getContentPane();
        CardLayout layout = (CardLayout) rootPanel.getLayout();
        willDisappear();
        getControllerFactory().get(controller).willAppear(params);
        layout.show(rootPanel, controller);
    }

    public void appendViewTo(JPanel panel) {
        panel.add(view, getClass().getSimpleName());
    }

    public String getInputDialog(String msg) {
        return JOptionPane.showInputDialog(msg);
    }
}
