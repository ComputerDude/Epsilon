package com.epsilon.util;

import org.bukkit.ChatColor;

public class ColorUtil {

    /**
     * Colorize a message using the '&' character.
     * @see #colorf(String, Object...)
     */
    public static String color(String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }

    /**
     * Colorize and format a message.
     * @see #color(String)
     */
    public static String colorf(String text, Object... args) {
        return String.format(ChatColor.translateAlternateColorCodes('&', text), args);
    }

}
