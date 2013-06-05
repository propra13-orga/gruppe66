package de.propra13.models;

public class Money extends Item {

    private int amount;

    public Money(int amount) {
        this.amount = amount;
    }

    @Override
    public void useOn(Player player) {
        player.gainMoney(amount);
    }

}
