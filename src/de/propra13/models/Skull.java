package de.propra13.models;

public class Skull extends Agressor {

    private int damage = 35;

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    @Override
    public void inflictDamageOn(Agressor opponent) {
        if (!reloads) {
            opponent.sufferDamage(damage);
            reload();
        }
    }

    @Override
    public void sufferDamage(double damage) {
    }

}
