package de.propra13.models;

public class Dragon extends Agressor {

    private int damage = 50;

    public Dragon(int maxhealth) {
        super(maxhealth);
        reloadTime = 5;
    }

    @Override
    public void sufferDamage(double damage) {
        health -= damage;
    }

    @Override
    public void inflictDamageOn(Agressor opponent) {
        if (!reloads) {
            opponent.sufferDamage(damage);
            reload();
        }
    }
}
