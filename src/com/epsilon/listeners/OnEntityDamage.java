package com.epsilon.listeners;

import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

public class OnEntityDamage implements Listener{

	@EventHandler
	public void onEntityDamageEntity(EntityDamageEvent e) {
		
		if(e.getEntityType() == EntityType.PLAYER && e.getCause() == DamageCause.FALL) {
			e.setCancelled(true);
		}
		
	}
	
}
