package com.epsilon.item.armor;

import java.util.concurrent.ThreadLocalRandom;

import org.bukkit.Material;

/**
 * This class is for creating new Armor
 */
public abstract class ArmorPiece {

	public abstract Material getMaterial();

	private int durability;

	protected ArmorPiece() {

		if (armor.name().startsWith("DIAMOND")) {
			this.durability = ThreadLocalRandom.current().nextInt(750, 2250);
		} else if (armor.name().startsWith("IRON")) {
			this.durability = ThreadLocalRandom.current().nextInt(500, 1500);
		} else if (armor.name().startsWith("CHAINMAIL")) {
			this.durability = ThreadLocalRandom.current().nextInt(250, 750);
		} else if (armor.name().startsWith("GOLD")) {
			this.durability = ThreadLocalRandom.current().nextInt(125, 375);
		} else if (armor.name().startsWith("LEATHER")) {
			this.durability = ThreadLocalRandom.current().nextInt(50, 150);
		}
	}

	public int getDurability() {
		return durability;
	}
}
