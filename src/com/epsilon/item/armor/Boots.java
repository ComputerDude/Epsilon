package com.epsilon.item.armor;

import com.epsilon.item.RequirementType;
import org.bukkit.Material;

public class Boots extends ArmorPiece {

    private static final Boots[] ITEMS = new Boots[5];

    static {
        ITEMS[0] = new Boots("Leather Boots", Material.LEATHER_BOOTS, 100, 4);
        ITEMS[0].setRequirement(RequirementType.LEVEL, 1);
        ITEMS[1] = new Boots("Chain Boots", Material.CHAINMAIL_BOOTS, 200, 9);
        ITEMS[1].setRequirement(RequirementType.LEVEL, 30);
        ITEMS[2] = new Boots("Gold Boots", Material.GOLD_BOOTS, 400, 16);
        ITEMS[2].setRequirement(RequirementType.LEVEL, 60);
        ITEMS[3] = new Boots("Iron Boots", Material.IRON_BOOTS, 800, 25);
        ITEMS[3].setRequirement(RequirementType.LEVEL, 90);
        ITEMS[4] = new Boots("Diamond Boots", Material.DIAMOND_BOOTS, 1600, 36);
        ITEMS[4].setRequirement(RequirementType.LEVEL, 120);
    }

    /**
     * Get the highest levelled boots whose level is less than or equal to the argument.
     */
    public static Boots getBoots(int level) {
        for (int i = ITEMS.length - 1; i >= 0; i--) {
            if (((Integer) ITEMS[i].getRequirement(RequirementType.LEVEL)) <= level) return ITEMS[i];
        }
        return null;
    }
    
    private Boots(String name, Material material, int maxDurability, int buffQuality) {
        super(name, material, maxDurability, buffQuality);
    }
    
}
