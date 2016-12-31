package environment.attack;

import geometry.Box;
import tag.Hittable;

import java.util.List;

public class BasicAttack implements Hittable {
    public BasicAttack() {
    }

    public boolean isReady() {
        return false;
    }

    public double getDamage() {
        return 0;
    }

    public List<Box> getBounds() {
        return null;
    }
}
