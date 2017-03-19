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
import com.epsilon.listeners.OnEntityDamage;
import com.epsilon.listeners.OnInteractEntity;
import com.epsilon.listeners.OnJoinAndRespawn;
import com.epsilon.listeners.OnPrePlayerLogin;
import com.epsilon.util.I18n;
import com.epsilon.util.MySQL;
import com.epsilon.util.PluginFile;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Main class of the plugin. (Extends {@link JavaPlugin})
 *
 * @author Multible Authors
 */
public class Epsilon extends JavaPlugin {

    public static PluginFile storageFile = null;
    public static PluginFile adminModeConfig = null;
    public static Epsilon instance;

    @Override
    public void onEnable() {
        long startTime = System.nanoTime();
        instance = this;
        storageFile = new PluginFile(this, "storage.yml", "storage.yml");
        adminModeConfig = new PluginFile(this, "admin-mode.yml");
        I18n.setConfig(new PluginFile(this, "i18n.yml", "i18n.yml"));
        getLogger().info("Registering listeners...");
        final PluginManager pm = Bukkit.getServer().getPluginManager();
        pm.registerEvents(new OnJoinAndRespawn(), this);
        pm.registerEvents(new PlayerChat(), this);
        pm.registerEvents(new OnPrePlayerLogin(), this);
        pm.registerEvents(new OnDeath(), this);
        pm.registerEvents(new OnInteractEntity(), this);
        pm.registerEvents(new OnEntityDamage(), this);
        getLogger().info("Registering commands...");
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
        getLogger().info("Initializing MySQL...");
        MySQL.createTables();
        getLogger().info(String.format("Epsilon has been enabled in %,.6fs.", (System.nanoTime() - startTime) / 1e9));
    }

    @Override
    public void onDisable() {
        long startTime = System.nanoTime();
        instance = null;
        getLogger().info("Disconnecting MySQL...");
        MySQL.disconnect();
        getLogger().info(String.format("Epsilon has been disabled in %,.6fs.", (System.nanoTime() - startTime) / 1e9));
    }

    public static Epsilon getInstance() {
        return instance;
    }
}
