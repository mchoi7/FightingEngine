package visual;

import geometry.MultiBox;
import geometry.Point;
import geometry.Box;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.List;

public class Sprite {
    private final BufferedImage image;
    private final MultiBox multiBox;
    private AffineTransform tx;

    public Sprite(BufferedImage image, MultiBox multiBox) {
        this.image = image;
        this.multiBox = multiBox;
        tx = new AffineTransform();
    }

    public void render(Point point, Point scale, Graphics2D g) {
        tx.setToIdentity();
        tx.translate(point.getX() + -scale.getX() * image.getWidth() / 2, point.getY() + -scale.getY() * image.getHeight() / 2);
        tx.scale(scale.getX(), scale.getY());
        g.drawImage(image, tx, null);
    }

    public MultiBox getMultiBox() {
        return multiBox;
    }
}
