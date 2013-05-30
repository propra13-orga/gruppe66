package de.propra13.views.objects;

import de.propra13.models.Theme;

public class HerbObject extends ItemObject {


    public HerbObject(int x, int y, Theme theme) {
        super(theme.getHerbImage(), x, y, 1, 8);
    }
}
