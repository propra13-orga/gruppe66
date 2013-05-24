package de.propra13.models;

public class Player {

    private final int MAXHEALTH = 100;

    private double health = MAXHEALTH;
    private int lives = 3;

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
        return health < MAXHEALTH;
    }

    public boolean hasLives() {
        return lives > 1;
    }

    public void die() {
        lives--;
        health = MAXHEALTH;
    }

}
