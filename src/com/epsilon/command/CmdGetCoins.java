package com.epsilon.command;

import com.epsilon.Epsilon;
import com.epsilon.util.MySQL;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import static com.epsilon.util.I18n.tlc;
import static com.epsilon.util.I18n.tlcf;

public class CmdGetCoins implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length > 1) {
            sender.sendMessage(tlcf("command.getcoins.syntax", label));
            return true;
        }
        final Player target;
        if (args.length == 1) {
            target = Bukkit.getPlayer(args[0]);
            if (target == null) {
                sender.sendMessage(tlc("command.player-not-found"));
                return true;
            }
        } else {
            if (!(sender instanceof Player)) {
                sender.sendMessage(tlc("command.player-only"));
                return true;
            }
            target = (Player) sender;
        }
        new BukkitRunnable() {
            @Override
            public void run() {
                final long coins = MySQL.getCoins(target);
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        sender.sendMessage(tlcf("command.getcoins.success", target.getName(), coins));
                    }
                }.runTask(Epsilon.getInstance());
            }
        }.runTaskAsynchronously(Epsilon.getInstance());
        return true;
    }

}
