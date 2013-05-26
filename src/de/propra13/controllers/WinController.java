package de.propra13.controllers;

import java.awt.Dimension;

import javax.swing.JFrame;

import de.propra13.views.ImageView;

public class WinController extends MenuController {

    public WinController(ControllerFactory cf, JFrame root) {
        super(cf, root);
    }

    @Override
    protected void initialize() {
        super.initialize();

        ImageView image = new ImageView("res/win.jpg");
        image.setPreferredSize(new Dimension(400, 398));
        view.add(image);
    }
}
