package com.epsilon.administration;

import com.epsilon.util.PluginFile;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class AdminModeManager {

	private static PluginFile config;

	public static void setConfig(PluginFile config) {
		AdminModeManager.config = config;
	}

	public static void toggleAdminMode(Player player) {

		if (config.getBoolean("player." + player.getUniqueId() + ".adminMode")) {
			if (config.get("player." + player.getUniqueId() + ".adminModeInv") != null) {
				player.getInventory().setContents(
						(ItemStack[]) config.get("player." + player.getUniqueId() + ".adminModeInv"));
			} else {
				player.getInventory().clear();
			}
			config.set("player." + player.getUniqueId() + ".adminMode", false);
			config.set("player." + player.getUniqueId() + ".adminModeInv", null);
			config.save();
			config.reload();
		} else {
			config.set("player." + player.getUniqueId() + ".adminModeInv",
					player.getInventory().getContents());

			player.getInventory().clear(); // Use #clear() method instead. - JustBru00

			config.set("player." + player.getUniqueId() + ".adminMode", true);
			config.save();
			config.reload();
		}
	}

	public static boolean isAdminModeEnabled(Player player) {
		return config.getBoolean("player." + player.getUniqueId() + ".adminMode");
	}

}
