package de.propra13.models;

public class Npc {

    private String currentMessage = "Hello";
    private boolean talking = false;

    public void setTalking(boolean talking) {
        this.talking = talking;
    }

    public boolean isTalking() {
        return talking;
    }

    public String getCurrentMessage() {
        return currentMessage;
    }

    public void setCurrentMessage(String currentMessage) {
        this.currentMessage = currentMessage;
    }
}
