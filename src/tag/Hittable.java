package tag;

import geometry.Box;

import java.util.List;

public interface Hittable {
    boolean isReady();

    double getDamage();

    List<Box> getBounds();
}
