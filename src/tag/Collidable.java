package tag;

import geometry.Box;

import java.util.List;

public interface Collidable {
    enum Attribute {
        FIXED, FLUID
    }

    Box getBound();

    default List<Box> getBounds() {
        return null;
    }

    Attribute getAttribute();
}