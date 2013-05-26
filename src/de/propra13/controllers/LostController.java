package de.propra13.controllers;

import java.awt.Dimension;

import javax.swing.JFrame;

import de.propra13.views.ImageView;

public class LostController extends MenuController {

    public LostController(ControllerFactory cf, JFrame root) {
        super(cf, root);
    }

    @Override
    protected void initialize() {
        super.initialize();

        ImageView image = new ImageView("res/problem.png");
        image.setPreferredSize(new Dimension(500, 418));
        view.add(image);
    }
}
