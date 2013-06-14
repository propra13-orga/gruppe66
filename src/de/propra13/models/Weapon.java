package de.propra13.models;

public abstract class Weapon extends Item {

    private double damage;

    public Weapon(double damage) {
        this.damage = damage;
    }

    public double getDamage() {
        return damage;
    }

    @Override
    public void useOn(Player player) {
        player.equip(this);
    }

}
