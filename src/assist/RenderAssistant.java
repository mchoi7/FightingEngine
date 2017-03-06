package assist;

import geometry.Box;
import geometry.ComplexBox;
import geometry.MultiBox;
import geometry.Point;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.List;

public class RenderAssistant {
    private static AffineTransform id = new AffineTransform();
    private static final Font f = new Font("Courier New", Font.PLAIN, 32);

    public static void drawRect(Box box, Graphics2D g) {
        g.drawRect((int) (box.getX() - box.getW() / 2), (int) (box.getY() - box.getH() / 2),
                (int) box.getW(), (int) box.getH());
    }

    public static void drawRect(Box box, Point link, Point scale, Graphics2D g) {
        g.drawRect((int) (scale.getX() * box.getX() + link.getX() - box.getW() / 2),
                (int) (scale.getY() * box.getY() + link.getY() - box.getH() / 2),
                (int) box.getW(), (int) box.getH());
    }

    public static void drawRect(ComplexBox complexBox, Graphics2D g) {
        complexBox.getMultiBox().getBounds().forEach(box -> drawRect(box, complexBox.getLink(), complexBox.getScale(), g));
    }

    public static void drawMessage(List<String> message, Graphics2D g) {
        g.setFont(f);
        AffineTransform tx = g.getTransform();
        g.setTransform(id);
        for (int i = 0; i < message.size(); i++)
            g.drawString(message.get(i), f.getSize(), f.getSize() * (i + 1));
        g.setTransform(tx);
    }
}