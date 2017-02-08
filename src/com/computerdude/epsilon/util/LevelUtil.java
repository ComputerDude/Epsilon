package com.computerdude.epsilon.util;

import java.util.stream.IntStream;

import org.bukkit.entity.Player;

public class LevelUtil {

    /** @return {@code base} to the power of {@code exp}, rounded to the nearest integer */
    private static int pow(double base, double exp) {
        return (int) Math.round(Math.pow(base, exp));
    }

    /** Return the amount of XP required to level up from level {@code (level)} to level {@code (level + 1)}. */
    public static int getXP(int level) {
        if (level <= 10) return 100 * pow(1.3, level);
        if (level <= 25) return 1_379 * pow(1.2, level - 10);
        return 21_246 * pow(1.1, level - 25);
    }

    /** Return the amount of XP required to get from level 1 to level {@code level}. */
    public static int getTotalXP(int level) {
        return IntStream.range(1, level).map(LevelUtil::getXP).sum();
    }

    /**
     * Set the player's XP bar.
     * @param player the target player.
     * @param level the player's current level.
     * @param xp the player's experience for the current level.
     */
    public static void setXPBar(Player player, int level, int xp) {
        player.setLevel(level);
        player.setExp((float) xp / getXP(level));
    }

}
