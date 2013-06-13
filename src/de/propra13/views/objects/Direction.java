package de.propra13.views.objects;

public class Direction {

    public class VelocityVector {
        public double vx = 0;
        public double vy = 0;

        public VelocityVector(double vx, double vy) {
            this.vx = vx;
            this.vy = vy;
        }
    }

    private double vx;
    private double vy;

    public Direction(double vx2, double vy2) {
        this.vx = vx2;
        this.vy = vy2;
    }

    public Direction(Direction direction) {
        setFrom(direction);
    }

    public double getVx() {
        return vx;
    }

    public double getVy() {
        return vy;
    }

    public void setVx(int vx) {
        this.vx = vx;
    }

    public void setVy(int vy) {
        this.vy = vy;
    }

    public boolean isMoving() {
        return Math.abs(vx) > 0.0001 || Math.abs(vy) > 0.0001;
    }

    public void setFrom(Direction direction) {
        vx = direction.vx;
        vy = direction.vy;
    }

    private int normalizedNumber(double x) {
        if (Math.abs(x) < 0.0001)
            return 0;
        else if (x < 0)
            return -1;
        else
            return 1;
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

    public VelocityVector getVelocityVector(double velocity) {
        double normvx = 0, normvy = 0, length = length();
        if (length != 0) {
            normvx = vx / length;
            normvy = vy / length;
        }
        return new VelocityVector(velocity * normvx, velocity * normvy);
    }

    public void bounceX() {
        vx *= -1;
    }

    public void bounceY() {
        vy *= -1;
    }

    public void stop() {
        vx = vy = 0;
    }

    public double length() {
        return Math.sqrt(vx * vx + vy * vy);
    }
}
