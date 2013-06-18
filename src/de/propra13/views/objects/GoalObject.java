package de.propra13.views.objects;

import de.propra13.assets.Theme;
import de.propra13.assets.animations.Animation;

public class GoalObject extends GameObject {

    public GoalObject(int x, int y, Theme theme) {
        super(new Animation(theme.getGoalBluna()), x, y);
    }

}
