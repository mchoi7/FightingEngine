package visual;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class VisualManager {
    private static VisualManager vm = new VisualManager();
    private Map<String, Animation> animations = new HashMap<>();

    private VisualManager() {
        File sheetsDir = new File("resources/sheets");
        File boundsDir = new File("resources/bounds");
        File[] sheetsListing = sheetsDir.listFiles();
        File[] boundsListing = boundsDir.listFiles();
        if(sheetsListing != null && boundsListing != null && sheetsListing.length == boundsListing.length)
            for (int i = 0; i < sheetsListing.length; i++) {
                String name = sheetsListing[i].getName();
                animations.put(name.substring(0, name.length() - 7), new Animation(sheetsListing[i], boundsListing[i]));
            }
    }

    public static Animation getAnimation(String name) {
        return vm.animations.get(name);
    }
}