package de.propra13.assets.animations;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.HashMap;

import de.propra13.assets.BlunaCrate;

public class Animation {

    private BufferedImage currentBluna;
    private AnimationManager animationManager;

    private HashMap<String, BlunaCrate> blunaSet;

    private int currentFrame = -1;
    private final int frames;
    private final Rectangle bounds;
    private final int spriteWidth;
    private final int spriteHeight;

    public Animation(BufferedImage defaultAnimation,
            HashMap<String, BufferedImage> imageSet, int directions,
            int frames, int shadowRGB) {
        this(defaultAnimation, directions, frames, shadowRGB);

        for (String key : imageSet.keySet()) {
            blunaSet.put(key, new BlunaCrate(imageSet.get(key), directions,
                    frames, shadowRGB));
        }

    }

    public Animation(BufferedImage defaultAnimation, int directions,
            int frames, int shadowRGB) {
        this(new BlunaCrate(defaultAnimation, directions, frames, shadowRGB));
    }

    public Animation(BlunaCrate defaultAnimation) {
        frames = defaultAnimation.getFrames();
        bounds = defaultAnimation.getBounds();
        spriteWidth = defaultAnimation.getSpriteWidth();
        spriteHeight = defaultAnimation.getSpriteHeight();

        currentBluna = defaultAnimation.getFirstBluna();
        blunaSet = new HashMap<>();
        blunaSet.put(AnimationManager.DEFAULT_ANIMATION_TYPE, defaultAnimation);
    }

    private void increaseFrame() {
        currentFrame = ++currentFrame % frames;
    }

    public BufferedImage getCurrentBluna() {
        return currentBluna;
    }

    public void animate() {
        increaseFrame();
        if (animationManager.getCurrentDirection().isMoving())
            currentBluna = getBluna();
        else
            currentBluna = getBluna(currentFrame);
    }

    public BufferedImage getBluna() {
        int vx3 = animationManager.getCurrentDirection().getNormalizedVx() + 1;
        int vy3 = animationManager.getCurrentDirection().getNormalizedVy() + 1;
        int v3 = 3 * vx3 + vy3;
        if (v3 > 4)
            v3--;
        return getBluna(v3 * frames + currentFrame);
    }

    public BufferedImage getBluna(int frame) {
        return blunaSet.get(animationManager.getCurrentAnimationType())
                .getBluna(frame);
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

    public Rectangle getBounds() {
        return bounds;
    }

    public int getSpriteWidth() {
        return spriteWidth;
    }

    public int getSpriteHeight() {
        return spriteHeight;
    }
}
