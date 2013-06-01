package de.propra13.models;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

public abstract class Agressor {

    public final double MAXHEALTH;

    protected double health;

    protected int reloadTime = 1;
    protected boolean reloads = false;

    public Agressor() {
        MAXHEALTH = Double.POSITIVE_INFINITY;
    }

    public Agressor(int maxhealth) {
        MAXHEALTH = health = maxhealth;
    }

    public abstract void sufferDamage(double damage);

    public abstract void inflictDamageOn(Agressor opponent);

    public boolean isWounded() {
        return health < MAXHEALTH;
    }

    public boolean isDead() {
        return health <= 0;
    }

    public double getHealth() {
        return health;
    }

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
