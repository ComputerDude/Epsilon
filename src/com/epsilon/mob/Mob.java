package com.epsilon.mob;

import java.util.Random;

import com.epsilon.player.EPlayer;
import com.epsilon.util.LevelUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import static com.epsilon.util.ColorUtil.colorf;
import static com.epsilon.util.RandomUtil.randDoubleTriangular;

/**
 * This class represents a custom mob in the game.
 */
public abstract class Mob {

    private static final Random RAND = new Random();

    protected final Entity entity;
    private int health;

    private final MobDrop[] drops;

    /**
     * Create the mob at the location specified.
     */
    protected Mob(EntityType type, Location loc, MobDrop... drops) {
        this.drops = drops;
        entity = loc.getWorld().spawnEntity(loc.getWorld().getHighestBlockAt(loc).getLocation(), type);
        health = getMaxHealth();
    }

    /**
     * Create the mob in a random position within a box around the radius specified.
     */
    protected Mob(EntityType type, Location loc, int radius, MobDrop... drops) {
        findSpawnLocation:
        {
            for (int i = 0; i < 256; i++) {
                final Location newLoc = loc.getWorld().getHighestBlockAt(new Location(
                        loc.getWorld(),
                        loc.getX() + RAND.nextInt(radius * 2 + 1) - radius,
                        loc.getY(),
                        loc.getZ() + RAND.nextInt(radius * 2 + 1) - radius
                )).getLocation();
                if (newLoc.getBlockY() > 1) {
                    loc = newLoc;
                    break findSpawnLocation; // Who said there was no 'goto' in Java?
                }
            }
            Bukkit.getConsoleSender().sendMessage(colorf(
                    "&eFailed to spawn a %s mob within %d blocks of (%d, %d, %d) within 256 tries",
                    getClass().getName(), radius, loc.getBlockX(), loc.getBlockY(), loc.getBlockZ()));
        }
        this.drops = drops;
        entity = loc.getWorld().spawnEntity(loc, type);
        health = getMaxHealth();
    }

    /**
     * Get the name of the mob, shown as the name tag above it.
     */
    public abstract String getName();

    /**
     * Get the XP multiplier for this mob. 1.0 means normal XP.
     */
    public abstract double getXPMultiplier();

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
        if (health <= 0) kill(null);
    }

    /**
     * Damage this mob.
     * @param damage the amount of damage to deal.
     */
    public void damage(int damage, EPlayer damager) {
        health -= damage;
        if (health <= 0) kill(damager);
    }

    /**
     * Kill this mob.
     */
    public void kill(EPlayer killer) {
        if (isDead()) return;
        health = 0;
        if (entity instanceof Damageable) {
            ((Damageable) entity).setHealth(0);
        } else {
            entity.remove();
        }
        if (killer != null) {
            // TODO Change this algorithm
            // Changed, but still needs changing.
            final long toLevelUp = LevelUtil.getTotalXP(killer.getLevel());
            killer.addXP((long) (toLevelUp / 20.0 / Math.sqrt(killer.getLevel()) * getXPMultiplier()));
        }
        for (MobDrop drop : drops) {
            for (int i = 0, amount = drop.getDroppedAmount(); i < amount; i++) {
                entity.getLocation().getWorld().dropItemNaturally(entity.getLocation(), drop.getItem());
            }
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
        final double damage = randDoubleTriangular(getMinDamage(), getMaxDamage());
        target.getOnlinePlayer().damage(damage, entity);
    }

}
