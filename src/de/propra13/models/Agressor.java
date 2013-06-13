package de.propra13.models;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

public abstract class Agressor {

    protected int reloadTime = 1;
    protected boolean reloads = false;

    public abstract void inflictDamageOn(BioAgressor opponent);

    protected void reload() {
        reloads = true;
        Timer t = new Timer(reloadTime * 1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                reloads = false;
            }
        });
        t.setRepeats(false);
        t.start();
    }
}
