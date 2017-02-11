package com.epsilon.administration;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.epsilon.Epsilon;

public class AdminMode {

	public static void toggleAdminMode(Player player) {

		if (Epsilon.adminModeConfig.getBoolean("player." + player.getUniqueId() + ".adminMode")) {
			player.getInventory().setContents((ItemStack[]) Epsilon.adminModeConfig.get("player." + player.getUniqueId() + ".adminModeInv"));
			Epsilon.adminModeConfig.set("player." + player.getUniqueId() + ".adminMode", false);
			Epsilon.adminModeConfig.set("player." + player.getUniqueId() + ".adminModeInv", null);
			Epsilon.adminModeConfig.save();
			Epsilon.adminModeConfig.reload();
		} else {
			Epsilon.adminModeConfig.set("player." + player.getUniqueId() + ".adminModeInv",	player.getInventory().getContents());
			
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
