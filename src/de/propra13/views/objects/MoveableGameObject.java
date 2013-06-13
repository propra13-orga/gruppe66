package de.propra13.views.objects;

import java.awt.Dimension;
import java.awt.Rectangle;

import de.propra13.assets.animations.Animation;
import de.propra13.models.Room;

public abstract class MoveableGameObject extends GameObject {

    protected Direction direction;
    protected double velocity = 1;

    public MoveableGameObject(Animation defaultAnimation, int x, int y) {
        super(defaultAnimation, x, y);
    }

    public void moveTo(GameObject ob) {
        this.x = ob.getX() + (this.x - getX());
        this.y = ob.getY() + (this.y - getY());
    }

    public void act(Dimension gameFieldSize, Room room) {
        double oldx = x, oldy = y;
        Rectangle field = new Rectangle(gameFieldSize);

        if (canAct())
            move(gameFieldSize, room);

        collideWithGameField(field, oldx, oldy);
        collideWithWalls(room, oldx, oldy);

    }

    public void move(Dimension gameFieldSize, Room room) {
        Direction.VelocityVector velocityVector = direction
                .getVelocityVector(velocity);
        x += velocityVector.vx;
        y += velocityVector.vy;
    }

    private void collideWithGameField(Rectangle field, double oldx, double oldy) {
        if (!field.contains(getBounds())) {
            if (getX() < 1)
                collidedLeft(oldx);
            if (getX() + getWidth() >= field.width)
                collidedRight(oldx);
            if (getY() < 1)
                collidedTop(oldy);
            if (getY() + getHeight() >= field.height)
                collidedBottom(oldy);
        }
    }

    private void collideWithWalls(Room room, double oldx, double oldy) {
        Direction.VelocityVector velocityVector = direction
                .getVelocityVector(velocity);
        for (WallObject wall : room.getWalls()) {
            if (wall.getBounds().intersects(this.getBounds())) {

                if (velocityVector.vx < 0 && intersectsY(oldy, wall))
                    collidedLeft(oldx);
                else if (velocityVector.vx > 0 && intersectsY(oldy, wall))
                    collidedRight(oldx);

                if (velocityVector.vy < 0 && intersectsX(oldx, wall))
                    collidedTop(oldy);
                else if (velocityVector.vy > 0 && intersectsX(oldx, wall))
                    collidedBottom(oldy);

            }
        }
    }

    protected void collidedLeft(double oldx) {
        x = oldx;
    }

    protected void collidedRight(double oldx) {
        x = oldx;
    }

    protected void collidedTop(double oldy) {
        y = oldy;
    }

    protected void collidedBottom(double oldy) {
        y = oldy;
    }

    protected boolean intersectsX(double x, GameObject o) {
        double scaledX = (x + getDefaultBounds().x);
        return scaledX + getWidth() > o.getX()
                && scaledX < o.getX() + o.getWidth();
    }

    protected boolean intersectsY(double y, GameObject o) {
        double scaledY = (y + getDefaultBounds().y);
        return scaledY + getHeight() > o.getY()
                && scaledY < o.getY() + o.getHeight();
    }

    @Override
    public void animate() {
        if (isMoving())
            setAnimationDirection(new Direction(direction));
        super.animate();
    }

    public double getVx() {
        return direction.getVelocityVector(velocity).vx;
    }

    public double getVy() {
        return direction.getVelocityVector(velocity).vy;
    }

    protected boolean isMoving() {
        return direction.isMoving();
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

}
