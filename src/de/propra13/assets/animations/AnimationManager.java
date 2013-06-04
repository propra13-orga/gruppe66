package de.propra13.assets.animations;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

import de.propra13.views.objects.Direction;

public class AnimationManager {

    public static final String DEFAULT_ANIMATION = "default";
    public static final String DEFAULT_ANIMATION_TYPE = "default";

    private Map<String, Animation> animations = new HashMap<>();
    private String currentAnimation = DEFAULT_ANIMATION;
    private String currentAnimationType = DEFAULT_ANIMATION_TYPE;

    private Direction currentDirection = new Direction(0, 0);

    public AnimationManager(Animation defaultAnimation) {
        defaultAnimation.setAnimationManager(this);
        animations.put(DEFAULT_ANIMATION, defaultAnimation);
    }

    public void setCurrentAnimationType(String currentAnimationType) {
        this.currentAnimationType = currentAnimationType;
    }

    public String getCurrentAnimationType() {
        return currentAnimationType;
    }

    public void addAnimation(String key, Animation animation) {
        if (key.equals(DEFAULT_ANIMATION))
            throw new IllegalArgumentException(
                    "You cannot replace the default animation");
        else if (animation != null) {
            animation.setAnimationManager(this);
            animations.put(key, animation);
        }
    }

    public void removeAnimation(String key) {
        if (key.equals(DEFAULT_ANIMATION))
            throw new IllegalArgumentException(
                    "You cannot remove the default animation");
        animations.remove(key);
    }

    public void setCurrentAnimation(String key) {
        if (!key.equals(currentAnimation)) {
            if (animations.containsKey(key)) {
                currentAnimation = key;
            } else
                throw new IllegalArgumentException("Wrong animation key");
        }
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
        return getDefaultAnimation().getBounds();
    }

    public Rectangle getDefaultBounds() {
        return new Rectangle(getBounds());
    }

    public Rectangle getDefaultBounds(int x, int y) {
        Rectangle blunaRect = getBounds();
        return new Rectangle(x + blunaRect.x, y + blunaRect.y, blunaRect.width,
                blunaRect.height);
    }

    public void setDirection(Direction direction) {
        currentDirection = direction;
    }

    public Direction getCurrentDirection() {
        return currentDirection;
    }
}
