package com.epsilon.listeners;

import com.epsilon.util.Const;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import static com.epsilon.util.I18n.tlc;

/**
 * Class for death handling
 *
 * @author ComputerDude
 */
public class OnDeath implements Listener {

    @EventHandler
    public void onDeath(EntityDamageByEntityEvent e) {
        if (e.getEntityType() == EntityType.PLAYER) {
            Player player = (Player) e.getEntity();
            if (player.getHealth() <= e.getDamage()) {
                e.setCancelled(true);
                player.setHealth(20);
                player.teleport(Const.SPAWN);
                player.sendTitle(tlc("player.you-died"), tlc("player.teleported-to-spawn"), 20, 80, 20);
                player.getInventory().clear();
            }
        }
    }

}
