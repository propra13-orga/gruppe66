package de.propra13.assets.animations;

import java.awt.image.BufferedImage;

import de.propra13.assets.BlunaCrate;
import de.propra13.views.objects.Direction;

public class Animation {

    private BlunaCrate blunaCrate;
    private BufferedImage currentBluna;
    private Direction currentDirection = new Direction(1, 0);

    private int currentFrame = -1;

    public Animation(BufferedImage image, int directions, int frames,
            int shadowRGB) {
        this(new BlunaCrate(image, directions, frames, shadowRGB));
    }

    public Animation(BlunaCrate blunaCrate) {
        this.blunaCrate = blunaCrate;
        currentBluna = blunaCrate.getFirstBluna();
    }

    private void increaseFrame() {
        currentFrame = ++currentFrame % blunaCrate.getFrames();
    }

    public BlunaCrate getBlunaCrate() {
        return blunaCrate;
    }

    public BufferedImage getCurrentBluna() {
        return currentBluna;
    }

    public void animate() {
        increaseFrame();
        currentBluna = blunaCrate.getBluna(currentFrame);
    }

    public void animate(Direction direction) {
        increaseFrame();
        if (direction.isMoving())
            currentDirection.setFrom(direction);
        currentBluna = getBluna();
    }

    public BufferedImage getBluna() {
        int vx3 = currentDirection.getNormalizedVx() + 1;
        int vy3 = currentDirection.getNormalizedVy() + 1;
        int v3 = 3 * vx3 + vy3;
        if (v3 > 4)
            v3--;
        return blunaCrate.getBluna(v3 * blunaCrate.getFrames() + currentFrame);
    }

    public int getFrameNumber() {
        return currentFrame;
    }

    public void setDirection(Direction direction) {
        currentDirection = direction;
    }

    public Direction getDirection() {
        return currentDirection;
    }
}
