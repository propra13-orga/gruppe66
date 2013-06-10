package de.propra13.models;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Model {

    protected static String readStringFromFile(String fileName)
            throws IOException {
        FileReader file = new FileReader("res/" + fileName);
        BufferedReader reader = new BufferedReader(file);

        String line = reader.readLine();

        StringBuffer buffer = new StringBuffer();
        while (line != null) {
            buffer.append(line + "\n");
            line = reader.readLine();
        }
        reader.close();

        return buffer.toString();
    }

}
