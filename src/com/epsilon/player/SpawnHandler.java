package com.epsilon.player;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

import com.epsilon.util.LevelUtil;
import com.epsilon.util.MySQL;

public class SpawnHandler implements Listener {

    public static final int[] SPAWN = {0, 90, 0};

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        LevelUtil.setXPBar(player, MySQL.getLevel(player), MySQL.getProperty(player, "exp"));
        if (!player.hasPlayedBefore()) {
            Location loc = new Location(e.getPlayer().getWorld(), SPAWN[0], SPAWN[1], SPAWN[2]);
            player.teleport(loc);
        }
    }
    
    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent e) {
    	Location loc = new Location(e.getPlayer().getWorld(), SPAWN[0], SPAWN[1], SPAWN[2]);
    	e.getPlayer().teleport(loc);
    }

}
