package de.propra13.models;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class Npc extends Model {

    public static final String PATTERN = "\\s+#{3,}\\s+";

    private List<String> monologue;
    private Iterator<String> monologueIterator;
    private String message;

    public static List<String> parseDialogue(String fileName)
            throws IOException {
        List<String> chapters = new ArrayList<>();
        Collections.addAll(chapters,
                readStringFromFile("models/dialogues/" + fileName + ".kdg")
                        .split(PATTERN));
        return chapters;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean hasMessage() {
        return message != null;
    }

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

    public Npc(String monologueName) throws IOException {
        monologue = parseDialogue(monologueName);
    }

    public List<String> getMonologue() {
        return monologue;
    }
}
