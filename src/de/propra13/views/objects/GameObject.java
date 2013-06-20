package de.propra13.views.objects;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RadialGradientPaint;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.util.HashMap;

import javax.swing.Timer;

import de.propra13.assets.BlunaCrate;
import de.propra13.assets.animations.Animation;
import de.propra13.assets.animations.AnimationManager;
import de.propra13.assets.animations.AnimationStateListener;
import de.propra13.views.GameFieldView;

public class GameObject {

    protected double x;
    protected double y;

    private AnimationManager animationManager;

    private boolean canAct = true;
    private boolean debug = false;
    private int glowRadius = 0;

    public GameObject(int x, int y, int width, int height) {
        this.x = (scale(x) - (width - GameFieldView.GRID) / 2);
        this.y = (scale(y) - (height - GameFieldView.GRID) / 2);
    }

    public GameObject(Animation defaultAnimation, int x, int y) {
        this(x, y, defaultAnimation.getSpriteWidth(), defaultAnimation
                .getSpriteHeight());
        animationManager = new AnimationManager(defaultAnimation);
    }

    public int getGlowRadius() {
        return glowRadius;
    }

    public void setGlowRadius(int glowRadius) {
        this.glowRadius = glowRadius;
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

    public void addAnimations(String defaultAnimationType,
            HashMap<String, HashMap<String, BlunaCrate>> animations) {
        for (String animationKey : animations.keySet()) {
            addAnimation(animationKey, new Animation(defaultAnimationType,
                    animations.get(animationKey)));
        }
    }

    public void addAnimation(String key, Animation animation) {
        animationManager.addAnimation(key, animation);
    }

    public Rectangle getDefaultBounds() {
        return animationManager.getDefaultBounds();
    }

    public void setAnimationDirection(Direction direction) {
        if (canAct)
            animationManager.setDirection(direction);
    }

    public void setCurrentAnimation(String animation) {
        if (canAct)
            animationManager.setCurrentAnimation(animation);
    }

    public Animation getDefaultAnimation() {
        return animationManager.getDefaultAnimation();
    }

    public void draw(Graphics2D gfx, ImageObserver ob) {
        BufferedImage image = getImage();
        Animation defaultAnimation = getDefaultAnimation();
        double dx = (defaultAnimation.getSpriteWidth() - image.getWidth()) / 2;
        double dy = (defaultAnimation.getSpriteHeight() - image.getHeight()) / 2;
        gfx.drawImage(getImage(), (int) Math.round(x + dx),
                (int) Math.round(y + dy), ob);
        if (debug)
            drawDebug(gfx, ob);
    }

    public void coroutine(int delay, final Coroutine coroutine) {
        new Timer(delay, new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                if (!coroutine.run()) {
                    ((Timer) e.getSource()).stop();
                }
            }
        }).start();
    }

    public Direction directionTo(GameObject object) {
        double nx = object.getCenter().x - getCenter().x;
        double ny = object.getCenter().y - getCenter().y;
        return new Direction(nx, ny);
    }

    public boolean isCloseTo(GameObject object, double maxDistance) {
        return object.getCenter().distance(getCenter()) < maxDistance;
    }

    public void lookAt(GameObject object) {
        setAnimationDirection(directionTo(object).getAnimationDirection());
    }

    public void dimGlowing(final int factor, int delay) {
        coroutine(delay, new Coroutine() {

            @Override
            public boolean run() {
                int radius = getGlowRadius();
                radius = (int) Math.max(0, radius
                        - (factor + (Math.random() - 0.5) * 5));

                setGlowRadius(radius);
                return radius > 0;
            }
        });
    }

    public void drawDebug(Graphics2D gfx, ImageObserver ob) {
        gfx.setPaint(Color.white);
        gfx.drawRect((int) getBounds().x, (int) getBounds().y,
                (int) getBounds().width, (int) getBounds().height);
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
    }

    public void toggleDebug() {
        debug = !debug;
    }

    public double getX() {
        return getBounds().x;
    }

    public double getY() {
        return getBounds().y;
    }

    public double getWidth() {
        return getBounds().width;
    }

    public double getHeight() {
        return getBounds().height;
    }

    public BufferedImage getImage() {
        return animationManager.getCurrentBluna();
    }

    public Rectangle.Double getBounds() {
        return animationManager.getDefaultBounds(x, y);
    }

    public double getBoundingArea() {
        Rectangle.Double bounds = getBounds();
        return bounds.width * bounds.height;
    }

    public double getIntersectionPercentage(GameObject object) {
        double area = getIntersectionAreaWith(object);
        if (area == Double.NaN) {
            return 0;
        }
        return area / getBoundingArea() * 100.0;
    }

    public double getIntersectionAreaWith(GameObject object) {
        Rectangle2D bounds = getBounds();
        Rectangle2D otherBounds = object.getBounds();
        if (!bounds.intersects(otherBounds)) {
            return Double.NaN;
        }
        Rectangle2D intersection = otherBounds.createIntersection(bounds);
        return intersection.getWidth() * intersection.getHeight();
    }

    public Point2D.Double getCenter() {
        return new Point2D.Double(getBounds().getCenterX(), getBounds()
                .getCenterY());
    }

    public Point getGridCenter() {
        Point2D.Double center = getCenter();
        return new Point((int) (center.x / GameFieldView.GRID),
                (int) (center.y / GameFieldView.GRID));
    }

    protected static double scale(double x) {
        return (x * GameFieldView.GRID);
    }

    public void glow(Graphics2D translatedGfx) {
        RadialGradientPaint gradient = new RadialGradientPaint(0, 0,
                getGlowRadius(), new float[] { 0, 1 }, new Color[] {
                        new Color(0, 0, 0, 0), Color.black });

        translatedGfx.setPaint(gradient);
        AlphaComposite composite = AlphaComposite.getInstance(
                AlphaComposite.DST_IN, 1);

        translatedGfx.setComposite(composite);
        int radius = getGlowRadius();
        translatedGfx.fillArc(-radius, -radius, 2 * radius, 2 * radius, 0, 360);
    }

    public boolean glows() {
        return glowRadius > 0;
    }

    public void keyPressed(KeyEvent event) {
    }

    public void keyReleased(KeyEvent event) {
    }
}
