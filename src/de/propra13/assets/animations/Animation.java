package de.propra13.assets.animations;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.HashMap;

import de.propra13.assets.BlunaCrate;
import de.propra13.assets.BlunaCrateFactory;

public class Animation {

    private AnimationManager animationManager;
    private HashMap<String, BlunaCrate> blunaSet = new HashMap<>();

    private int currentFrame = 0;
    private int frames;
    private Rectangle bounds;
    private int spriteWidth;
    private int spriteHeight;
    private boolean directable;
    private AnimationPhaseListener listener;

    public Animation(String defaultAnimationType,
            HashMap<String, BufferedImage> imageSet, int directions,
            int frames, int shadowRGB) {
        for (String key : imageSet.keySet()) {
            blunaSet.put(key, BlunaCrateFactory.getBlunaCrate(
                    imageSet.get(key), directions, frames, shadowRGB));
        }
        directable = checkDirectable(directions);
        setDefaultAnimationType(defaultAnimationType);
    }

    public Animation(String defaultAnimationType,
            BufferedImage defaultAnimation,
            HashMap<String, BufferedImage> imageSet, int directions,
            int frames, Rectangle bounds) {
        for (String key : imageSet.keySet()) {
            blunaSet.put(key, BlunaCrateFactory.getBlunaCrate(
                    imageSet.get(key), directions, frames, bounds));
        }
        directable = checkDirectable(directions);
        setDefaultAnimationType(defaultAnimationType);
    }

    public Animation(BufferedImage bluna, int directions, int frames,
            int shadowRGB) {
        blunaSet.put("default", BlunaCrateFactory.getBlunaCrate(bluna,
                directions, frames, shadowRGB));
        directable = checkDirectable(directions);
        setDefaultAnimationType("default");
    }

    public Animation(BufferedImage bluna, int directions, int frames,
            Rectangle bounds) {
        blunaSet.put("default", BlunaCrateFactory.getBlunaCrate(bluna,
                directions, frames, bounds));
        directable = checkDirectable(directions);
        setDefaultAnimationType("default");
    }

    private boolean checkDirectable(int directions) {
        return directions == 8;
    }

    public void setDefaultAnimationType(String defaultAnimationName) {
        BlunaCrate defaultAnimation = blunaSet.get(defaultAnimationName);
        frames = defaultAnimation.getFrames();
        bounds = defaultAnimation.getBounds();
        spriteWidth = defaultAnimation.getSpriteWidth();
        spriteHeight = defaultAnimation.getSpriteHeight();
    }

    private void increaseFrame() {
        currentFrame++;
        if (currentFrame >= frames) {
            currentFrame = 0;
            if (null != listener)
                listener.didLoop();
        }
    }

    public BufferedImage getCurrentBluna() {
        if (directable && animationManager.getCurrentDirection().isMoving())
            return getBluna();
        else
            return getBluna(currentFrame);
    }

    public void animate() {
        increaseFrame();
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

    public void reset() {
        currentFrame = 0;
    }

    public void setListener(AnimationPhaseListener listener) {
        this.listener = listener;
    }

}
