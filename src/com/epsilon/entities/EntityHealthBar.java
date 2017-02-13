package com.epsilon.entities;

import static com.epsilon.util.ColorUtil.color;
import static com.epsilon.util.ColorUtil.colorf;

public class EntityHealthBar {

    /**
     * For formating the health bar.
     *
     * @param health The health of the entity
     * @param maxHealth The max health of the entity.
     */
    private static String formatBar(int health, int maxHealth) {
        // Consult null if you would like to modify any part of this method.
        // He will adjust the algorithm if necessary.
        final double fraction = (double) health / maxHealth;
        final int healthLen = Integer.toString(health).length();
        String bar = colorf("&8[&a|||||%d|||||&8]", health);
        final int pos = 5 + (fraction >= 1 ? healthLen + 10 : (int) (fraction * (healthLen + 11)));
        return bar.substring(0, pos) + color("&c") + (bar.substring(pos));
    }

}
