package tag;

public interface Respondable {
    boolean isColliding(Collidable collidable);

    void respond(Collidable collidable);
}
