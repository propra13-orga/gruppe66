package de.propra13.models;

public class Health extends Item {

    private int health;

    public Health(int health) {
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
