package com.epsilon.command;

import com.epsilon.Epsilon;
import com.epsilon.gui.AdminPanelGUI;
import com.epsilon.player.EPlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import static com.epsilon.util.ColorUtil.color;

public class CmdAdminPanel implements CommandExecutor{

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage(color("&4Only in-game players can do that!"));
			return true;
		}
		final Player player = (Player) sender;
		final EPlayer eplayer = new EPlayer(player);
		if (eplayer.getRank().getLevel() <= 8800) {
			sender.sendMessage(color("&4You don't have permission to do that!"));
			return true;
		}
		final AdminPanelGUI gui = new AdminPanelGUI(Epsilon.getInstance(), player);
		gui.init();
		gui.open();
		return true;
	}
	
}
