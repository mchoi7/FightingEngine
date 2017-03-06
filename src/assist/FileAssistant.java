package assist;

import geometry.Box;
import geometry.MultiBox;

import javax.imageio.ImageIO;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileAssistant {

    public static List<MultiBox> getBoundsList(File file) {
        List<MultiBox> boundsList = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                List<Box> bounds = new ArrayList<>();
                for (String boxData : line.split(",")) {
                    double[] data = Arrays.stream(boxData.split(" ")).mapToDouble(Double::parseDouble).toArray();
                    bounds.add(new Box(data[0], data[1], data[2], data[3]));
                }
                boundsList.add(new MultiBox(bounds));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return boundsList;
    }
}
