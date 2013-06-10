package de.propra13.assets;

import java.util.HashMap;
import java.util.Map;

public class ThemeFactory {

    private static Map<String, Theme> themes = new HashMap<>();

    public static Theme getTheme(String themeName) {
        if (!themes.containsKey(themeName)) {
            themes.put(themeName, new Theme(themeName));
        }
        return themes.get(themeName);
    }

}
