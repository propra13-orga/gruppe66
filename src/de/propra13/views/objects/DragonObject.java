package de.propra13.views.objects;

import java.awt.Dimension;
import java.awt.geom.Point2D;

import de.propra13.assets.Theme;
import de.propra13.assets.animations.Animation;
import de.propra13.models.Room;
import de.propra13.views.GameFieldView;

public class DragonObject extends EnemyObject {

    public DragonObject(int x, int y, Theme theme) {
        super(new Animation(theme.getDragonBluna(), 9, 9, 0x1f160d), x, y);
    }

    @Override
    public void move(Dimension gameFieldSize, Room room) {
        super.move(gameFieldSize, room);

        Point2D.Double p = room.getPlayerObject().getCenter();
        Direction d = new Direction((int) (p.x - getCenter().x),
                (int) (p.y - getCenter().y));

        if (p.distance(getCenter()) > GameFieldView.GRID * 2) {
            vx = d.getNormalizedVx();
            vy = d.getNormalizedVy();
        } else {
            vx = vy = 0;

            double diffx = p.x - getCenter().x, diffy = p.y - getCenter().y;
            Direction currentDirection = getAnimationManager()
                    .getCurrentAnimation().getDirection();
            Direction newDirection = new Direction((int) diffx, (int) diffy);

            if (!currentDirection.equals(newDirection))
                getAnimationManager().getCurrentAnimation().animate(
                        newDirection);
        }
    }

    @Override
    public void animate() {
        if (vx != 0 || vy != 0)
            super.animate();
    }

}