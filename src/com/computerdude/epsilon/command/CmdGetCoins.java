package com.computerdude.epsilon.command;

import com.computerdude.epsilon.util.MySQL;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import static com.computerdude.epsilon.util.ColorUtil.color;
import static com.computerdude.epsilon.util.ColorUtil.colorf;

public class CmdGetCoins implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length > 1) {
            sender.sendMessage(colorf("&4/%s [player]", label));
            return true;
        }
        final Player target;
        if (args.length == 1) {
            target = Bukkit.getPlayer(args[0]);
            if (target == null) {
                sender.sendMessage(color("&4Player not found!"));
                return true;
            }
        } else {
            if (!(sender instanceof Player)) {
                sender.sendMessage(color("&4You must choose whose balance to get!"));
                return true;
            }
            target = (Player) sender;
        }
        sender.sendMessage(colorf("&a%s has %d coins.", target.getName(), MySQL.getCoins(target)));
        return false;
    }

}
