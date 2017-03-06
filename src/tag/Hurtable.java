package tag;

import geometry.Box;
import geometry.ComplexBox;
import geometry.MultiBox;

import java.util.List;

public interface Hurtable extends Teamable {
    boolean isInteracting(Hittable hittable);

    void hurt(Hittable hittable);

    ComplexBox getComplexBox();
}