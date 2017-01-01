package control;

import geometry.Point;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.Map;

public class GameFrame extends JFrame implements KeyListener, MouseListener, MouseMotionListener {
    private static final int WIDTH = 1280, HEIGHT = 960;
    public static final Color bg = Color.black;
    private boolean[] keys = new boolean[1024];
    private boolean mouseOn;
    private Point mouse = new Point(0, 0);

    public GameFrame() {
        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null);
        setUndecorated(true);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
        createBufferStrategy(3);
        addKeyListener(this);
        addMouseListener(this);
        addMouseListener(this);
    }

    public boolean isKeyOn(int index) {
        return keys[index];
    }

    public boolean isMouseOn() {
        return mouseOn;
    }

    public Point getMouse() {
        return mouse;
    }

    public void keyTyped(KeyEvent e) {
    }

    public void keyPressed(KeyEvent e) {
        keys[e.getKeyCode()] = true;
        //if (isKeyOn(KeyEvent.VK_ESCAPE))
        //    System.exit(0);
    }

    public void keyReleased(KeyEvent e) {
        keys[e.getKeyCode()] = false;
    }

    public void mouseClicked(MouseEvent e) {
        mouse.set((double) e.getX() / WIDTH, (double) e.getY() / HEIGHT);
    }

    public void mousePressed(MouseEvent e) {
        mouse.set((double) e.getX() / WIDTH, (double) e.getY() / HEIGHT);
        mouseOn = true;
    }

    public void mouseReleased(MouseEvent e) {
        mouse.set((double) e.getX() / WIDTH, (double) e.getY() / HEIGHT);
        mouseOn = false;
    }

    public void mouseEntered(MouseEvent e) {
        mouse.set((double) e.getX() / WIDTH, (double) e.getY() / HEIGHT);
    }

    public void mouseExited(MouseEvent e) {
        mouse.set((double) e.getX() / WIDTH, (double) e.getY() / HEIGHT);
    }

    public void mouseDragged(MouseEvent e) {
        mouse.set((double) e.getX() / WIDTH, (double) e.getY() / HEIGHT);
    }

    public void mouseMoved(MouseEvent e) {
        mouse.set((double) e.getX() / WIDTH, (double) e.getY() / HEIGHT);
    }
}
