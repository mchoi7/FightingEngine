package geometry;

import java.util.List;

import static java.lang.Math.abs;
import static java.lang.Math.max;
import static java.lang.Math.min;

public class Box extends Point {
    private Point point;
    private double w, h;

    public Box(double x, double y, double w, double h) {
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

    public boolean intersects(Box box) {
        return 2 * abs(getX() - box.getX()) <= getW() + box.getW() && 2 * abs(getY() - box.getY()) <= getH() + box.getH();
    }
}
