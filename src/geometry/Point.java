package geometry;

public class Point {
    private static final Point origin = new Point(0, 0);
    private final double ox, oy;
    private double x, y;
    private double lx, ly;
    private Point link;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
        ox = x;
        oy = y;
        setLast();
    }

    public double getOx() {
        return ox;
    }

    public double getOy() {
        return oy;
    }

    public double getX() {
        return link == null ? x : x + link.getX();
    }

    public double getY() {
        return link == null ? y : y + link.getY();
    }

    public double getX(Point tempLink) {
        return x + tempLink.getX();
    }

    public double getY(Point tempLink) {
        return y + tempLink.getY();
    }

    public double getLx() {
        return lx;
    }

    public double getLy() {
        return ly;
    }

    public void set(double x, double y) {
        setX(x);
        setY(y);
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void add(double dx, double dy) {
        addX(dx);
        addY(dy);
    }

    public void addX(double dx) {
        setX(x + dx);
    }

    public void addY(double dy) {
        setY(y + dy);
    }

    public void setLast() {
        lx = x;
        ly = y;
    }

    public Point getLink() {
        return link;
    }

    public void setLink(Point link) {
        this.link = link;
    }
}
