package com.epsilon.item.armor;

import org.bukkit.Material;
import static com.epsilon.util.RandomUtil.randIntTriangular;

/**
 * This class is for creating new Armor
 */
public abstract class ArmorPiece {

	public abstract Material getMaterial();

	private int durability;

	protected ArmorPiece() {
		if (getMaterial().name().startsWith("DIAMOND")) {
			this.durability = randIntTriangular(750, 2251);
		} else if (getMaterial().name().startsWith("IRON")) {
			this.durability = randIntTriangular(500, 1501);
		} else if (getMaterial().name().startsWith("CHAINMAIL")) {
			this.durability = randIntTriangular(250, 751);
		} else if (getMaterial().name().startsWith("GOLD")) {
			this.durability = randIntTriangular(125, 376);
		} else if (getMaterial().name().startsWith("LEATHER")) {
			this.durability = randIntTriangular(50, 151);
		}
	}

	public int getDurability() {
		return durability;
	}
}
