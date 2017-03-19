package com.epsilon.mob;

import com.epsilon.administration.AdminModeManager;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

public class MobSpawner extends BukkitRunnable {

    private final World world;

    public MobSpawner(World targetWorld) {
        this.world = targetWorld;
    }

    public void register(Plugin plugin) {
        runTaskTimer(plugin, 5, 5); // TODO This may need adjustment
    }

    @Override
    public void run() {
        for (Player p : world.getPlayers()) {
            if (AdminModeManager.isAdminModeEnabled(p)) continue;
            // Spawn test mob.
            final Mob mob = new SimpleMob(EntityType.ZOMBIE, p.getLocation(), "Test Mob", 1, 50, 5, 10, new
                    MobDrop(new ItemStack(Material.FEATHER), 0.7, 3, 3));
        }
    }

}
