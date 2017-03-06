package geometry;

import static java.lang.Math.abs;
/* TODO */
public class Oval extends Point {
    private Point point;
    private double w, h;

    public Oval(double x, double y, double w, double h) {
        super(x, y);
        this.w = w;
        this.h = h;
    }

    public double getW() {
        return w;
    }

    public double getH() {
        return h;
    }

    public void setW(double w) {
        this.w = w;
    }

    public void setH(double h) {
        this.h = h;
    }

    public boolean intersects(Oval oval) {
        return true;
    }
}
