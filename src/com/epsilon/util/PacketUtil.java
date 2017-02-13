package com.epsilon.util;

import net.minecraft.server.v1_11_R1.PacketPlayOutTitle;
import net.minecraft.server.v1_11_R1.PacketPlayOutTitle.EnumTitleAction;
import net.minecraft.server.v1_11_R1.PlayerConnection;
import org.bukkit.craftbukkit.v1_11_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import static net.minecraft.server.v1_11_R1.IChatBaseComponent.ChatSerializer.a;

public class PacketUtil {

    /**
     * Send a title to a player.
     *
     * @param player the player to send the title to.
     * @param title the title to display, {@code null} if a title packet should not be sent
     * @param subtitle the subtitle to display, {@code null} if a subtitle packet should not be sent
     * @param fadeIn the fade-in time in ticks
     * @param stay the time the title should stay on the screen in ticks
     * @param fadeOut the fade-out time in ticks
     */
    public static void sendTitle(Player player, String title, String subtitle, int fadeIn, int stay, int fadeOut) {
        final PlayerConnection connection = ((CraftPlayer) player).getHandle().playerConnection;
        connection.sendPacket(new PacketPlayOutTitle(EnumTitleAction.TIMES, null, fadeIn, stay, fadeOut));
        if (title != null) {
            connection.sendPacket(new PacketPlayOutTitle(EnumTitleAction.TITLE, a(toJsonObject(title))));
        }
        if (subtitle != null) {
            connection.sendPacket(new PacketPlayOutTitle(EnumTitleAction.SUBTITLE, a(toJsonObject(subtitle))));
        }
    }

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
