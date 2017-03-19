package com.epsilon.command;

import com.epsilon.Epsilon;
import com.epsilon.util.LevelUtil;
import com.epsilon.util.MySQL;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import static com.epsilon.util.ColorUtil.color;
import static com.epsilon.util.ColorUtil.colorf;

/**
 * The {@code /setlevel} command sets your current level.
 */
public class CmdSetLevel implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length == 0 || args.length > 3) {
            sender.sendMessage(colorf("&4/%s <level> [xp] [player]", label));
            return true;
        }
        final int level;
        final long xp;
        final Player target;
        try {
            level = Integer.parseInt(args[0]);
            if (args.length > 1) xp = Long.parseLong(args[1]);
            else xp = 0;
        } catch (NumberFormatException e) {
            sender.sendMessage(color("&4That is not a number!"));
            return true;
        }
        if (args.length > 2) {
            target = Bukkit.getPlayer(args[2]);
            if (target == null) {
                sender.sendMessage(color("&4Player not found!"));
                return true;
            }
        } else {
            if (!(sender instanceof Player)) {
                sender.sendMessage("&4You must choose whose level to change!");
                return true;
            }
            target = (Player) sender;
        }
        new BukkitRunnable() {
            @Override
            public void run() {
                MySQL.setProperty(target, "level", level);
                MySQL.setProperty(target, "xp", xp);
                new BukkitRunnable(){
                    @Override
                    public void run() {
                        LevelUtil.setXPBar(target, level, xp);
                        sender.sendMessage(colorf("&aChanged %s's level to %d with %,d experience.",
                                target.getName(), level, xp));
                    }
                }.runTask(Epsilon.getInstance());
            }
        }.runTaskAsynchronously(Epsilon.getInstance());
        return true;
    }
}
