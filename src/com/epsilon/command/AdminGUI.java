package com.epsilon.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import com.epsilon.Epsilon;
import com.epsilon.administration.AdminMode;
import com.epsilon.gui.GUI;
import com.epsilon.player.EPlayer;
import com.epsilon.util.ColorUtil;

public class AdminGUI implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (sender instanceof Player) {

			Player player = (Player) sender;
			EPlayer eplayer = new EPlayer(player);
			
			if (eplayer.getRank(player).getLevel() <= 8800) {

				GUI adminpanel = new GUI(Epsilon.getInstance(), player, ColorUtil.color("&4Admin Panel"), 9) {
					
					@Override
					public boolean onClick(InventoryClickEvent e) {
						return false;
					}
					
					@Override
					public void init() {
						
					}
				};;
				}
			return false;
			}

		return true;
	}
	/*
	 * G
	 */
	
}
