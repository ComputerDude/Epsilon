package com.epsilon.listeners;

import java.sql.PreparedStatement;

import com.epsilon.util.MySQL;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import static com.epsilon.util.ColorUtil.colorf;

/**
 * Hold listener for stuff OnPlayerLogin
 * @author JustBru00
 *
 */
public class OnPrePlayerLogin implements Listener {

	@EventHandler
    public void onPlayerPreLogin(AsyncPlayerPreLoginEvent e) {
        MySQL.openConnection();
        try {
            @SuppressWarnings("unused")
			String lastName = null;
            if (MySQL.playerDataContainsPlayer(e.getUniqueId())) {
                // lastName = getProperty(e.getPlayer(), "player");
                MySQL.setProperty(e.getUniqueId(), "player", e.getName());
            } else {
                PreparedStatement sql = MySQL.con.prepareStatement("INSERT INTO `player_data` VALUES(?, ?, DEFAULT, " +
                        "DEFAULT, DEFAULT);");
                sql.setString(1, e.getUniqueId().toString());
                sql.setString(2, e.getName());
                sql.execute();
                sql.close();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            e.disallow(AsyncPlayerPreLoginEvent.Result.KICK_OTHER, colorf("&cAn error occurred while loading your " +
                    "player data.\nPlease contact an administrator.\n&4%s: %s", ex.getClass().getName(), ex.getMessage()));
        } finally {
            MySQL.closeConnection();
        }
    }
	
}
