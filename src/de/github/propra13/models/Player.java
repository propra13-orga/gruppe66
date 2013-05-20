package de.github.propra13.models;

public class Player {

    private double health = 100;

    public void inflictDamage(int damage) {
        health -= damage;
    }

    public double getHealth() {
        return health;
    }

    public void setHealth(double health) {
        this.health = health;
    }

    public boolean isDead() {
        return health <= 0;
    }

    public boolean isWounded() {
        return health < 100;
    }

}
