package com.epsilon.listeners;

import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import com.epsilon.constants.EConstants;
import com.epsilon.util.ColorUtil;

/**
 * Class for death handling
 * @author ComputerDude
 *
 */
public class OnDeath implements Listener {

	@EventHandler
	public void onDeath(EntityDamageByEntityEvent e) {
		if(e.getEntityType() == EntityType.PLAYER) {
			Player player = (Player) e.getEntity();
			if(player.getHealth() <= e.getDamage()) {
				e.setCancelled(true);
				player.setHealth(20); // Change from depreciated method - JustBru00
				player.teleport(EConstants.SPAWN);
				player.sendTitle(ColorUtil.color("&c&lYou Died"), ColorUtil.color("&4You have been teleported to spawn!"), 20, 80, 20);
				player.getInventory().clear();
			}
			
		}
		
	}
	
}
