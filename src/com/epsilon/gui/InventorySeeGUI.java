package com.epsilon.gui;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import static com.epsilon.util.ColorUtil.color;
import static com.epsilon.util.ItemUtil.createItem;

public class InventorySeeGUI extends GUI {

    private final Player target;

    public InventorySeeGUI(Plugin plugin, Player player, Player target) {
        super(plugin, player, "Inventory of " + target.getName(), 45);
        this.target = target;
    }

    protected InventorySeeGUI(Plugin plugin, Player player, String name, int slots) {
        super(plugin, player, name, slots);
        target = null;
    }

    @Override
    public void init() {
        final ItemStack[] items = new ItemStack[45];
        System.arraycopy(target.getInventory().getContents(), 0, items, 0, 36);
        System.arraycopy(target.getInventory().getArmorContents(), 0, items, 36, 4);
        items[40] = target.getInventory().getItemInOffHand();
        items[44] = createItem(Material.BOOK, 1, (short) 0, color("&bRefresh"));
        inventory.setContents(items);
    }

    @Override
    public boolean onClick(InventoryClickEvent e) {
        if (e.getRawSlot() == 44) new BukkitRunnable() {
            @Override
            public void run() {
                init();
            }
        }.runTaskLater(plugin, 1);
        return true;
    }

}
