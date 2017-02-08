package com.computerdude.epsilon.command;

import com.computerdude.epsilon.util.MySQL;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import static com.computerdude.epsilon.Main.color;

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
        if (args.length != 1) {
            player.sendMessage(color("&4/setlevel <level>"));
        } else {
            final int amount;
            try {
                amount = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                sender.sendMessage(color("&4That is not a number!"));
                return true;
            }
            MySQL.setLevel(player, amount);
            player.sendMessage(color("Changed level to " + args[0]));
        }
        return true;
    }
}
