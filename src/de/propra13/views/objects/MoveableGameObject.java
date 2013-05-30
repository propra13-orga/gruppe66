package de.propra13.views.objects;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import de.propra13.models.Room;

public abstract class MoveableGameObject extends GameObject {

    protected int vx;
    protected int vy;

    protected Direction currentDirection;

    public MoveableGameObject(BufferedImage image, int x, int y,
            int directions, int frames) {
        super(image, x, y, directions, frames);

        currentDirection = new Direction(1, 0);
        currentBluna = getBluna(currentDirection);
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

    public void animate() {
        increaseFrame();
        if (vx != 0 || vy != 0) {
            currentDirection.setVx(vx);
            currentDirection.setVy(vy);
        }

        BufferedImage bluna = getBluna(currentDirection, currentFrame);
        currentBluna = bluna;
    }

    private int abstractNumber(int x) {
        if (x == 0)
            return 0;
        return Math.abs(x) / x;
    }

    private BufferedImage getBluna(Direction direction, int frameIndex) {
        int vx3 = abstractNumber(direction.getVx()) + 1;
        int vy3 = abstractNumber(direction.getVy()) + 1;
        int v3 = 3 * vx3 + vy3;
        if (v3 > 4)
            v3--;
        return blunaCrate.getBluna(v3 * frames + frameIndex);
    }

    private BufferedImage getBluna(Direction direction) {
        return getBluna(direction, 0);
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
        Rectangle objectBounds = o.getBounds();
        return objectBounds.intersects(getBoundsFromOldX(x));
    }

    protected boolean intersectsY(int y, GameObject o) {
        Rectangle objectBounds = o.getBounds();
        return objectBounds.intersects(getBoundsFromOldY(y));
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
