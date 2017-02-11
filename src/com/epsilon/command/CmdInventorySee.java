package com.epsilon.command;

import com.epsilon.Epsilon;
import com.epsilon.gui.InventorySeeGUI;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import static com.epsilon.util.ColorUtil.color;
import static com.epsilon.util.ColorUtil.colorf;

public class CmdInventorySee implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(color("&4Only in-game players can do that!"));
            return true;
        }
        if (args.length != 1) {
            sender.sendMessage(colorf("&4/%s <player>", label));
            return true;
        }
        final Player target = Bukkit.getPlayer(args[0]);
        if (target == null) {
            sender.sendMessage(color("&4Player not found!"));
            return true;
        }
        final InventorySeeGUI gui = new InventorySeeGUI(Epsilon.getInstance(), (Player) sender, target);
        gui.init();
        gui.open();
        return true;
    }

}
