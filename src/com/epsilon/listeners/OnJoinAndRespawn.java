package com.epsilon.listeners;

import com.epsilon.util.Const;
import com.epsilon.player.EPlayer;
import com.epsilon.util.LevelUtil;
import com.epsilon.util.MySQL;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.scoreboard.Team;
import static com.epsilon.util.ColorUtil.colorf;

public class OnJoinAndRespawn implements Listener {   

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        // FIXME Make this async... somehow
        Player player = e.getPlayer();
        EPlayer eplayer = EPlayer.getPlayer(player);
        
        Team t = Bukkit.getScoreboardManager().getMainScoreboard().registerNewTeam(("team"));
        t.setPrefix(colorf("&3[&6%s&3] &f", StringUtils.capitalize(eplayer.getRank().name().toLowerCase())));
        t.addEntry(player.getName());
        
        LevelUtil.setXPBar(player, MySQL.getLevel(player), MySQL.getProperty(player, "exp"));
        if (!player.hasPlayedBefore()) {           
            player.teleport(Const.SPAWN);
        }
    }
    
    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent e) {    
    	e.getPlayer().teleport(Const.SPAWN);
    }

}
