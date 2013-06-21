package de.propra13.models;

public class Armor extends Item {

    private double armor;

    public Armor(double armor) {
        this.armor = armor;
    }

    @Override
    public void useOn(Player player) {
        player.regainArmorFrom(this);
    }

    public double getArmor() {
        return armor;
    }

}
