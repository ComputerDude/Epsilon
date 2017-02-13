package com.epsilon.administration;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.epsilon.Epsilon;

public class AdminModeManager {

	public static void toggleAdminMode(Player player) {

		if (Epsilon.adminModeConfig.getBoolean("player." + player.getUniqueId() + ".adminMode")) {
			if (Epsilon.adminModeConfig.get("player." + player.getUniqueId() + ".adminModeInv") != null) {
				player.getInventory().setContents(
						(ItemStack[]) Epsilon.adminModeConfig.get("player." + player.getUniqueId() + ".adminModeInv"));
			} else {
				player.getInventory().clear();
			}
			Epsilon.adminModeConfig.set("player." + player.getUniqueId() + ".adminMode", false);
			Epsilon.adminModeConfig.set("player." + player.getUniqueId() + ".adminModeInv", null);
			Epsilon.adminModeConfig.save();
			Epsilon.adminModeConfig.reload();
		} else {
			Epsilon.adminModeConfig.set("player." + player.getUniqueId() + ".adminModeInv",
					player.getInventory().getContents());

			player.getInventory().clear(); // Use #clear() method instead. - JustBru00

			Epsilon.adminModeConfig.set("player." + player.getUniqueId() + ".adminMode", true);
			Epsilon.adminModeConfig.save();
			Epsilon.adminModeConfig.reload();
		}
	}

	public static boolean isAdminModeEnabled(Player player) {
		return Epsilon.adminModeConfig.getBoolean("player." + player.getUniqueId() + ".adminMode");
	}

}
