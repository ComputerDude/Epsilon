package com.epsilon.command;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import static com.epsilon.util.ColorUtil.color;
import static com.epsilon.util.ColorUtil.colorf;

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
                sender.sendMessage(color("&4Player not found!"));
                return true;
            }
        } else {
            if (!(sender instanceof Player)) {
                sender.sendMessage(color("&4You must choose whose game mode to change!"));
                return true;
            }
            target = (Player) sender;
        }
        target.setGameMode(mode);
        sender.sendMessage(colorf("&aSet %s's game mode to %s.", target.getName(), mode.name().toLowerCase()));
        return true;
    }

}
