package tag;

public interface Collidable {
    boolean isColliding(Respondable respondable);

    void collide(Respondable respondable);
}