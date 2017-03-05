package com.epsilon.command;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import static com.epsilon.util.I18n.tlc;
import static com.epsilon.util.I18n.tlcf;

public class CmdGameMode implements CommandExecutor {

    private final GameMode mode;

    public CmdGameMode(GameMode gameMode) {
        mode = gameMode;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        final Player target;
        if (args.length > 0) {
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
        target.setGameMode(mode);
        sender.sendMessage(tlcf("command.gamemode.success", target.getName(), mode.name().toLowerCase()));
        return true;
    }

}
