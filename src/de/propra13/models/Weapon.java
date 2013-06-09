package de.propra13.models;

public class Weapon extends Item {

    public static final String SWORD = "schwoard";
    public double damage = 100;

    @Override
    public void useOn(Player player) {
        player.equip(this);
    }

    public double getDamage() {
        return damage;
    }

}
