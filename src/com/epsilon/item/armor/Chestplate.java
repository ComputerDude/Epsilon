package com.epsilon.item.armor;

import com.epsilon.item.RequirementType;
import org.bukkit.Material;

public class Chestplate extends ArmorPiece {

    private static final Chestplate[] ITEMS = new Chestplate[5];

    static {
        ITEMS[0] = new Chestplate("Leather Chestplate", Material.LEATHER_CHESTPLATE, 100, 4);
        ITEMS[0].setRequirement(RequirementType.LEVEL, 1);
        ITEMS[1] = new Chestplate("Chain Chestplate", Material.CHAINMAIL_CHESTPLATE, 200, 9);
        ITEMS[1].setRequirement(RequirementType.LEVEL, 30);
        ITEMS[2] = new Chestplate("Gold Chestplate", Material.GOLD_CHESTPLATE, 400, 16);
        ITEMS[2].setRequirement(RequirementType.LEVEL, 60);
        ITEMS[3] = new Chestplate("Iron Chestplate", Material.IRON_CHESTPLATE, 800, 25);
        ITEMS[3].setRequirement(RequirementType.LEVEL, 90);
        ITEMS[4] = new Chestplate("Diamond Chestplate", Material.DIAMOND_CHESTPLATE, 1600, 36);
        ITEMS[4].setRequirement(RequirementType.LEVEL, 120);
    }

    /**
     * Get the highest levelled chestplate whose level is less than or equal to the argument.
     */
    public static Chestplate getChestplate(int level) {
        for (int i = ITEMS.length - 1; i >= 0; i--) {
            if (((Integer) ITEMS[i].getRequirement(RequirementType.LEVEL)) <= level) return ITEMS[i];
        }
        return null;
    }

    private Chestplate(String name, Material material, int maxDurability, int buffQuality) {
        super(name, material, maxDurability, buffQuality);
    }

}
