package de.propra13.models;

import java.util.ArrayList;

public class Player {

    private final int MAXHEALTH = 100;

    private double health = MAXHEALTH;
    private int lives = 3;

    private Weapon weapon;
    private ArrayList<Item> items = new ArrayList<Item>();

    public void inflictDamage(int damage) {
        health -= damage;
    }

    public double getHealth() {
        return health;
    }

    public void setHealth(double health) {
        this.health = Math.min(MAXHEALTH, health);
    }

    public void addHealth(double health) {
        setHealth(this.health + health);
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

    public void pickUpItem(Item item) {
        this.items.add(item);
        item.useOn(this);
    }

    public void equip(Weapon weapon) {
        if (items.contains(weapon)) {
            items.remove(weapon);
        }
        this.weapon = weapon;
    }

    public ArrayList<Item> getItems() {
        return items;
    }

}
