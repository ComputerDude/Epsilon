package com.computerdude.epsilon.command;

import com.computerdude.epsilon.util.MySQL;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import static com.computerdude.epsilon.util.ColorUtil.color;
import static com.computerdude.epsilon.util.ColorUtil.colorf;

/**
 * The {@code /getlevel} command gets your (or someone else's) current level.
 */
public class CmdGetLevel implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length > 1) {
            sender.sendMessage(colorf("&4/%s <level> [player]", label));
            return true;
        }
        final Player target;
        if (args.length == 1) {
            target = Bukkit.getPlayer(args[1]);
            if (target == null) {
                sender.sendMessage(color("&4Player not found!"));
                return true;
            }
        } else {
            if (!(sender instanceof Player)) {
                sender.sendMessage("&4You must choose whose level to get!");
                return true;
            }
            target = (Player) sender;
        }
        sender.sendMessage(colorf("&a%s is level %d.", target.getName(), MySQL.getLevel(target)));
        return true;
    }

}
