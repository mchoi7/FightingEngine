package tag;

import geometry.Box;
import geometry.ComplexBox;
import geometry.MultiBox;
import geometry.Point;

import java.util.List;

public interface Hittable extends Teamable {
    double getDamage();

    default Point getKnockback() {
        return Point.origin;
    }

    ComplexBox getComplexBox();
}
