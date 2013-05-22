package de.propra13.controllers;

import java.awt.Dimension;

import javax.swing.JFrame;

import de.propra13.views.ImageView;

public class LostController extends MenuController {

    static final String CONTROLLERTAG = "lostController";

    public LostController(JFrame root) {
        super(root);
    }

    @Override
    protected void initialize() {
        super.initialize();

        ImageView image = new ImageView("res/problem.png");
        image.setPreferredSize(new Dimension(500, 418));
        view.add(image);
    }

    @Override
    protected String getTag() {
        return CONTROLLERTAG;
    }
}
