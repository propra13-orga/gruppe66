package de.propra13.models;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Trader extends Npc {

    class Slot {
        int price;
        Queue<Item> items = new LinkedList<>();
    }

    private List<Slot> slots = new ArrayList<>();

    private boolean shopOpen = false;

    public Trader() throws IOException {
        super("trader");

        for (int i = 0; i < 9; i++) {
            Slot slot = new Slot();
            slot.items.add(new Herb(100));
            slot.items.add(new Herb(100));
            slot.items.add(new Herb(100));
            slot.price = 40;

            slots.add(slot);
        }
    }

    public Item getItemInSlot(int slot) {
        return slots.get(slot).items.poll();
    }

    public int getSlotPrice(int slot) {
        return slots.get(slot).price;
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
