package visual;

import geometry.Box;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Animation {
    private final Sprite[] sprites;

    public Animation(File sheetFile, File boundsFile) {
        String name = sheetFile.getName();
        int imageNumber = name.charAt(name.length() - 6) - '0';
        sprites = new Sprite[imageNumber];
        BufferedImage sheet;
        List<List<Box>> boundsList = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(boundsFile))) {
            sheet = ImageIO.read(sheetFile);
            String line;
            int i = 0;
            while ((line = br.readLine()) != null) {
                List<Box> bounds = new ArrayList<>();
                for (String boxData : line.split(",")) {
                    double[] data = Arrays.stream(boxData.split(" ")).mapToDouble(Double::parseDouble).toArray();
                    bounds.add(new Box(data[0], data[1], data[2], data[3]));
                }
                boundsList.add(i++, bounds);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        int imageWidth = sheet.getWidth() / imageNumber;
        for (int i = 0; i < imageNumber; i++) {
            BufferedImage image = sheet.getSubimage(i * imageWidth, 0, imageWidth, sheet.getHeight());
            sprites[i] = new Sprite(image, boundsList.get(i));
        }
    }

    public Sprite getSprite(double index) {
        return sprites[(int) index % sprites.length];
    }
}