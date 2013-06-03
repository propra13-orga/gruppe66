package de.propra13.assets.animations;

import java.awt.image.BufferedImage;

import de.propra13.assets.BlunaCrate;

public class Animation {

    private BlunaCrate blunaCrate;
    private BufferedImage currentBluna;
    private AnimationManager animationManager;

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
        if (animationManager.getCurrentDirection().isMoving())
            currentBluna = getBluna();
        else
            currentBluna = blunaCrate.getBluna(currentFrame);
    }

    public BufferedImage getBluna() {
        int vx3 = animationManager.getCurrentDirection().getNormalizedVx() + 1;
        int vy3 = animationManager.getCurrentDirection().getNormalizedVy() + 1;
        int v3 = 3 * vx3 + vy3;
        if (v3 > 4)
            v3--;
        return blunaCrate.getBluna(v3 * blunaCrate.getFrames() + currentFrame);
    }

    public int getFrameNumber() {
        return currentFrame;
    }

    public void setAnimationManager(AnimationManager animationManager) {
        this.animationManager = animationManager;
    }

    public AnimationManager getAnimationManager() {
        return animationManager;
    }
}
