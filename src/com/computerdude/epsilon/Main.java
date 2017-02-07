package com.computerdude.epsilon;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.computerdude.epsilon.game.Spawn;
import com.computerdude.epsilon.player.Chat;
import com.computerdude.epsilon.util.MySQL;
import com.computerdude.epsilon.util.PluginFile;

public class Main extends JavaPlugin {
	
	public static PluginFile storageFile = null;
	
	@Override
	public void onEnable() {
		storageFile = new PluginFile(this, "storage.yml", "storage.yml");
		PluginManager pm = Bukkit.getServer().getPluginManager();

		pm.registerEvents(new Spawn(), this);
		pm.registerEvents(new Chat(), this);
		pm.registerEvents(new MySQL(), this);

	}

	@Override
	public void onDisable() {

		MySQL.disconnect();
	}

	/**
	 * @param uncoloredtext
	 *            | Text to Color
	 * @return | Returns Text Colored
	 */
	public static String color(String uncoloredtext) {
		return ChatColor.translateAlternateColorCodes('&', uncoloredtext);
	}

	public static void sendChatMessage(Entity en, String message, Player sender, int level) {

		if (level < 20) {

			String msg = Main.color("&7(" + level + ") &f" + sender.getName() + "&7: &f") + message;
			en.sendMessage(msg);

		} else if (level >= 20 && level < 40) {

			String msg = Main.color("&e(" + level + ") &f" + sender.getName() + "&e: &f") + message;
			en.sendMessage(msg);

		} else if (level >= 40 && level < 60) {

			String msg = Main.color("&6(" + level + ") &f" + sender.getName() + "&6: &f") + message;
			en.sendMessage(msg);

		} else if (level >= 60 && level < 80) {

			String msg = Main.color("&4(" + level + ") &f" + sender.getName() + "&4: &f") + message;
			en.sendMessage(msg);

		} else if (level >= 80 && level < 100) {

			String msg = Main.color("&5(" + level + ") &f" + sender.getName() + "&5: &f") + message;
			en.sendMessage(msg);

		} else if (level >= 100 && level < 120) {

			String msg = Main.color("&1(" + level + ") &f" + sender.getName() + "&1: &f") + message;
			en.sendMessage(msg);

		} else if (level >= 120 && level < 140) {

			String msg = Main.color("&9(" + level + ") &f" + sender.getName() + "&9: &f") + message;
			en.sendMessage(msg);

		} else if (level >= 140) {

			String msg = Main.color("&b(" + level + ") &f" + sender.getName() + "&b: &f") + message;
			en.sendMessage(msg);
			
		}

	}

	
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (cmd.getName().equalsIgnoreCase("setlevel") && sender instanceof Player) {

			Player player = (Player) sender;

			if (args.length != 1) {
				player.sendMessage(color("&4Error!"));
				
			} else {
				
				int amount = Integer.parseInt(args[0]);
				
				MySQL.setLevel(player, amount);
				
				player.sendMessage(color("Changed level to " + args[0]));
			}
			return true;
		}

		return false;

	}

}