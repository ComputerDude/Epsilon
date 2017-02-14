package com.epsilon.item.armor;

import org.bukkit.Material;

/**
 * This class is for creating new Armor
 */
public abstract class ArmorPiece {

	private static Material armor;
	
	protected ArmorPiece(Material armor) {
		this.armor = armor;
	}
	
}
