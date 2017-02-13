package com.epsilon.util;

import net.minecraft.server.v1_11_R1.PacketPlayOutTitle;
import net.minecraft.server.v1_11_R1.PacketPlayOutTitle.EnumTitleAction;
import org.bukkit.craftbukkit.v1_11_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import static net.minecraft.server.v1_11_R1.IChatBaseComponent.ChatSerializer.a;

public class PacketUtil {

    /**
     * Send an action bar message to the player.
     *
     * @param player the player to send the actionbar to.
     * @param text the new text in the action bar.
     */
    public static void sendActionBar(Player player, String text) {
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(new PacketPlayOutTitle(EnumTitleAction
                .ACTIONBAR, a(toJsonObject(text))));
    }

    /**
     * Turn the argument into a JSON object, in the form {"text":"&lt;argument&gt;"}.
     */
    public static String toJsonObject(String s) {
        return "{\"text\":\"" + s.replace("\\", "\\\\").replace("\"", "\\\"") + "\"}";
    }

}
