package de.propra13.views.objects;

import java.awt.Dimension;
import java.awt.Rectangle;

import de.propra13.assets.animations.Animation;
import de.propra13.models.Room;

public abstract class MoveableGameObject extends GameObject {

    protected int vx;
    protected int vy;

    public MoveableGameObject(Animation defaultAnimation, int x, int y) {
        super(defaultAnimation, x, y);
    }

    public void moveTo(GameObject ob) {
        this.x = ob.getX() + (this.x - getX());
        this.y = ob.getY() + (this.y - getY());
    }

    public void act(Dimension gameFieldSize, Room room) {
        int oldx = x, oldy = y;
        Rectangle field = new Rectangle(gameFieldSize);

        if (canAct())
            move(gameFieldSize, room);

        collideWithGameField(field, oldx, oldy);
        collideWithWalls(room, oldx, oldy);

    }

    public void move(Dimension gameFieldSize, Room room) {
        x += vx;
        y += vy;
    }

    private void collideWithGameField(Rectangle field, int oldx, int oldy) {
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

    private void collideWithWalls(Room room, int oldx, int oldy) {
        for (WallObject wall : room.getWalls()) {
            if (wall.getBounds().intersects(this.getBounds())) {

                if (vx < 0 && intersectsY(oldy, wall))
                    collidedLeft(oldx);
                else if (vx > 0 && intersectsY(oldy, wall))
                    collidedRight(oldx);

                if (vy < 0 && intersectsX(oldx, wall))
                    collidedTop(oldy);
                else if (vy > 0 && intersectsX(oldx, wall))
                    collidedBottom(oldy);

            }
        }
    }

    protected void collidedLeft(int oldx) {
        x = oldx;
    }

    protected void collidedRight(int oldx) {
        x = oldx;
    }

    protected void collidedTop(int oldy) {
        y = oldy;
    }

    protected void collidedBottom(int oldy) {
        y = oldy;
    }

    protected boolean intersectsX(int x, GameObject o) {
        int scaledX = (x + getDefaultBounds().x);
        return scaledX + getWidth() > o.getX()
                && scaledX < o.getX() + o.getWidth();
    }

    protected boolean intersectsY(int y, GameObject o) {
        int scaledY = (y + getDefaultBounds().y);
        return scaledY + getHeight() > o.getY()
                && scaledY < o.getY() + o.getHeight();
    }

    @Override
    public void animate() {
        if (isMoving())
            setDirection(new Direction(vx, vy));
        super.animate();
    }

    public int getVx() {
        return vx;
    }

    public void setVx(int vx) {
        this.vx = vx;
    }

    public int getVy() {
        return vy;
    }

    public void setVy(int vy) {
        this.vy = vy;
    }

    protected boolean isMoving() {
        return vx != 0 || vy != 0;
    }

    public Direction getDirection() {
        return new Direction(vx, vy);
    }

}
