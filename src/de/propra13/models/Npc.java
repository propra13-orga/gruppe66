package de.propra13.models;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Npc extends Model {

    public static List<String> parseDialogue(String fileName)
            throws IOException {
        List<String> chapters = new ArrayList<>();
        Collections.addAll(chapters,
                readStringFromFile("models/dialogues/" + fileName + ".kdg")
                        .split("\\s+-\\s+"));
        return chapters;
    }

    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean hasMessage() {
        return message != null;
    }
}
