package de.propra13.models;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

public class Skull {

    private int reloadTime = 1;
    private int damage = 35;

    private boolean reloads = false;

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public void inflictDamageOn(Player player) {
        if (!reloads) {
            player.inflictDamage(damage);
            reloads = true;

            reload();
        }
    }

    private void reload() {
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
