package com.epsilon.mob;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

/**
 * This class represents a custom mob in the game.
 */
public abstract class Mob {

    protected final Entity entity;

    protected Mob(EntityType type, Location loc) {
        entity = loc.getWorld().spawnEntity(loc, type);
    }

    public Entity getEntity() { return entity; }

}
