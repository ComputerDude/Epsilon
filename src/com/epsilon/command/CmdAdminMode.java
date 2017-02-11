package com.epsilon.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.epsilon.administration.AdminMode;
import com.epsilon.player.EPlayer;

public class CmdAdminMode implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (sender instanceof Player) {

			Player player = (Player) sender;
			EPlayer eplayer = new EPlayer(player);
			
			if (eplayer.getRank().getLevel() <= 8800) { // Fix using a depreciated method. - JustBru00

				if(args.length == 0) {
					AdminMode.toggleAdminMode(player);
				}
			}

		}
		return true;
	}

}
