package com.computerdude.epsilon.player;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;

public class SecondHand implements Listener {

    @EventHandler
    public void onPlayerHandSwap(PlayerSwapHandItemsEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        if (e.getInventory().getType() == InventoryType.CRAFTING && e.getSlot() == 40) {
            e.setCancelled(true);
        }
    }

}
