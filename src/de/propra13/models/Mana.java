package de.propra13.models;

public class Mana extends Item {

    private double mana;

    public Mana(double power) {
        this.mana = power;
    }

    @Override
    public void useOn(Player player) {
        player.regainManaFrom(this);
    }

    public double getMana() {
        return mana;
    }
}
