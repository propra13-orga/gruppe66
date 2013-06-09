package de.propra13.views.objects;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

import de.propra13.assets.animations.Animation;
import de.propra13.assets.animations.AnimationManager;
import de.propra13.assets.animations.AnimationStateListener;
import de.propra13.views.GameFieldView;

public class GameObject {

    protected int x;
    protected int y;

    private AnimationManager animationManager;

    private boolean canAct = true;
    private boolean debug = false;

    public GameObject(int x, int y, int width, int height) {
        this.x = (scale(x) - (width - GameFieldView.GRID) / 2);
        this.y = (scale(y) - (height - GameFieldView.GRID) / 2);
    }

    public GameObject(Animation defaultAnimation, int x, int y) {
        this(x, y, defaultAnimation.getSpriteWidth(), defaultAnimation
                .getSpriteHeight());
        animationManager = new AnimationManager(defaultAnimation);
    }

    public boolean canAct() {
        return canAct;
    }

    public void setCanAct(boolean canAct) {
        this.canAct = canAct;
    }

    public void animate() {
        animationManager.getCurrentAnimation().animate();
    }

    public void triggerAnimation(String animation) {
        triggerAnimation(animation, null);
    }

    public void triggerAnimation(String animation,
            final AnimationStateListener listener) {
        if (canAct) {
            canAct = false;
            animationManager.triggerAnimation(animation,
                    new AnimationStateListener() {

                        @Override
                        public void willStart() {
                            if (listener != null)
                                listener.willStart();
                        }

                        @Override
                        public void didEnd() {
                            canAct = true;
                            if (listener != null)
                                listener.didEnd();
                        }
                    });
        }
    }

    public void setCurrentAnimationType(String typ) {
        if (canAct)
            animationManager.setCurrentAnimationType(typ);
    }

    public void addAnimation(String key, Animation animation) {
        animationManager.addAnimation(key, animation);
    }

    public Rectangle getDefaultBounds() {
        return animationManager.getDefaultBounds();
    }

    public void setDirection(Direction direction) {
        if (canAct)
            animationManager.setDirection(direction);
    }

    public void setCurrentAnimation(String animation) {
        if (canAct)
            animationManager.setCurrentAnimation(animation);
    }

    public void draw(Graphics2D gfx, ImageObserver ob) {
        gfx.drawImage(getImage(), x, y, ob);
        if (debug)
            drawDebug(gfx, ob);
    }

    public void drawDebug(Graphics2D gfx, ImageObserver ob) {
        gfx.setPaint(Color.white);
        gfx.drawRect(getBounds().x, getBounds().y, getBounds().width,
                getBounds().height);
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
    }

    public void toggleDebug() {
        debug = !debug;
    }

    public int getX() {
        return getBounds().x;
    }

    public int getY() {
        return getBounds().y;
    }

    public int getWidth() {
        return getBounds().width;
    }

    public int getHeight() {
        return getBounds().height;
    }

    public BufferedImage getImage() {
        return animationManager.getCurrentBluna();
    }

    public Rectangle getBounds() {
        return animationManager.getDefaultBounds(x, y);
    }

    public Point2D.Double getCenter() {
        return new Point2D.Double(getBounds().getCenterX(), getBounds()
                .getCenterY());
    }

    protected int scale(int x) {
        return (x * GameFieldView.GRID);
    }
}
