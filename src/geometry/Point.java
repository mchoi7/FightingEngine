package geometry;

import static java.lang.Math.*;

public class Point {
    public static final Point origin = new Point(0, 0), identity = new Point(1, 1);
    private double x, y, lx, ly;

    public Point(double x, double y) {
        set(x, y);
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double takeX() {
        lx = x;
        x = 0;
        return lx;
    }

    public double takeY() {
        ly = y;
        y = 0;
        return ly;
    }

    public double getLx() {
        return lx;
    }

    public double getLy() {
        return ly;
    }

    public double getDx() {
        return lx - x;
    }

    public double getDy() {
        return ly - y;
    }

    public void set(double x, double y) {
        setLast();
        adjust(x, y);
    }

    public void adjust(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public void mux(double mx, double my) {
        set(mx * x, my * y);
    }

    public void add(double dx, double dy) {
        set(x + dx, y + dy);
    }

    public void add(double dx, double dy, double mdx, double mdy) {
        double fx, fy, sx = x + dx, sy = y + dy;
        if (abs(sx) <= mdx) fx = x + dx;
        else fx = signum(sx) * max(min(abs(sx), abs(x)), mdx);
        if (abs(sy) <= mdy) fy = y + dy;
        else fy = signum(sy) * max(min(abs(sy), abs(y)), mdy);
        set(fx, fy);
    }

    private void setLast() {
        lx = x;
        ly = y;
    }
}
