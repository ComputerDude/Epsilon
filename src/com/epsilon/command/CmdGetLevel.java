package com.epsilon.command;

import static com.epsilon.util.ColorUtil.color;
import static com.epsilon.util.ColorUtil.colorf;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.epsilon.util.LevelUtil;
import com.epsilon.util.MySQL;

/**
 * The {@code /getlevel} command gets your (or someone else's) current level.
 */
public class CmdGetLevel implements CommandExecutor {

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
                sender.sendMessage(color("&4You must choose whose level to get!"));
                return true;
            }
            target = (Player) sender;
        }
        final int level = MySQL.getLevel(target);
        final long exp = MySQL.getProperty(target, "exp");
        sender.sendMessage(colorf("&a%s is level %d with %,d/%,d (%.3f%%) experience.", target.getName(), level, exp,
                LevelUtil.getXP(level), 100.0 * exp / LevelUtil.getXP(level)));
        return true;
    }

}