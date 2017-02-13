package com.epsilon;

import com.epsilon.chat.PlayerChat;
import com.epsilon.command.CmdAdminMode;
import com.epsilon.command.CmdGameMode;
import com.epsilon.command.CmdGetCoins;
import com.epsilon.command.CmdGetLevel;
import com.epsilon.command.CmdInventorySee;
import com.epsilon.command.CmdSetCoins;
import com.epsilon.command.CmdSetLevel;
import com.epsilon.listeners.OnDeath;
import com.epsilon.listeners.OnJoinAndRespawn;
import com.epsilon.listeners.OnPrePlayerLogin;
import com.epsilon.util.MySQL;
import com.epsilon.util.PluginFile;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Main class of the plugin. (Extends {@link JavaPlugin}) 
 * @author Multible Authors
 */
public class Epsilon extends JavaPlugin {

    public static PluginFile storageFile = null;
    public static PluginFile adminModeConfig = null;
    public static Epsilon plugin;

    @Override
    public void onEnable() {
    	plugin = this;
        storageFile = new PluginFile(this, "storage.yml", "storage.yml");
        adminModeConfig = new PluginFile(this, "admin-mode.yml");
        final PluginManager pm = Bukkit.getServer().getPluginManager();
        
        pm.registerEvents(new OnJoinAndRespawn(), this);
        pm.registerEvents(new PlayerChat(), this);
        pm.registerEvents(new OnPrePlayerLogin(), this);
        pm.registerEvents(new OnDeath(), plugin);

        getCommand("setlevel").setExecutor(new CmdSetLevel());
        getCommand("getlevel").setExecutor(new CmdGetLevel());
        getCommand("setcoins").setExecutor(new CmdSetCoins());
        getCommand("getcoins").setExecutor(new CmdGetCoins());
        getCommand("adminmode").setExecutor(new CmdAdminMode());
        getCommand("inventorysee").setExecutor(new CmdInventorySee());
        getCommand("gmc").setExecutor(new CmdGameMode(GameMode.CREATIVE));
        getCommand("gms").setExecutor(new CmdGameMode(GameMode.SURVIVAL));
        getCommand("gma").setExecutor(new CmdGameMode(GameMode.ADVENTURE));
        getCommand("gmsp").setExecutor(new CmdGameMode(GameMode.SPECTATOR));
        
        MySQL.createTables();
        getLogger().info("Epsilon has been enabled.");
    }

    @Override
    public void onDisable() {
        MySQL.disconnect();
        plugin = null;
    }
    
    public static Epsilon getInstance() {
    	return plugin;
    }
}
