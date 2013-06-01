package de.propra13.views.objects;

public class Direction {

    private int vx;
    private int vy;

    public Direction(int vx, int vy) {
        this.vx = vx;
        this.vy = vy;
    }

    public int getVx() {
        return vx;
    }

    public int getVy() {
        return vy;
    }

    public void setVx(int vx) {
        this.vx = vx;
    }

    public void setVy(int vy) {
        this.vy = vy;
    }

    public boolean isMoving() {
        return vx != 0 || vy != 0;
    }

    public void setFrom(Direction direction) {
        vx = direction.vx;
        vy = direction.vy;
    }

    private int normalizedNumber(int x) {
        if (x == 0)
            return 0;
        return Math.abs(x) / x;
    }

    public int getNormalizedVx() {
        return normalizedNumber(vx);
    }

    public int getNormalizedVy() {
        return normalizedNumber(vy);
    }

    @Override
    public boolean equals(Object otherDirection) {
        return ((Direction) otherDirection).getNormalizedVx() == getNormalizedVx()
                && ((Direction) otherDirection).getNormalizedVy() == getNormalizedVy();
    }
}
