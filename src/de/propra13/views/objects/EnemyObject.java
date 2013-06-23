package de.propra13.views.objects;

import de.propra13.assets.animations.Animation;
import de.propra13.models.BioAgressor;

public abstract class EnemyObject<E extends BioAgressor> extends
        BioAgressorObject<E> {

    public EnemyObject(E enemy, Animation defaultAnimation, int x, int y) {
        super(enemy, defaultAnimation, x, y);
    }
}
