package com.epsilon.util;

import java.util.Arrays;

import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_11_R1.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.epsilon.exceptions.WrongVersionException;

import net.minecraft.server.v1_11_R1.NBTTagCompound;
import net.minecraft.server.v1_11_R1.NBTTagList;

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
	 * NMS WARNING MUST USE VERSION 1.11.2
	 * Use ItemStack itm = ItemUtil.addGlow(itm); not just ItemUtil.addGlow(itm); 
	 * @param item The itemstack to add glow two.
	 * @return A itemstack with glow.
	 */
	public static ItemStack addGlow(ItemStack item) throws WrongVersionException {
		try {
			net.minecraft.server.v1_11_R1.ItemStack nmsStack = CraftItemStack.asNMSCopy(item);
			NBTTagCompound tag = null;
			if (!nmsStack.hasTag()) {
				tag = new NBTTagCompound();
				nmsStack.setTag(tag);
			}
			if (tag == null)
				tag = nmsStack.getTag();
			NBTTagList ench = new NBTTagList();
			tag.set("ench", ench);
			nmsStack.setTag(tag);
			return CraftItemStack.asCraftMirror(nmsStack);
		} catch (Error e) {
			if (e.getClass() == Error.class) // i.e. not a subclass of Error
				throw new WrongVersionException("Problem in ItemUtil.addGlow(). Probably wrong version.");
			throw e;
		}
	}

}
