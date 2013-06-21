package de.propra13.models;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Trader extends Npc {

    public enum ItemType {
        ARMOR, MANA, HEALTH
    }

    class Slot {
        String name;
        int price;
        ItemType type;
        Queue<Item> items = new LinkedList<>();

        public Slot(String name, int price, ItemType type) {
            this.name = name;
            this.price = price;
            this.type = type;
        }
    }

    private List<Slot> slots = new ArrayList<>();

    private boolean shopOpen = false;

    public Trader() throws IOException {
        super("trader");
        Slot healthPotions = new Slot("Heiltrank", 40, ItemType.HEALTH);
        healthPotions.items.add(new Health(100));
        healthPotions.items.add(new Health(100));
        healthPotions.items.add(new Health(100));

        Slot manaPotions = new Slot("Manatrank", 60, ItemType.MANA);
        manaPotions.items.add(new Mana(33));
        manaPotions.items.add(new Mana(33));
        manaPotions.items.add(new Mana(33));
        manaPotions.items.add(new Mana(33));
        manaPotions.items.add(new Mana(33));
        manaPotions.items.add(new Mana(33));
        manaPotions.items.add(new Mana(33));

        Slot armors = new Slot("Rüstung", 80, ItemType.ARMOR);
        armors.items.add(new Armor(20));
        armors.items.add(new Armor(20));
        armors.items.add(new Armor(20));

        slots.add(healthPotions);
        slots.add(manaPotions);
        slots.add(armors);
    }

    public Item getItemInSlot(int slot) {
        return slots.get(slot).items.poll();
    }

    public ItemType getSlotType(int slot) {
        return slots.get(slot).type;
    }

    public String getSlotName(int slot) {
        return slots.get(slot).name;
    }

    public int getSlotPrice(int slot) {
        return slots.get(slot).price;
    }

    public int activeSlots() {
        return slots.size();
    }

    public boolean shopIsClosed() {
        return !shopOpen;
    }

    public boolean shopIsOpen() {
        return shopOpen;
    }

    public void openShop() {
        shopOpen = true;
    }

    public void closeShop() {
        shopOpen = false;
    }

    public int countItemsInSlot(int slot) {
        return slots.get(slot).items.size();
    }

    public boolean hasItemsInSlot(int slot) {
        return !slots.get(slot).items.isEmpty();
    }

    public void putItemBackTo(int slot, Item item) {
        slots.get(slot).items.offer(item);
    }
}
