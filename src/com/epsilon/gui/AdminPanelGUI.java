package com.epsilon.gui;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.plugin.Plugin;

public class AdminPanelGUI extends GUI {

    public AdminPanelGUI(Plugin plugin, Player player) {
        super(plugin, player, "Admin Panel", 54);
    }

    protected AdminPanelGUI(Plugin plugin, Player player, String name, int slots) {
        super(plugin, player, name, slots);
    }

    @Override
    public void init() {
        // I don't know what you guys want to put in here but it goes here.
    }

    @Override
    public boolean onClick(InventoryClickEvent e) {
        return false;
    }

}
