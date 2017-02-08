package com.computerdude.epsilon.player;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.computerdude.epsilon.util.MySQL;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import static com.computerdude.epsilon.util.ColorUtil.colorf;

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

    private static final Map<Integer, Character> LEVEL_COLORS = new HashMap<Integer, Character>() {{
        put(0, '7');
        put(20, '7');
        put(40, 'e');
        put(60, '4');
        put(80, '5');
        put(100, '1');
        put(120, '9');
        put(140, 'b');
    }};

    public static void sendChatMessage(Entity en, String message, Player sender, int level) {
        final char color = LEVEL_COLORS.get((int) (Math.ceil(level / 20.0) * 20.00001));
        en.sendMessage(colorf("&" + color + "(%d) &f%s&7: &f%s", level, sender.getName(), message));
    }

}
