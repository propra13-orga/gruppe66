package de.propra13.models;

public class Herb extends Item {

    private int health;

    public Herb(int health) {
        this.health = health;
    }

    @Override
    public void useOn(Player player) {
        player.heal(this);
    }

    public int getHealth() {
        return health;
    }
}
