package com.epsilon.item.armor;

import com.epsilon.item.RequirementType;
import org.bukkit.Material;

public class Leggings extends ArmorPiece {

    private static final Leggings[] ITEMS = new Leggings[5];

    static {
        ITEMS[0] = new Leggings("Leather Leggings", Material.LEATHER_LEGGINGS, 100, 4);
        ITEMS[0].setRequirement(RequirementType.LEVEL, 1);
        ITEMS[1] = new Leggings("Chain Leggings", Material.CHAINMAIL_LEGGINGS, 200, 9);
        ITEMS[1].setRequirement(RequirementType.LEVEL, 30);
        ITEMS[2] = new Leggings("Gold Leggings", Material.GOLD_LEGGINGS, 400, 16);
        ITEMS[2].setRequirement(RequirementType.LEVEL, 60);
        ITEMS[3] = new Leggings("Iron Leggings", Material.IRON_LEGGINGS, 800, 25);
        ITEMS[3].setRequirement(RequirementType.LEVEL, 90);
        ITEMS[4] = new Leggings("Diamond Leggings", Material.DIAMOND_LEGGINGS, 1600, 36);
        ITEMS[4].setRequirement(RequirementType.LEVEL, 120);
    }

    /**
     * Get the highest levelled leggings whose level is less than or equal to the argument.
     */
    public static Leggings getLeggings(int level) {
        for (int i = ITEMS.length - 1; i >= 0; i--) {
            if (((Integer) ITEMS[i].getRequirement(RequirementType.LEVEL)) <= level) return ITEMS[i];
        }
        return null;
    }
    
    private Leggings(String name, Material material, int maxDurability, int buffQuality) {
        super(name, material, maxDurability, buffQuality);
    }
    
}
