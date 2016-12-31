package assist;

import geometry.Box;
import geometry.Point;

import java.awt.*;

public class RenderAssistant {
    public static void drawRect(Box box, Graphics2D g) {
        g.drawRect((int) (box.getX() - box.getW() / 2), (int) (box.getY() - box.getH() / 2), (int) box.getW(), (int) box.getH());
    }


    public static void drawRect(Box box, Point tempLink, Graphics2D g) {
        g.drawRect((int) (box.getX(tempLink) - box.getW() / 2), (int) (box.getY(tempLink) - box.getH() / 2), (int) box.getW(), (int) box.getH());
    }
}