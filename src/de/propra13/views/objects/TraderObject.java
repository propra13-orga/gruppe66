package de.propra13.views.objects;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import de.propra13.assets.Theme;
import de.propra13.assets.animations.Animation;
import de.propra13.controllers.GameController;
import de.propra13.models.Item;
import de.propra13.models.Player;
import de.propra13.models.Trader;
import de.propra13.views.GameFieldView;

public class TraderObject extends NpcObject<Trader> {

    private GameController controller;
    private Theme theme;
    private Font shopFont = new Font("Verdana", Font.BOLD, 12);

    public TraderObject(Trader trader, int x, int y, Theme theme,
            GameController controller) {
        super(trader, new Animation(theme.getTraderBlunas().get("stands")
                .get("default")), x, y);

        this.theme = theme;
        this.controller = controller;
        addAnimations("default", theme.getTraderBlunas());
    }

    @Override
    public void keyPressed(KeyEvent event, boolean paused) {
        super.keyPressed(event, paused);
        switch (event.getKeyCode()) {
        case KeyEvent.VK_ENTER:
            if (isNearPlayer()) {
                if (getNpc().shopIsClosed()) {
                    controller.pause();
                    getNpc().openShop();
                } else {
                    controller.remain();
                    getNpc().closeShop();
                }
            }
            break;
        }

        if (getNpc().shopIsOpen()) {
            if (event.getKeyCode() >= KeyEvent.VK_1
                    && event.getKeyCode() <= KeyEvent.VK_9) {
                trade(event.getKeyCode() - KeyEvent.VK_1);
            }
        }
    }

    private void trade(int slot) {
        Player player = getNearPlayer().getPlayer();
        Item item = getNpc().getItemInSlot(slot);
        if (item != null) {
            if (player.pay(getNpc().getSlotPrice(slot))) {
                player.pickUpItem(item);
            } else {
                getNpc().putItemBackTo(slot, item);
            }
        }
    }

    @Override
    public void drawText(Graphics2D gfx, GameFieldView view) {
        if (getNpc().shopIsClosed()) {
            super.drawText(gfx, view);
        } else {
            gfx.setPaint(new Color(255, 243, 215, 180));
            gfx.setStroke(new BasicStroke(2));
            gfx.fillRoundRect(100, 75, 600, 400, 25, 25);

            gfx.drawImage(theme.getShopHealthImage(), 120, 150, 100, 100, null);
            gfx.drawImage(theme.getShopManaImage(), 420, 150, 100, 100, null);

            gfx.setPaint(new Color(69, 63, 57));
            gfx.setFont(shopFont);

            if (getNpc().hasItemsInSlot(0)) {
                gfx.drawString("Health (" + getNpc().countItemsInSlot(0)
                        + " übrig)", 235, 170);
                gfx.drawString("(Drücke 1)   " + getNpc().getSlotPrice(0)
                        + " Gold", 235, 190);
            } else {
                gfx.drawString("Health (ausverkauft)", 235, 170);
            }

            if (getNpc().hasItemsInSlot(1)) {
                gfx.drawString("Mana (" + getNpc().countItemsInSlot(1)
                        + " übrig)", 535, 170);
                gfx.drawString("(Drücke 2)   " + getNpc().getSlotPrice(1)
                        + " Gold", 535, 190);
            } else {
                gfx.drawString("Mana (ausverkauft)", 535, 170);
            }

            gfx.drawString("Um zu kaufen, drücke die Zahl neben dem Bild.",
                    120, 340);
            gfx.drawString(
                    "Sofern du genügend Geld hast, erhälts du den Gegenstand.",
                    120, 360);
            gfx.drawString("Drücke ENTER um den Shop zu verlassen!", 120, 380);
        }
    }
}
