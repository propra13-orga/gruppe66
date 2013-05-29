package de.propra13.views.objects;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.Timer;

import de.propra13.assets.Bluna;
import de.propra13.models.Room;

public abstract class MoveableGameObject extends GameObject {

    protected int vx;
    protected int vy;

    private Bluna bluna;
    private int frames;
    private int currentFrame = -1;

    public MoveableGameObject(BufferedImage image, int x, int y,
            int directions, int frames) {
        super(x, y, image.getWidth(null) / frames, image.getHeight(null)
                / directions);
        this.frames = frames;
        bluna = new Bluna(image, directions, frames);

        this.image = bluna.getBluna(1, 0, 0);

        startAnimator(frames);
    }

    private void startAnimator(int frames) {
        Timer t = new Timer(100, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                animate();
            }
        });
        t.start();
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

    public void animate() {
        currentFrame = ++currentFrame % frames;
        image = bluna.getBluna(vx, vy, currentFrame);
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
