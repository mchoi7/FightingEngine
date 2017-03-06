package control;

import geometry.Point;
import environment.solid.SolidBox;
import environment.structure.Scene;
import environment.character.Luna;
import tag.Teamable.Team;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Timer;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static java.lang.Math.round;

public class GameManager implements Runnable {
    private ScheduledExecutorService loop;
    private GameFrame gameFrame;
    private Scene scene;
    private Controller controller;
    private boolean running, pause;

    public GameManager() {
        gameFrame = new GameFrame();
        controller = new Controller();
        scene = loadScene(new File("resources/levels/Level1.txt"));
        scene.setMouse(gameFrame.getMouse());
        running = true;
        new Thread(this).start();
    }

    public void run() {
        long FPS = 60;
        long startTime, endTime;
        long lagTime = 0, sleepTime = 0, NSPF = 1000000000 / FPS;

        while (running) {
            startTime = System.nanoTime();
            step();
            long elapsedTime = (System.nanoTime() - startTime);
            sleepTime = NSPF - elapsedTime - lagTime;
            if(sleepTime > 0) {
                try {
                    Thread.sleep(sleepTime / 1000000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            lagTime = (System.nanoTime() - startTime) - NSPF;
        }
    }

    private Scene loadScene(File file) {
        Scene scene = new Scene();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] tag = line.split(" ", 2);
                Point point = Point.origin;
                double[] data;
                switch (tag[0]) {
                    case "B":
                        data = Arrays.stream(tag[1].split(" ")).mapToDouble(Double::parseDouble).toArray();
                        point = new SolidBox(data[0], data[1], data[2], data[3]);
                        break;
                    case "L":
                        String[] pieces = tag[1].split(" ");
                        Luna luna = new Luna(Double.parseDouble(pieces[0]), Double.parseDouble(pieces[1]), Team.valueOf(pieces[2]));
                        controller.setPlayer(luna);
                        scene.setFocus(luna);
                        point = luna;
                        break;
                }
                scene.add(point);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return scene;
    }

    private void step() {
        if (!pause) {
            controller.update(gameFrame);
            scene.setZoom(gameFrame.getScroll());
            scene.update();
        }
        BufferStrategy bs = gameFrame.getBufferStrategy();
        Graphics2D g = (Graphics2D) bs.getDrawGraphics();
        g.setColor(GameFrame.bg);
        g.fillRect(0, 0, gameFrame.getWidth(), gameFrame.getHeight());
        scene.render(g);
        bs.show();
        g.dispose();
    }
}
