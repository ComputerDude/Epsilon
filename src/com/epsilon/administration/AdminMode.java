package com.epsilon.administration;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.epsilon.Epsilon;

public class AdminMode {

	public static void toggleAdminMode(Player player) {

		if (Epsilon.storageFile.getBoolean("player." + player.getUniqueId() + ".adminMode")) {
			player.getInventory().setContents((ItemStack[]) Epsilon.storageFile.get("player." + player.getUniqueId() + ".adminModeInv"));
			Epsilon.storageFile.set("player." + player.getUniqueId() + ".adminMode", false);
			Epsilon.storageFile.set("player." + player.getUniqueId() + ".adminModeInv", null);
			Epsilon.storageFile.save();
			Epsilon.storageFile.reload();
		} else {
			Epsilon.storageFile.set("player." + player.getUniqueId() + ".adminModeInv",	player.getInventory().getContents());
			
			player.getInventory().clear(); // Use #clear() method instead. - JustBru00
			
			Epsilon.storageFile.set("player." + player.getUniqueId() + ".adminMode", true);
			Epsilon.storageFile.save();
			Epsilon.storageFile.reload();
		}
	}

	public static boolean isAdminModeEnabled(Player player) {
		return Epsilon.storageFile.getBoolean("player." + player.getUniqueId() + ".adminMode");
	}

}
