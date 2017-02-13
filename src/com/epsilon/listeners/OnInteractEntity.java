package com.epsilon.listeners;

import com.epsilon.Epsilon;
import com.epsilon.administration.AdminModeManager;
import com.epsilon.gui.InventorySeeGUI;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;

public class OnInteractEntity implements Listener {

    @EventHandler
    public void onInteractEntity(PlayerInteractEntityEvent e) {
        if (e.getRightClicked() instanceof Player) {
            if (AdminModeManager.isAdminModeEnabled(e.getPlayer())) {
                final InventorySeeGUI gui = new InventorySeeGUI(Epsilon.getInstance(), e.getPlayer(),
                        (Player) e.getRightClicked());
                gui.init();
                gui.open();
            }
        }
    }

}
