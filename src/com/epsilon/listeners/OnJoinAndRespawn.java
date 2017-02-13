package com.epsilon.listeners;

import org.apache.commons.lang3.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.scoreboard.Team;

import com.epsilon.constants.EConstants;
import com.epsilon.player.EPlayer;
import com.epsilon.util.ColorUtil;
import com.epsilon.util.LevelUtil;
import com.epsilon.util.MySQL;

public class OnJoinAndRespawn implements Listener {   

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        // FIXME Make this async... somehow
        Player player = e.getPlayer();
        EPlayer eplayer = EPlayer.getPlayer(player);
        
        Team t = Bukkit.getScoreboardManager().getMainScoreboard().registerNewTeam(("team"));
        t.setPrefix(ColorUtil.color("&3[&6" + StringUtils.capitalize(eplayer.getRank().name().toLowerCase()) + "&3] &f"));
        t.addEntry(player.getName());
        
        LevelUtil.setXPBar(player, MySQL.getLevel(player), MySQL.getProperty(player, "exp"));
        if (!player.hasPlayedBefore()) {           
            player.teleport(EConstants.SPAWN);
        }
    }
    
    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent e) {    
    	e.getPlayer().teleport(EConstants.SPAWN);
    }

}
