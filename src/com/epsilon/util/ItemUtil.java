package com.epsilon.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemUtil {

	/**
	 * Quickly create an {@link ItemStack}.
	 * 
	 * @param mat the item material.
	 * @param amount the quantity of the ItemStack.
	 * @param damage the damage value of the item.
	 * @param name the display name of the item.
	 * @param lore the lore of the item.
	 */
	public static ItemStack createItem(Material mat, int amount, short damage, String name, String... lore) {
		final ItemStack item = new ItemStack(mat, amount, damage);
		final ItemMeta meta = item.getItemMeta();
		if (name != null)
			meta.setDisplayName(name);
		if (lore != null && lore.length > 0)
			meta.setLore(Arrays.asList(lore));
		item.setItemMeta(meta);
		return item;
	}

	/**
	 * Add a glowing effect to an item stack.
	 * @param item the item to add the glowing effect to.
	 * @return a new item with the glowing effect.
	 */
	public static ItemStack addGlow(ItemStack item) {
		item = PacketUtil.setItemNBT(item, "ench", new ArrayList<Object>() {{
			add(new HashMap<String, Object>() {{
				put("id", (short) 6); // Aqua Affinity
				put("lvl", (short) 1);
			}});
		}});
		item = PacketUtil.setItemNBT(item, "HideFlags", 63); // Hide the enchantment text
		return item;
	}

}
