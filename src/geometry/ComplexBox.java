package geometry;

import java.util.ArrayList;

import static java.lang.Math.abs;

public class ComplexBox {
    protected MultiBox multiBox;
    private Point link, scale;

    public ComplexBox(Point link, Point scale) {
        this.link = link;
        this.scale = scale;
        multiBox = new MultiBox(new ArrayList<>());
    }

    public boolean intersects(ComplexBox complexBox) {
        if (multiBox.intersects((Box) complexBox.multiBox))
            for (Box boxA : multiBox.getBounds())
                for (Box boxB : complexBox.multiBox.getBounds())
                    if (intersects(boxA, boxB, link, complexBox.link, scale, complexBox.scale))
                        return true;
        return false;
    }

    private boolean intersects(Box boxA, Box boxB, Point linkA, Point linkB, Point scaleA, Point scaleB) {
        double dx = scaleA.getX() * boxA.getX() - scaleB.getX() * boxB.getX() + linkA.getX() - linkB.getX();
        double dy = scaleA.getY() * boxA.getY() - scaleB.getY() * boxB.getY() + linkA.getY() - linkB.getY();
        return 2 * abs(dx) < boxA.getW() + boxB.getW() && 2 * abs(dy) < boxA.getH() + boxB.getH();
    }

    public MultiBox getMultiBox() {
        return multiBox;
    }

    public Point getLink() {
        return link;
    }

    public Point getScale() {
        return scale;
    }

    public void setMultiBox(MultiBox multiBox) {
        this.multiBox = multiBox;
    }
}
