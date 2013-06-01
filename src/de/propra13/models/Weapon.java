package de.propra13.models;

public class Weapon extends Item {

    public double damage = 100;

    public void useOn(Player player) {
        player.equip(this);
    }

    public double getDamage() {
        return damage;
    }

}
