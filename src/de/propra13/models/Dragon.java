package de.propra13.models;

public class Dragon extends BioAgressor {

    private int damage = 50;

    public Dragon(int maxhealth) {
        super(maxhealth);
        reloadTime = 5;
    }

    @Override
    public void sufferDamage(double damage) {
        health -= damage;
    }

    @Override
    public double getDamage() {
        return damage;
    }
}
