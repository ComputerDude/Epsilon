package com.epsilon.mob;

import org.bukkit.Location;
import org.bukkit.entity.EntityType;

/**
 * Will probably be removed in production!!!
 */
public class SimpleMob extends Mob {

    private final String name;
    private final double xpMultiplier;
    private final int maxHealth;
    private final double minDamage, maxDamage;

    public SimpleMob(EntityType type, Location location, String name, double xpMultiplier, int maxHealth, double
            minDamage, double maxDamage, MobDrop... drops) {
        super(type, location, drops);
        this.name = name;
        this.xpMultiplier = xpMultiplier;
        this.maxHealth = maxHealth;
        this.minDamage = minDamage;
        this.maxDamage = maxDamage;
    }

    public SimpleMob(EntityType type, Location location, int radius, String name, double xpMultiplier, int maxHealth,
                     double minDamage, double maxDamage, MobDrop... drops) {
        super(type, location, radius, drops);
        this.name = name;
        this.xpMultiplier = xpMultiplier;
        this.maxHealth = maxHealth;
        this.minDamage = minDamage;
        this.maxDamage = maxDamage;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public double getXPMultiplier() {
        return xpMultiplier;
    }

    @Override
    public int getMaxHealth() {
        return maxHealth;
    }

    @Override
    public double getMinDamage() {
        return minDamage;
    }

    @Override
    public double getMaxDamage() {
        return maxDamage;
    }

}
