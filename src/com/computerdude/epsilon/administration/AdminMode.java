package com.computerdude.epsilon.administration;

import org.bukkit.entity.Player;

import com.computerdude.epsilon.Epsilon;

public class AdminMode {

	
	public static void toggleAdminMode(Player player) {
		if(Epsilon.storageFile.getBoolean("player." + player.getUniqueId().toString() + ".adminMode") == true) {
			Epsilon.storageFile.set("player." + player.getUniqueId().toString() + ".adminMode", false);
			Epsilon.storageFile.save();
			Epsilon.storageFile.reload();
		} else {
			Epsilon.storageFile.set("player." + player.getUniqueId().toString() + ".adminMode", true);
			Epsilon.storageFile.save();
			Epsilon.storageFile.reload();
		}
	}
	
	public static boolean adminModeIsEnabled(Player player) {
		return Epsilon.storageFile.getBoolean("player." + player.getUniqueId().toString() + ".adminMode");
	}
	
}
