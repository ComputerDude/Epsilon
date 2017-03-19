package com.epsilon.listeners;

import com.epsilon.player.EPlayer;
import com.epsilon.util.Const;
import com.epsilon.util.LevelUtil;
import com.epsilon.util.MySQL;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.scoreboard.Team;
import static com.epsilon.util.I18n.tlcf;

public class OnJoinAndRespawn implements Listener {   

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        EPlayer eplayer = EPlayer.getPlayer(player);

        // FIXME Incorrect name tag due to asynchronous initialization
        // The rank field in EPlayer (accessed via getRank()) is initialized in a separate thread, because it must
        // query MySQL to do so. However, this piece of code tries to access the player's rank immediately after
        // getting the EPlayer instance, which means that the rank is not yet initialized. Before it is initialized,
        // it is defaulted to Rank.DEFAULT, thus players' name tags will always show that they are default rank when
        // they join.

        Team t = Bukkit.getScoreboardManager().getNewScoreboard().registerNewTeam(e.getPlayer().getUniqueId()
                .toString().replace("-", "").substring(0, 16)); // Team name = first 16 chars of uuid without dashes
        t.setPrefix(tlcf("player.name-tag-prefix", eplayer.getRank().getName()));
        t.addEntry(player.getName());
        
        LevelUtil.setXPBar(player, MySQL.getLevel(player), MySQL.getProperty(player, "xp"));
        if (!player.hasPlayedBefore()) {
            player.teleport(Const.SPAWN);
        }
    }
    
    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent e) {    
    	e.getPlayer().teleport(Const.SPAWN);
    }

}
