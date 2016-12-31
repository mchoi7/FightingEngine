package tag;

public interface Hurtable {
    boolean isInteracting(Hittable hittable);

    void hurt(Hittable hittable);
}
