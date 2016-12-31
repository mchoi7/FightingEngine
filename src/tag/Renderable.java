package tag;

import java.awt.*;

public interface Renderable {
    void render(Graphics2D g);

    default boolean isVisible() {
        return true;
    }
}
