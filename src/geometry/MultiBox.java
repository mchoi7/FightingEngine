package geometry;

import java.util.List;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class MultiBox extends Box {
    private List<Box> bounds;

    public MultiBox(List<Box> bounds) {
        super(0, 0, 0, 0);
        this.bounds = bounds;
        constrain(bounds);
    }

    public List<Box> getBounds() {
        return bounds;
    }

    private void constrain(List<Box> bounds) {
        double minX = getX(), minY = getY(), maxX = getX(), maxY = getY();
        for (Box box : bounds) {
            minX = min(minX, box.getX() - box.getW() / 2);
            minY = min(minY, box.getY() - box.getH() / 2);
            maxX = max(maxX, box.getX() + box.getW() / 2);
            maxY = max(maxY, box.getY() + box.getH() / 2);
        }
        set((maxX + minX) / 2, (maxY + minY) / 2);
        setW(maxX - minX);
        setH(maxY - minY);
    }
}
