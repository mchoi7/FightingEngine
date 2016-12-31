package control;

import environment.Player;
import environment.Solid;
import environment.Scene;
import environment.character.Luna;

import java.awt.*;
import java.awt.image.BufferStrategy;

public class GameManager {
    private GameFrame gameFrame;
    private Scene scene;
    private Controller controller;
    private boolean running, pause;

    public GameManager() {
        gameFrame = new GameFrame();
        scene = new Scene();
        controller = new Controller();
        running = true;
        Thread t = new Thread(this::loop);
        t.setDaemon(true);
        t.start();
    }

    private void initialize() {
        Luna luna = new Luna(650, 400);
        controller.setPlayer(luna);
        scene.add(luna);
        scene.add(new Solid(300, 300, 100, 100));
    }

    private void loop() {
        initialize();
        long startTime;
        long MSPF = 1000 / 60;
        while (running) {
            startTime = System.currentTimeMillis();
            if (!pause) {
                controller.update(gameFrame);
                scene.update();
            }
            BufferStrategy bs = gameFrame.getBufferStrategy();
            Graphics2D g = (Graphics2D) bs.getDrawGraphics();
            g.setColor(GameFrame.bg);
            g.fillRect(0, 0, gameFrame.getWidth(), gameFrame.getHeight());
            scene.render(g);
            bs.show();
            g.dispose();
            long sleepTime = MSPF - (System.currentTimeMillis() - startTime);
            if (sleepTime > 0)
                try {
                    Thread.sleep(sleepTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
        }
    }
}
