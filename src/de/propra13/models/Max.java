package de.propra13.models;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

public class Max extends Npc {

    private List<String> monologue;
    private Iterator<String> monologueIterator;

    private boolean isTalking() {
        return monologueIterator != null;
    }

    public void reset() {
        setMessage(null);
        monologueIterator = null;
    }

    public void startTalking() {
        if (!isTalking()) {
            monologueIterator = monologue.iterator();
            advanceMessage();
        }
    }

    public void advanceMessage() {
        if (isTalking() && monologueIterator.hasNext()) {
            setMessage(monologueIterator.next());
        }
    }

    public Max() throws IOException {
        monologue = parseDialogue("max");
    }

    public List<String> getMonologue() {
        return monologue;
    }
}
