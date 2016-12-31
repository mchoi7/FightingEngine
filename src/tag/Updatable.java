package tag;

public interface Updatable {
    void update();

    default boolean isAwake() {
        return true;
    }
}