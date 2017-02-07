package com.computerdude.epsilon.player;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import com.computerdude.epsilon.Main;
import com.computerdude.epsilon.util.MySQL;

public class Chat implements Listener {

	public static List<Entity> getEntitiesAroundPoint(Location location, double radius) {
	    Collection<Entity> col = location.getWorld().getNearbyEntities(location, radius, radius, radius);
	    ArrayList<Entity> list = new ArrayList<Entity>(col);
	    return list;
	}
	
	@EventHandler
	public void onPlayerChat(AsyncPlayerChatEvent e) {
		
		Player sender = e.getPlayer();

		List<Entity> ent = getEntitiesAroundPoint(sender.getLocation(), 50);
		
		for (Entity en : ent) {
			if (en.getType() == EntityType.PLAYER) {
				
				Main.sendChatMessage(en, e.getMessage(), sender, MySQL.getLevel(sender));
				
			}
		}
		e.setCancelled(true);
		
	}

}
