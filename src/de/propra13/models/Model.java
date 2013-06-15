package de.propra13.models;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Model {

    protected static String readStringFromFile(String fileName)
            throws IOException {
        return new String(Files.readAllBytes(Paths.get("res/" + fileName)));
    }

    protected static List<String> readLinesFromFile(String fileName)
            throws IOException {
        return Files.readAllLines(Paths.get("res/" + fileName),
                Charset.defaultCharset());
    }
}
