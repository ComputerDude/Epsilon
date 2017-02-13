package com.epsilon.listeners;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import com.epsilon.Epsilon;

/**
 * Class for death handling
 * @author JustBru00
 *
 */
public class OnDeath implements Listener {

	@EventHandler
	public void onDeath(PlayerDeathEvent e) {
		
		final Player p = e.getEntity();
		
		// Respawn player after 3 ticks (One or two ticks will not work sometimes.)
		Bukkit.getScheduler().scheduleSyncDelayedTask(Epsilon.getInstance(), new Runnable() {
			
			@Override
			public void run() {
				p.spigot().respawn(); 				
			}
		}, 3);
		
	}
	
}
