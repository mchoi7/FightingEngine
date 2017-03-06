package visual;

import assist.FileAssistant;
import geometry.Box;
import geometry.MultiBox;

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
        try {
            sheet = ImageIO.read(sheetFile);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        int imageWidth = sheet.getWidth() / imageNumber;
        List<MultiBox> boundsList = FileAssistant.getBoundsList(boundsFile);
        for (int i = 0; i < imageNumber; i++) {
            BufferedImage image = sheet.getSubimage(i * imageWidth, 0, imageWidth, sheet.getHeight());
            sprites[i] = new Sprite(image, boundsList.get(i));
        }
    }

    public Sprite getSprite(double index) {
        return sprites[(int) index % sprites.length];
    }
}