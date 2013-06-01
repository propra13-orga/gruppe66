package de.propra13.assets.animations;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

public class AnimationManager {

    public static final String DEFAULT_ANIMATION = "default";

    private Map<String, Animation> animations = new HashMap<>();
    private String currentAnimation = DEFAULT_ANIMATION;

    public AnimationManager(Animation defaultAnimation) {
        animations.put(DEFAULT_ANIMATION, defaultAnimation);
    }

    public void addAnimation(String key, Animation animation) {
        if (key.equals(DEFAULT_ANIMATION))
            throw new IllegalArgumentException(
                    "You cannot replace the default animation");
        else if (animation != null)
            animations.put(key, animation);
    }

    public void removeAnimation(String key) {
        if (key.equals(DEFAULT_ANIMATION))
            throw new IllegalArgumentException(
                    "You cannot remove the default animation");
        animations.remove(key);
    }

    public void setCurrentAnimation(String key) {
        if (animations.containsKey(key)) {
            currentAnimation = key;
        } else
            throw new IllegalArgumentException("Wrong animation key");
    }

    public Animation getCurrentAnimation() {
        return animations.get(currentAnimation);
    }

    public BufferedImage getCurrentBluna() {
        return getCurrentAnimation().getCurrentBluna();
    }

    public Animation getDefaultAnimation() {
        return animations.get(DEFAULT_ANIMATION);
    }

    private Rectangle getBounds() {
        return getDefaultAnimation().getBlunaCrate().getBounds();
    }

    public Rectangle getDefaultBounds() {
        return new Rectangle(getBounds());
    }

    public Rectangle getDefaultBounds(int x, int y) {
        Rectangle blunaRect = getBounds();
        return new Rectangle(x + blunaRect.x, y + blunaRect.y, blunaRect.width,
                blunaRect.height);
    }
}
