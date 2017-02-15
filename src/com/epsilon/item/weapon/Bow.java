package com.epsilon.item.weapon;

import com.epsilon.item.EItem;
import com.epsilon.item.RequirementType;
import org.bukkit.Material;

public class Bow extends EItem {

    private static final Bow[] ITEMS = new Bow[5];

    static {
        ITEMS[0] = new Bow("Oak Wood Bow", Material.BOW, 100, 4);
        ITEMS[0].setRequirement(RequirementType.LEVEL, 1);
        ITEMS[1] = new Bow("Dark Oak Wood Bow", Material.BOW, 200, 9);
        ITEMS[1].setRequirement(RequirementType.LEVEL, 30);
        ITEMS[2] = new Bow("Birch Wood Bow", Material.BOW, 400, 16);
        ITEMS[2].setRequirement(RequirementType.LEVEL, 60);
        ITEMS[3] = new Bow("Jungle Wood Bow", Material.BOW, 800, 25);
        ITEMS[3].setRequirement(RequirementType.LEVEL, 90);
        ITEMS[4] = new Bow("Spruce Wood Bow", Material.BOW, 1600, 36);
        ITEMS[4].setRequirement(RequirementType.LEVEL, 120);
    }

    /**
     * Get the highest levelled bow whose level is less than or equal to the argument.
     */
    public static Bow getBow(int level) {
        for (int i = ITEMS.length - 1; i >= 0; i--) {
            if (((Integer) ITEMS[i].getRequirement(RequirementType.LEVEL)) <= level) return ITEMS[i];
        }
        return null;
    }
    
    private Bow(String name, Material material, int maxDurability, int buffQuality) {
        super(name, material, maxDurability, buffQuality);
    }

}
