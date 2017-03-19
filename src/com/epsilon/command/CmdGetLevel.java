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
        new BukkitRunnable() {
            @Override
            public void run() {
                final int level = MySQL.getProperty(target, "level");
                final long xp = MySQL.getProperty(target, "xp");
                final long totalxp = LevelUtil.getXP(level);
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        sender.sendMessage(colorf("&a%s is level %d with %,d/%,d (%.3f%%) experience.",
                                target.getName(), level, xp, totalxp, 100.0 * xp / totalxp));
                    }
                }.runTask(Epsilon.getInstance());
            }
        }.runTaskAsynchronously(Epsilon.getInstance());
        return true;
    }

}
