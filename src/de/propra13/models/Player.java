package de.propra13.models;

import java.util.ArrayList;

public class Player extends Agressor {

    public Player(int maxhealth) {
        super(maxhealth);
    }

    private int lives = 3;

    private Weapon weapon;
    private int baseDamage = 5;
    private ArrayList<Item> items = new ArrayList<Item>();

    @Override
    public void sufferDamage(double damage) {
        health -= damage;
    }

    @Override
    public void inflictDamageOn(Agressor opponent) {
        opponent.sufferDamage(getDamage());
    }

    private double getDamage() {
        if (null != weapon)
            return weapon.getDamage();

        return baseDamage;
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

    public String getWeaponType() {
        if (weapon != null) {
            return Weapon.SWORD;
        } else {
            return "";
        }
    }

}
