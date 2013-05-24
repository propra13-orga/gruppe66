package de.propra13.views.objects;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Rectangle;

import de.propra13.models.Room;

public class MoveableGameObject extends GameObject {

    protected int vx;
    protected int vy;

    public MoveableGameObject(Image image) {
        super(image);
    }

    public MoveableGameObject(Image image, int x, int y) {
        super(image, x, y);
    }

    public void moveTo(int x, int y) {
        this.x = scale(x);
        this.y = scale(y);
    }

    public void move(Dimension gameFieldSize, Room room) {
        int oldx = x, oldy = y;
        Rectangle field = new Rectangle(gameFieldSize);

        x += vx;
        y += vy;

        if (!field.contains(new Rectangle(x, y, width, height))) {
            if (x < 1)
                collidedLeft(oldx);
            if (x + width >= gameFieldSize.width)
                collidedRight(oldx);
            if (y < 1)
                collidedTop(oldy);
            if (y + height >= gameFieldSize.height)
                collidedBottom(oldy);
        }

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
