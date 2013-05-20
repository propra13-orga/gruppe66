package de.github.propra13.objects;

import java.awt.Dimension;
import java.awt.Rectangle;

import de.github.propra13.models.Room;

public class MoveableGameObject extends GameObject {

    protected int vx;
    protected int vy;

    public MoveableGameObject(String imageName) {
        super(imageName);
    }

    public MoveableGameObject(String imageName, int x, int y) {
        super(imageName, x, y);
    }

    public void moveTo(int x, int y) {
        this.x = scale(x);
        this.y = scale(y);
    }

    public void move(Dimension size, Room room) {
        int oldx = x, oldy = y;
        Rectangle field = new Rectangle(size);

        x += vx;
        y += vy;

        if (!field.contains(new Rectangle(x, y, width, height))) {
            if (x < 1 || x + width >= size.width)
                x = oldx;
            if (y < 1 || y + height >= size.height)
                y = oldy;
        }

        for (WallObject wall : room.getWalls()) {
            if (wall.getBounds().intersects(this.getBounds())) {
                if (vx != 0 && intersectsY(oldy, wall)) {
                    x = oldx;
                }

                if (vy != 0 && intersectsX(oldx, wall)) {
                    y = oldy;
                }
            }
        }
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
