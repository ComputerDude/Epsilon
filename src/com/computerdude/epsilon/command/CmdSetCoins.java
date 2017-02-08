package com.computerdude.epsilon.command;

import com.computerdude.epsilon.util.MySQL;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import static com.computerdude.epsilon.util.ColorUtil.color;
import static com.computerdude.epsilon.util.ColorUtil.colorf;

public class CmdSetCoins implements CommandExecutor {
    
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length == 0 || args.length > 2) {
            sender.sendMessage(colorf("&4/%s <coins> [player]", label));
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
            if (!(sender instanceof Player)) {
                sender.sendMessage("&4You must choose whose balance to change!");
                return true;
            }
            target = (Player) sender;
        }
        MySQL.setCoins(target, amount);
        sender.sendMessage(colorf("&aChanged %s's balance to %d.", target.getName(), amount));
        return true;
    }
    
}
