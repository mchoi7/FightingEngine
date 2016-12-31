package visual;

import geometry.Box;
import geometry.Point;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;

public class Sprite {
    private final BufferedImage image;
    private final List<Box> bounds;

    public Sprite(BufferedImage image, List<Box> bounds) {
        this.image = image;
        this.bounds = bounds;
    }

    public void render(Point point, Graphics2D g) {
        g.drawImage(image, (int) point.getX() - image.getWidth() / 2, (int) point.getY() - image.getHeight() / 2, null);
    }

    public List<Box> getBounds() {
        return bounds;
    }
}
