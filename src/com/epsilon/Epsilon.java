package com.epsilon;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.epsilon.command.CmdGetCoins;
import com.epsilon.command.CmdGetLevel;
import com.epsilon.command.CmdSetCoins;
import com.epsilon.command.CmdSetLevel;
import com.epsilon.player.Chat;
import com.epsilon.player.SpawnHandler;
import com.epsilon.util.MySQL;
import com.epsilon.util.PluginFile;

/**
 * Main class of the plugin. (Extends {@link JavaPlugin}) 
 * @author Multible Authors
 *
 */
public class Epsilon extends JavaPlugin {

    public static PluginFile storageFile = null;
    public static Epsilon plugin;

    @Override
    public void onEnable() {
    	plugin = this;
        storageFile = new PluginFile(this, "storage.yml", "storage.yml");
        final PluginManager pm = Bukkit.getServer().getPluginManager();
        
        pm.registerEvents(new SpawnHandler(), this);
        pm.registerEvents(new Chat(), this);
        pm.registerEvents(new MySQL(), this);

        getCommand("setlevel").setExecutor(new CmdSetLevel());
        getCommand("getlevel").setExecutor(new CmdGetLevel());
        getCommand("setcoins").setExecutor(new CmdSetCoins());
        getCommand("getcoins").setExecutor(new CmdGetCoins());
        getCommand("adminmode").setExecutor(new CmdGetCoins());

        MySQL.createTables();
    }

    @Override
    public void onDisable() {
        MySQL.disconnect();
        plugin = null;
    }
    
    public Epsilon getInstance() {
    	return plugin;
    }
}
