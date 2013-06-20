package de.propra13.models;

public abstract class BioAgressor extends Agressor {

    public final double MAXHEALTH;

    protected double health;

    public BioAgressor() {
        MAXHEALTH = Double.POSITIVE_INFINITY;
    }

    public BioAgressor(int maxhealth) {
        MAXHEALTH = health = maxhealth;
    }

    public abstract void sufferDamage(double damage);

    public boolean isWounded() {
        return health < MAXHEALTH;
    }

    public boolean isDead() {
        return health <= 0;
    }

    public double getHealth() {
        return health;
    }

    public void heal(int health) {
        this.health = Math.min(this.health + health, MAXHEALTH);
    }
}
