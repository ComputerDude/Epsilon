package com.computerdude.epsilon.game;

import com.computerdude.epsilon.util.LevelUtil;
import com.computerdude.epsilon.util.MySQL;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class Spawn implements Listener {

    @EventHandler
    public static void onPlayerJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        LevelUtil.setXPBar(player, MySQL.getLevel(player), 0 /* FIXME Add XP tracking */);
        if (!player.hasPlayedBefore()) {
            double xz = 0;
            double y = 90;

            Location loc = new Location(e.getPlayer().getWorld(), xz, y, xz);
            player.teleport(loc);
        }
    }

}
