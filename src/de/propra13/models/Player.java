package de.propra13.models;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.Timer;

public class Player extends BioAgressor implements ActionListener {

    public final static int MAXARMOR = 100;
    public final static int MAXMANA = 99;

    private int manaIncreaseDelay = 2;
    private int manaIncreaseRate = 7;

    private Timer manaTimer;

    private int lifes = 3;
    private double armor = MAXARMOR;

    private double mana = MAXMANA;

    private int money;

    private Weapon weapon;

    private ArrayList<Item> items = new ArrayList<Item>();

    public Player(Weapon defaultWeapon, int maxhealth) {
        super(maxhealth);

        manaTimer = new Timer(manaIncreaseDelay * 1000, this);
        manaTimer.start();

        pickUpItem(defaultWeapon);
    }

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
    public double getDamage() {
        return weapon.getDamage();
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
        return lifes > 0;
    }

    public int getLifes() {
        return lifes;
    }

    public void heal(Health health) {
        if (items.contains(health)) {
            items.remove(health);
        }
        heal(health.getHealth());
    }

    public void die() {
        lifes--;
        health = MAXHEALTH;
        armor = MAXARMOR;
        mana = MAXMANA;
    }

    public boolean pay(int price) {
        if (!canAfford(price)) {
            return false;
        }
        money -= price;
        return true;
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

    public Weapon getWeapon() {
        return weapon;
    }

    public void gainMoney(int amount) {
        money += amount;
    }

    public int getMoney() {
        return money;
    }

    public double getMana() {
        return mana;
    }

    public MagicFireball createFireball() {
        if (canConjure()) {
            mana -= MagicFireball.getManaCost();
            if (!manaTimer.isRunning())
                manaTimer.start();
            return new MagicFireball();
        } else {
            return null;
        }
    }

    private boolean canConjure() {
        return mana - MagicFireball.getManaCost() > 0;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        regainMana(manaIncreaseRate);
        if (mana == MAXMANA)
            manaTimer.stop();
    }

    public boolean canAfford(int price) {
        return money >= price;
    }

    public void regainManaFrom(Mana mana) {
        if (items.contains(mana)) {
            regainMana(mana.getMana());
            items.remove(mana);
        }
    }

    private void regainMana(double mana) {
        this.mana = Math.min(MAXMANA, this.mana + mana);
    }

    public void regainArmorFrom(Armor armor) {
        if (items.contains(armor)) {
            this.armor = Math.min(MAXARMOR, this.armor + armor.getArmor());
            items.remove(armor);
        }
    }
}
