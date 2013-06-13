package de.propra13.models;

public class MagicFireball extends Agressor {

    public static final double DEFAULTPOWER = 50;

    private double power;

    public MagicFireball(double power) {
        this.power = power;
    }

    public MagicFireball() {
        this(DEFAULTPOWER);
    }

    @Override
    public void inflictDamageOn(BioAgressor enemy) {
        if (!reloads) {
            enemy.sufferDamage(power);
            reload();
        }
    }

}
