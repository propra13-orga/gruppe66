package de.propra13.models;

public class Skull extends Agressor {

    private int damage = 35;

    public void setDamage(int damage) {
        this.damage = damage;
    }

    @Override
    public double getDamage() {
        return damage;
    }

}
