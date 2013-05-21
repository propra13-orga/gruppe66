package de.github.propra13.controllers;

import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JFrame;

import de.github.propra13.views.ImageView;

public class LostController extends MenuController {

    static final String CONTROLLERTAG = "lostController";

    public LostController(JFrame root) {
        super(root);
    }

    @Override
    protected void initialize() {
        view.setLayout(new BoxLayout(view, BoxLayout.Y_AXIS));
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
