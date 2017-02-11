package com.computerdude.epsilon.player;

import com.computerdude.epsilon.util.LevelUtil;
import com.computerdude.epsilon.util.MySQL;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class Spawn implements Listener {

    public static final int[] SPAWN = {0, 90, 0};

    @EventHandler
    public static void onPlayerJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        LevelUtil.setXPBar(player, MySQL.getLevel(player), MySQL.getProperty(player, "exp"));
        if (!player.hasPlayedBefore()) {
            Location loc = new Location(e.getPlayer().getWorld(), SPAWN[0], SPAWN[1], SPAWN[2]);
            player.teleport(loc);
        }
    }

}
