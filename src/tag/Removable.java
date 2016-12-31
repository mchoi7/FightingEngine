package tag;

public interface Removable {
    default boolean isFinished() {
        return false;
    }
}
