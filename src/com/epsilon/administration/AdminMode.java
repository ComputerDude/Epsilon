package com.epsilon.administration;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.epsilon.Epsilon;

public class AdminMode {

	public static void toggleAdminMode(Player player) {

		if (Epsilon.storageFile.getBoolean("player." + player.getUniqueId().toString() + ".adminMode") == true) {
			player.getInventory().setContents(
					(ItemStack[]) Epsilon.storageFile.get("player." + player.getUniqueId().toString() + ".adminModeInv"));
			Epsilon.storageFile.set("player." + player.getUniqueId().toString() + ".adminMode", false);
			Epsilon.storageFile.set("player." + player.getUniqueId().toString() + ".adminModeInv", null);
			Epsilon.storageFile.save();
			Epsilon.storageFile.reload();
		} else {
			Epsilon.storageFile.set("player." + player.getUniqueId().toString() + ".adminModeInv",
					player.getInventory().getContents());
			ItemStack[] clear = {};
			player.getInventory().setContents(clear);
			Epsilon.storageFile.set("player." + player.getUniqueId().toString() + ".adminMode", true);
			Epsilon.storageFile.save();
			Epsilon.storageFile.reload();
		}
	}

	public static boolean adminModeIsEnabled(Player player) {
		return Epsilon.storageFile.getBoolean("player." + player.getUniqueId().toString() + ".adminMode");
	}

}
