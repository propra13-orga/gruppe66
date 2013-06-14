package de.propra13.models;

public class MagicFireball extends Agressor {

    public static final double DEFAULTPOWER = 50;

    private double power;

    private long birthTime;
    private int lifeSpan = 3;

    public MagicFireball(double power) {
        this.power = power;

        birthTime = System.currentTimeMillis();
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

    public boolean isAlive() {
        return System.currentTimeMillis() - birthTime < lifeSpan * 1000;
    }

}
