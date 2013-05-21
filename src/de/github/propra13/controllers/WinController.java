package de.github.propra13.controllers;

import java.awt.Dimension;

import javax.swing.JFrame;

import de.github.propra13.views.ImageView;

public class WinController extends MenuController {
    static final String CONTROLLERTAG = "winController";

    public WinController(JFrame root) {
        super(root);
    }

    @Override
    protected void initialize() {
        super.initialize();

        ImageView image = new ImageView("res/win.jpg");
        image.setPreferredSize(new Dimension(400, 398));
        view.add(image);
    }

    @Override
    protected String getTag() {
        return CONTROLLERTAG;
    }
}
