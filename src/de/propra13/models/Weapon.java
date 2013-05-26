package de.propra13.models;

public class Weapon extends Item {

    public int damage = 50000;

    public void useOn(Player player) {
        player.equip(this);
    }

}
