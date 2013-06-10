package de.propra13.models;

import java.util.ArrayList;

public class Player extends Agressor {

    public final static int MAXARMOR = 100;

    public Player(int maxhealth) {
        super(maxhealth);
    }

    private int lifes = 3;
    private double armor = MAXARMOR;

    private int money;

    private Weapon weapon;
    private int baseDamage = 5;
    private ArrayList<Item> items = new ArrayList<Item>();

    @Override
    public void sufferDamage(double damage) {
        if (armor <= 0) {
            health -= damage;
        } else {
            armor -= 2 * damage / 3;
            health -= damage / 3;
        }
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

    @Override
    public double getHealth() {
        return health;
    }

    public void setHealth(double health) {
        this.health = Math.min(MAXHEALTH, health);
    }

    public void addHealth(double health) {
        setHealth(this.health + health);
    }

    public boolean hasLifes() {
        return lifes > 1;
    }

    public int getLifes() {
        return lifes;
    }

    public void die() {
        lifes--;
        health = MAXHEALTH;
        armor = MAXARMOR;
    }

    public void pickUpItem(Item item) {
        this.items.add(item);
        item.useOn(this);
    }

    public double getArmor() {
        return armor;
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

    public void gainMoney(int amount) {
        money += amount;
    }

    public int getMoney() {
        return money;
    }

}
