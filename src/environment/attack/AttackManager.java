package environment.attack;

import assist.FileAssistant;
import geometry.Box;
import geometry.MultiBox;
import tag.Hittable;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.FileHandler;

public class AttackManager {
    private static AttackManager am = new AttackManager();
    private Map<String, List<MultiBox>> attacks = new HashMap<>();

    private AttackManager() {
        File attacksDir = new File("resources/attacks");
        File[] attacksListing = attacksDir.listFiles();
        if(attacksListing != null)
            for (int i = 0; i < attacksListing.length; i++) {
                String name = attacksListing[i].getName();
                List<Box> bounds = new ArrayList<>();
                bounds.add(new Box(100, 100, 100, 100));
                attacks.put(name.substring(0, name.length() - 4), FileAssistant.getBoundsList(attacksListing[i]));
            }
    }

    public static List<MultiBox> getAttackBox(String name) {
        return am.attacks.get(name);
    }
}
