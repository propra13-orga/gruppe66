package de.propra13.views.objects;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.HashMap;

import de.propra13.assets.Theme;
import de.propra13.assets.animations.Animation;
import de.propra13.controllers.GameController;
import de.propra13.models.Item;
import de.propra13.models.Player;
import de.propra13.models.Trader;
import de.propra13.views.GameFieldView;

public class TraderObject extends NpcObject<Trader> {

    private GameController controller;
    private Font shopFont;

    private HashMap<Trader.ItemType, BufferedImage> images;

    public TraderObject(Trader trader, int x, int y, Theme theme,
            GameController controller) {
        super(trader, new Animation(theme.getTraderBlunas().get("stands")
                .get("default")), x, y, theme.getGameFont());

        this.controller = controller;
        this.shopFont = getMessageFont().deriveFont(14f);
        addAnimations("default", theme.getTraderBlunas());

        images = new HashMap<>(3);
        images.put(Trader.ItemType.HEALTH, theme.getShopHealthImage());
        images.put(Trader.ItemType.MANA, theme.getShopManaImage());
        images.put(Trader.ItemType.ARMOR, theme.getShopArmorImage());
        images.put(Trader.ItemType.SWORD, theme.getShopSwordImage());
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
            gfx.setPaint(new Color(253, 246, 227, 180));
            Rectangle ground = new Rectangle(100, 75, 600, 360);
            gfx.fillRoundRect(ground.x, ground.y, ground.width, ground.height,
                    25, 25);
            gfx.setPaint(new Color(105, 93, 105, 230));
            gfx.setStroke(new BasicStroke(10));
            gfx.drawRoundRect(ground.x, ground.y, ground.width, ground.height,
                    25, 25);

            drawInfo(gfx, ground.x + 30, ground.y + 30);
            drawItems(gfx, view, ground.x + 30, ground.y + 100);
        }
    }

    private void drawInfo(Graphics2D gfx, int x, int y) {
        gfx.setPaint(new Color(39, 33, 27));
        gfx.setFont(new Font(shopFont.getFontName(), Font.BOLD, shopFont
                .getSize()));
        gfx.drawString("Um zu kaufen, drücke die Zahl neben dem Bild.", x,
                y + 5);
        gfx.drawString(
                "Sofern du genügend Geld hast, erhälts du den Gegenstand.", x,
                y + 25);
        gfx.drawString("Drücke ENTER um den Shop zu verlassen!", x, y + 45);
    }

    private void drawItems(Graphics2D gfx, GameFieldView view, int x, int y) {
        gfx.setPaint(new Color(69, 63, 57));
        gfx.setFont(shopFont);
        for (int slot = 0; slot < getNpc().activeSlots(); slot++) {
            BufferedImage image = images.get(getNpc().getSlotType(slot));
            int slotX = x + (300 * (slot % 2));
            int slotY = y + (130 * (slot / 2));
            int width = 100, height = 100;
            gfx.drawImage(image, slotX, slotY, width, height, view);

            if (getNpc().hasItemsInSlot(slot)) {
                gfx.drawString(getNpc().getSlotName(slot) + " ("
                        + getNpc().countItemsInSlot(slot) + " übrig)", slotX
                        + width + 10, slotY + 20);
                gfx.drawString("Drücke " + (slot + 1) + "("
                        + getNpc().getSlotPrice(slot) + " Gold)", slotX + width
                        + 10, slotY + 35);
            } else {
                gfx.drawString(getNpc().getSlotName(slot) + " (ausverkauft)",
                        slotX + width + 10, slotY + 20);
            }
        }
    }
}
