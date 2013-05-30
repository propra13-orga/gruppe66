package de.propra13.views.objects;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.HashSet;

import de.propra13.models.Room;

public abstract class MoveableGameObject extends GameObject {

    protected int vx;
    protected int vy;

    protected Direction currentDirection;

    public MoveableGameObject(BufferedImage image, int x, int y,
            int directions, int frames) {
        super(image, x, y, directions, frames);

        currentDirection = new Direction(1, 0);
        currentBluna = blunaCrate.getBluna(currentDirection);
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
        if (!field.contains(new Rectangle(x, y, width, height))) {
            if (x < 1)
                collidedLeft(oldx);
            if (x + width >= field.width)
                collidedRight(oldx);
            if (y < 1)
                collidedTop(oldy);
            if (y + height >= field.height)
                collidedBottom(oldy);
        }
    }

    private void collideWithWalls(Room room, int oldx, int oldy) {
        for (WallObject wall : room.getWalls()) {
            if (wall.getBounds().intersects(this.getBounds())) {
                HashSet<Point> myPointMask = getPointMask();
                myPointMask.retainAll(wall.getPointMask());

                if (myPointMask.size() > 0) {
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
    }

    public void animate() {
        increaseFrame();
        if (vx != 0 || vy != 0) {
            currentDirection.setVx(vx);
            currentDirection.setVy(vy);
        }
        currentBluna = blunaCrate.getBluna(currentDirection, currentFrame);
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
        return x + width > o.getX() && x < o.getX() + o.getWidth();
    }

    protected boolean intersectsY(int y, GameObject o) {
        return y + height > o.getY() && y < o.getY() + o.getHeight();
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
