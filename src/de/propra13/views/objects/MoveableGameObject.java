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
        this.x = ob.getX();
        this.y = ob.getY();
    }

    public void move(Dimension gameFieldSize, Room room) {
        int oldx = x, oldy = y;
        Rectangle field = new Rectangle(gameFieldSize);

        x += vx;
        y += vy;

        collideWithGameField(field, oldx, oldy);
        collideWithWalls(room, oldx, oldy);
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
        int scaledX = (x + animationManager.getDefaultBounds().x);
        return scaledX + getWidth() > o.getX()
                && scaledX < o.getX() + o.getWidth();
    }

    protected boolean intersectsY(int y, GameObject o) {
        int scaledY = (y + animationManager.getDefaultBounds().y);
        return scaledY + getHeight() > o.getY()
                && scaledY < o.getY() + o.getHeight();
    }

    @Override
    public void animate() {
        getAnimationManager().getCurrentAnimation().animate(
                new Direction(vx, vy));
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

}
