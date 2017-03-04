package com.epsilon.util;

import org.bukkit.configuration.file.YamlConfiguration;
import static com.epsilon.util.ColorUtil.color;
import static com.epsilon.util.ColorUtil.colorf;

public class I18n {

    private static YamlConfiguration config;

    public static void setConfig(YamlConfiguration config) {
        I18n.config = config;
    }

    public static YamlConfiguration getConfig() {
        return config;
    }

    /**
     * <b>T</b>rans<b>l</b>ate a string.
     */
    public static String tl(String key) {
        return config.getString(key, key);
    }

    /**
     * <b>T</b>rans<b>l</b>ate and <b>c</b>olor a string.
     */
    public static String tlc(String key) {
        return color(config.getString(key, key));
    }

    /**
     * <b>T</b>rans<b>l</b>ate and <b>f</b>ormat a string.
     */
    public static String tlf(String key, Object... args) {
        return String.format(config.getString(key, key), args);
    }

    /**
     * <b>T</b>rans<b>l</b>ate, <b>c</b>olor, and <b>f</b>ormat a string.
     */
    public static String tlcf(String key, Object... args) {
        return colorf(config.getString(key, key), args);
    }

}
