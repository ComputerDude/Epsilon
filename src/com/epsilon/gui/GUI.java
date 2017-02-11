package com.epsilon.gui;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

/**
 * This class represents a graphical user interface (GUI) which is shown to a player.
 *
 * @author null
 */
public abstract class GUI implements Listener {

    protected final Plugin plugin;
    protected final Player player;
    protected final Inventory inventory;
    private boolean eventsRegistered;

    protected GUI(final Plugin plugin, final Player player, final String name, final int slots) {
        this.plugin = plugin;
        this.player = player;
        inventory = Bukkit.getServer().createInventory(null, slots, name);
    }

    /**
     * This method sets up all the items in the inventory. It should be called
     * before using the {@code open()} method.
     */
    public abstract void init();

    /**
     * This method will be called whenever the player clicks in the inventory.
     * It will not be called when a different player clicks in an inventory, or
     * if the original player clicks in a different inventory. This method
     * should be overridden rather than creating another {@code EventHandler}.
     */
    public abstract boolean onClick(final InventoryClickEvent e);

    /**
     * This method will be called whenever the inventory is closed. If this
     * method returns {@code true}, the inventory will not be closed -- in other
     * words, it will immediately be reopened.
     */
    public boolean onClose() {
        return false;
    }

    /**
     * This method will be called when the the {@code open()} method is called,
     * <b>before the inventory is opened</b>. The rest of the {@code open()}
     * method will be cancelled if this method returns {@code true}. By default,
     * this method does nothing and simply returns {@code false}.
     */
    public boolean onOpen() {
        return false;
    }

    /**
     * Open up the inventory to the player. This method registers all events
     * within the class. The {@code init()} method should be called before to
     * set up the inventory.
     */
    public final void open() {
        if (onOpen()) return;
        player.openInventory(inventory);
        if (!eventsRegistered) Bukkit.getServer().getPluginManager().registerEvents(this, plugin);
        eventsRegistered = true;
    }

    /**
     * Close the inventory for the player. This method unregisters all events
     * within this class.
     */
    public final void close() {
        if (eventsRegistered) HandlerList.unregisterAll(this);
        eventsRegistered = false;
        player.closeInventory();
    }

    @EventHandler
    public final void onInventoryClose(final InventoryCloseEvent e) {
        if (e.getPlayer().getUniqueId().equals(player.getUniqueId())) {
            if (e.getInventory().hashCode() == inventory.hashCode()) {
                if (onClose()) {
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            GUI.this.open();
                        }
                    }.runTaskLater(plugin, 1);
                } else {
                    close();
                }
            }
        }
    }

    @EventHandler
    public final void onInventoryClick(final InventoryClickEvent e) {
        if (e.getWhoClicked().getUniqueId().equals(player.getUniqueId())) {
            if (e.getInventory().hashCode() == inventory.hashCode()) {
                if (onClick(e)) {
                    e.setCancelled(true);
                }
            }
        }
    }

}
