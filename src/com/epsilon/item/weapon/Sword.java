package com.epsilon.item.weapon;

import static com.epsilon.util.RandomUtil.randIntTriangular;

import org.bukkit.Material;

public abstract class Sword {

	public abstract Material getMaterial();
	
	private int durability;
	private int damage;
	
	protected Sword() {
		
		if (getMaterial().name().startsWith("DIAMOND")) {
			this.durability = randIntTriangular(750, 2251);
		} else if (getMaterial().name().startsWith("IRON")) {
			this.durability = randIntTriangular(500, 1501);
		} else if (getMaterial().name().startsWith("STONE")) {
			this.durability = randIntTriangular(250, 751);
		} else if (getMaterial().name().startsWith("GOLD")) {
			this.durability = randIntTriangular(125, 376);
		} else if (getMaterial().name().startsWith("WOOD")) {
			this.durability = randIntTriangular(50, 151);
		}
		
	}
	
	public int getDurability() {
		return durability;
	}
	
	public int getDamage() {
		return damage;
	}
	
}
