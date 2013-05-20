package de.github.propra13.models;

public class Player {

    private int health = 100;

    public void inflictDamage(int damage) {
        health -= damage;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public boolean isDead() {
        return health <= 0;
    }

}
