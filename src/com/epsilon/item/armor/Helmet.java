package com.epsilon.item.armor;

import com.epsilon.item.RequirementType;
import org.bukkit.Material;

public class Helmet extends ArmorPiece{

    private static final Helmet[] ITEMS = new Helmet[5];

    static {
        ITEMS[0] = new Helmet("Leather Helmet", Material.LEATHER_HELMET, 100, 4);
        ITEMS[0].setRequirement(RequirementType.LEVEL, 1);
        ITEMS[1] = new Helmet("Chain Helmet", Material.CHAINMAIL_HELMET, 200, 9);
        ITEMS[1].setRequirement(RequirementType.LEVEL, 30);
        ITEMS[2] = new Helmet("Gold Helmet", Material.GOLD_HELMET, 400, 16);
        ITEMS[2].setRequirement(RequirementType.LEVEL, 60);
        ITEMS[3] = new Helmet("Iron Helmet", Material.IRON_HELMET, 800, 25);
        ITEMS[3].setRequirement(RequirementType.LEVEL, 90);
        ITEMS[4] = new Helmet("Diamond Helmet", Material.DIAMOND_HELMET, 1600, 36);
        ITEMS[4].setRequirement(RequirementType.LEVEL, 120);
    }

    /**
     * Get the highest levelled helmet whose level is less than or equal to the argument.
     */
    public static Helmet getHelmet(int level) {
        for (int i = ITEMS.length - 1; i >= 0; i--) {
            if (((Integer) ITEMS[i].getRequirement(RequirementType.LEVEL)) <= level) return ITEMS[i];
        }
        return null;
    }

    private Helmet(String name, Material material, int maxDurability, int buffQuality) {
        super(name, material, maxDurability, buffQuality);
    }

}
