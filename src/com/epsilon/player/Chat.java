package com.epsilon.player;

import static com.epsilon.util.ColorUtil.colorf;

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

import com.epsilon.util.MySQL;

public class Chat implements Listener {

    public static List<Entity> getEntitiesAroundPoint(Location location, double radius) {
        Collection<Entity> col = location.getWorld().getNearbyEntities(location, radius, radius, radius);
        return new ArrayList<>(col);
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent e) {

        Player sender = e.getPlayer();

        List<Entity> ent = getEntitiesAroundPoint(sender.getLocation(), 50);

        for (Entity en : ent) {
            if (en.getType() == EntityType.PLAYER) sendChatMessage(en, e.getMessage(), sender, MySQL.getLevel(sender));
        }
        e.setCancelled(true);

    }

    private static final char[] COLORS = {'7', 'e', '4', '5', '1', '9', 'b'};

    public static void sendChatMessage(Entity en, String message, Player sender, int level) {
        final char color;
        if (level / 20 >= COLORS.length) color = COLORS[COLORS.length - 1];
        else color = COLORS[level / 20];
        en.sendMessage(colorf("&" + color + "(%d) &f%s&7: &f%s", level, sender.getName(), message));
    }

}
