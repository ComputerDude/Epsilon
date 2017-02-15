package com.epsilon.item.weapon;

import com.epsilon.item.EItem;
import com.epsilon.item.RequirementType;
import org.bukkit.Material;

public class Sword extends EItem {

    private static final Sword[] ITEMS = new Sword[5];

    static {
        ITEMS[0] = new Sword("Wooden Sword", Material.WOOD_SWORD, 100, 4);
        ITEMS[0].setRequirement(RequirementType.LEVEL, 1);
        ITEMS[1] = new Sword("Stone Sword", Material.STONE_SWORD, 200, 9);
        ITEMS[1].setRequirement(RequirementType.LEVEL, 30);
        ITEMS[2] = new Sword("Gold Sword", Material.GOLD_SWORD, 400, 16);
        ITEMS[2].setRequirement(RequirementType.LEVEL, 60);
        ITEMS[3] = new Sword("Iron Sword", Material.IRON_SWORD, 800, 25);
        ITEMS[3].setRequirement(RequirementType.LEVEL, 90);
        ITEMS[4] = new Sword("Diamond Sword", Material.DIAMOND_SWORD, 1600, 36);
        ITEMS[4].setRequirement(RequirementType.LEVEL, 120);
    }

    /**
     * Get the highest levelled sword whose level is less than or equal to the argument.
     */
    public static Sword getSword(int level) {
        for (int i = ITEMS.length - 1; i >= 0; i--) {
            if (((Integer) ITEMS[i].getRequirement(RequirementType.LEVEL)) <= level) return ITEMS[i];
        }
        return null;
    }
    
    public Sword(String name, Material material, int maxDurability, int buffQuality) {
        super(name, material, maxDurability, buffQuality);
    }

}
