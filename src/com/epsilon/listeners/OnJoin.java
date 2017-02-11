package com.epsilon.listeners;

import java.sql.PreparedStatement;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

import com.epsilon.util.MySQL;

/**
 * Hold listener for stuff OnPlayerLogin
 * @author JustBru00
 *
 */
public class OnJoin implements Listener {

	@EventHandler
    public void onPlayerLogin(PlayerLoginEvent e) {
		//FIXME Make all database queries async
        MySQL.openConnection();
        try {
            @SuppressWarnings("unused")
			String lastName = null;
            if (MySQL.playerDataContainsPlayer(e.getPlayer())) {
                // lastName = getProperty(e.getPlayer(), "player");
                MySQL.setProperty(e.getPlayer(), "player", e.getPlayer().getName());
            } else {
                PreparedStatement sql = MySQL.con.prepareStatement("INSERT INTO `player_data` VALUES(?, ?, DEFAULT, " +
                        "DEFAULT, DEFAULT);");
                sql.setString(1, e.getPlayer().getUniqueId().toString());
                sql.setString(2, e.getPlayer().getName());
                sql.execute();
                sql.close();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            MySQL.closeConnection();
        }
    }
	
}
