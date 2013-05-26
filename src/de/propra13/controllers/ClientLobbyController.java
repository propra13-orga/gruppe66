package de.propra13.controllers;

import java.awt.event.ActionEvent;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class ClientLobbyController extends Controller {

    public ClientLobbyController(ControllerFactory controllerFactory,
            JFrame root) {
        super(controllerFactory, root);
    }

    private JLabel addressLabel;

    @Override
    public void actionPerformed(ActionEvent e) {
    }

    @Override
    protected void initialize() {

        addressLabel = new JLabel();

        view.add(addressLabel);
    }

    @Override
    protected void willAppear(Object... params) {
        if (params.length > 0)
            addressLabel.setText(params[0].toString());
    }

    @Override
    protected void willDisappear() {
    }
}
