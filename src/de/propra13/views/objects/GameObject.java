package de.propra13.views.objects;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;

import de.propra13.assets.BlunaCrate;
import de.propra13.views.GameFieldView;

public class GameObject {
    protected int x;
    protected int y;

    protected int width;
    protected int height;

    protected BlunaCrate blunaCrate;
    protected int frames;
    protected int currentFrame = -1;

    protected BufferedImage currentBluna;

    public GameObject(int x, int y, int width, int height) {
        this.x = Math
                .max(scale(x), scale(x) - (width - GameFieldView.GRID) / 2);
        this.y = Math.max(scale(y), scale(y) - (height - GameFieldView.GRID)
                / 2);
        this.width = width;
        this.height = height;
    }

    public GameObject(BufferedImage image, int x, int y, int directions,
            int frames) {
        this(x, y, image.getWidth() / frames, image.getHeight() / directions);
        this.frames = frames;

        blunaCrate = new BlunaCrate(image, directions, frames);

        currentBluna = blunaCrate.getFirstBluna();
    }

    public void animate() {
        increaseFrame();
        currentBluna = blunaCrate.getBluna(currentFrame);
    }

    protected void increaseFrame() {
        currentFrame = ++currentFrame % frames;
    }

    public void draw(Graphics2D gfx, ImageObserver ob) {
        gfx.drawImage(getImage(), getX(), getY(), ob);
        /*
         * Polygon shape = new Polygon(); for (Point p : getPointMask()) {
         * shape.addPoint(p.x, p.y); } gfx.setPaint(Color.white);
         * gfx.drawPolygon(shape);
         */
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public BufferedImage getImage() {
        return currentBluna;
    }

    public Rectangle getBounds() {
        Rectangle blunaRect = blunaCrate.getBounds();
        int xdiff = (this.width - blunaRect.width) / 2;
        int ydiff = (this.height - blunaRect.height) / 2;
        return new Rectangle(x + xdiff, y + ydiff, blunaRect.width,
                blunaRect.height);
    }

    public Rectangle getBoundsFromOldX(int x) {
        Rectangle bounds = getBounds();
        bounds.x = x;
        return bounds;
    }

    public Rectangle getBoundsFromOldY(int y) {
        Rectangle bounds = getBounds();
        bounds.y = y;
        return bounds;
    }

    protected int scale(int x) {
        return (x * GameFieldView.GRID);
    }
    /*
     * public HashSet<Point> getPointMask() { return
     * getPointMaskFrom(blunaCrate.getPointMask()); }
     */
    /*
     * protected HashSet<Point> getPointMaskFrom(HashSet<Point> originalMask) {
     * HashSet<Point> mask = new HashSet<>(originalMask.size()); for (Point p :
     * originalMask) { mask.add(new Point(p.x + x, p.y + y)); } return mask; }
     * 
     * protected HashSet<Point> getPointMaskFromOldX(HashSet<Point>
     * originalMask, int diff) { HashSet<Point> mask = new
     * HashSet<>(originalMask.size()); for (Point p : originalMask) {
     * mask.add(new Point(p.x + diff, p.y)); } return mask; }
     * 
     * protected HashSet<Point> getPointMaskFromOldY(HashSet<Point>
     * originalMask, int diff) { HashSet<Point> mask = new
     * HashSet<>(originalMask.size()); for (Point p : originalMask) {
     * mask.add(new Point(p.x, p.y + diff)); } return mask; }
     */
}
