package com.epsilon.mob;

import java.util.Random;

import com.epsilon.player.EPlayer;
import org.bukkit.Location;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

/**
 * This class represents a custom mob in the game.
 */
public abstract class Mob {

    private static final Random RAND = new Random();

    protected final Entity entity;
    private int health;

    protected Mob(EntityType type, Location loc) {
        entity = loc.getWorld().spawnEntity(loc, type);
        health = getMaxHealth();
    }

    /**
     * Get the {@link Entity} associated with this mob.
     */
    public Entity getEntity() { return entity; }

    /**
     * Get the maximum amount of health the mob can have.
     */
    public abstract int getMaxHealth();

    /**
     * Get how much health the mob has left.
     */
    public int getHealth() {
        return health;
    }

    /**
     * Set the amount of health this mob has left.
     */
    public void setHealth(int health) {
        this.health = health;
        if (health <= 0) kill();
    }

    /**
     * Damage this mob.
     * @param damage the amount of damage to deal.
     */
    public void damage(int damage) {
        health -= damage;
        if (health <= 0) kill();
    }

    /**
     * Kill this mob.
     */
    public void kill() {
        health = 0;
        if (entity instanceof Damageable) {
            ((Damageable) entity).setHealth(0);
        } else {
            entity.remove();
        }
    }

    /**
     * @return {@code true} if the mob is alive (has more than 0 health); {@code false} otherwise.
     */
    public boolean isAlive() {
        return health > 0;
    }

    /**
     * @return {@code true} if the mob is dead (has 0 health); {@code false} otherwise.
     */
    public boolean isDead() {
        return health <= 0;
    }

    /**
     * Get the minimum amount of damage this mob can do, in health points, before armor calculations.
     */
    public abstract double getMinDamage();

    /**
     * Get the maximum amount of damage this mob can do, in health points, before armor calculations.
     */
    public abstract double getMaxDamage();

    /**
     * Cause this mob to damage a player. If this mob cannot damage a player, this method does nothing.
     * @param target the player to damage.
     */
    public void damagePlayer(EPlayer target) {
        if (getMaxDamage() <= 0) return;
        final double damage = RAND.nextDouble() * (getMaxDamage() - getMinDamage()) + getMinDamage();
        target.getOnlinePlayer().damage(damage, entity);
    }

}
