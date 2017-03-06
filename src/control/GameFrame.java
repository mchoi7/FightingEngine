package control;

import geometry.Point;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.Map;

public class GameFrame extends JFrame implements KeyListener, MouseListener, MouseMotionListener, MouseWheelListener {
    public static final int WIDTH = 2400, HEIGHT = 1600;
    public static final Color bg = Color.getHSBColor(.35f, .3f, .5f);
    private Map<Integer, Boolean> keyOn;
    private Point mouse = new Point(0, 0);
    private int scroll = 8;

    public GameFrame() {
        setTitle("Riot Summer Internship 2017 Application Project");
        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        createBufferStrategy(2);
        addKeyListener(this);
        addMouseListener(this);
        addMouseMotionListener(this);
        addMouseWheelListener(this);
        keyOn = new HashMap<>();
    }

    public boolean isKeyOn(int index) {
        return keyOn.get(index) != null ? keyOn.get(index) : false;
    }

    public Point getMouse() {
        return mouse;
    }

    public int getScroll() {
        return scroll;
    }

    public void keyTyped(KeyEvent e) {
    }

    public void keyPressed(KeyEvent e) {
        keyOn.put(e.getKeyCode(), true);
        if (isKeyOn(KeyEvent.VK_ESCAPE))
            System.exit(0);
    }

    public void keyReleased(KeyEvent e) {
        keyOn.put(e.getKeyCode(), false);
    }

    public void mouseClicked(MouseEvent e) {
        setMouse(e);
    }

    public void mousePressed(MouseEvent e) {
        keyOn.put(-1, SwingUtilities.isLeftMouseButton(e));
        keyOn.put(-2, SwingUtilities.isRightMouseButton(e));
        setMouse(e);
    }

    public void mouseReleased(MouseEvent e) {
        keyOn.put(-1, false);
        keyOn.put(-2, false);
        setMouse(e);
    }

    public void mouseEntered(MouseEvent e) {
        setMouse(e);
    }

    public void mouseExited(MouseEvent e) {
        setMouse(e);
    }

    public void mouseDragged(MouseEvent e) {
        setMouse(e);
    }

    public void mouseMoved(MouseEvent e) {
        setMouse(e);
    }

    public void mouseWheelMoved(MouseWheelEvent e) {
        scroll -= e.getWheelRotation();
    }

    private void setMouse(MouseEvent e) {
        mouse.set(2.0 * e.getX() / getWidth() - 1, 2.0 * e.getY() / getHeight() - 1);
    }
}
