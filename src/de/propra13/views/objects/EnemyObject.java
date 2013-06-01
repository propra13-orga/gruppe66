package de.propra13.views.objects;

import de.propra13.assets.animations.Animation;

public abstract class EnemyObject extends AgressorObject {

    public EnemyObject(Animation defaultAnimation, int x, int y) {
        super(defaultAnimation, x, y);
    }
}
