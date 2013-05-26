package de.propra13.views.objects;

import de.propra13.models.Theme;

public class StartObject extends GameObject {

    public StartObject(int x, int y, Theme theme) {
        super(theme.getStartImage(), x, y);
    }

}
