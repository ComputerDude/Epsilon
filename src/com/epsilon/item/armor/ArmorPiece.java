package com.epsilon.item.armor;

import java.util.concurrent.ThreadLocalRandom;

import org.bukkit.Material;

/**
 * This class is for creating new Armor
 */
public abstract class ArmorPiece {

	private static Material armor;
	private static int durability;

	protected ArmorPiece(Material armor) {
		this.armor = armor;

		if (armor.name().contains("DIAMOND")) {
			this.durability = ThreadLocalRandom.current().nextInt(750, 2250);

		} else if (armor.name().contains("IRON")) {
			this.durability = ThreadLocalRandom.current().nextInt(500, 1500);

		} else if (armor.name().contains("CHAINMAIL")) {
			this.durability = ThreadLocalRandom.current().nextInt(250, 750);

		} else if (armor.name().contains("GOLD")) {
			this.durability = ThreadLocalRandom.current().nextInt(125, 375);

		} else if (armor.name().contains("LEATHER")) {
			this.durability = ThreadLocalRandom.current().nextInt(50, 150);

		}
	}

}
