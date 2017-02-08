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
 * The {@code /setlevel} command sets your current level.
 */
public class CmdSetLevel implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(color("&4Only in-game players can do that."));
            return true;
        }
        Player player = (Player) sender;
        if (args.length == 0 || args.length > 2) {
            player.sendMessage(color("&4/setlevel <level> [player]"));
            return true;
        }
        final int amount;
        final Player target;
        try {
            amount = Integer.parseInt(args[0]);
        } catch (NumberFormatException e) {
            sender.sendMessage(color("&4That is not a number!"));
            return true;
        }
        if (args.length >= 2) {
            target = Bukkit.getPlayer(args[1]);
            if (target == null) {
                sender.sendMessage(color("&4Player not found!"));
                return true;
            }
        } else {
            target = player;
        }
        MySQL.setLevel(target, amount);
        player.sendMessage(colorf("&aChanged %s's level to %s.", target.getName(), args[0]));
        return true;
    }
}
