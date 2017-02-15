package com.epsilon.item.armor;

import com.epsilon.item.EItem;
import org.bukkit.Material;

/**
 * Represents a piece of armour.
 */
public abstract class ArmorPiece extends EItem {

    public ArmorPiece(String name, Material material, int maxDurability, int buffQuality) {
        super(name, material, maxDurability, buffQuality);
    }

}
