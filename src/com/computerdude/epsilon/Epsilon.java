package com.computerdude.epsilon;

import com.computerdude.epsilon.command.CmdGetCoins;
import com.computerdude.epsilon.command.CmdGetLevel;
import com.computerdude.epsilon.command.CmdSetCoins;
import com.computerdude.epsilon.command.CmdSetLevel;
import com.computerdude.epsilon.player.Chat;
import com.computerdude.epsilon.player.SpawnHandler;
import com.computerdude.epsilon.util.MySQL;
import com.computerdude.epsilon.util.PluginFile;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Epsilon extends JavaPlugin {

    public static PluginFile storageFile = null;

    @Override
    public void onEnable() {
        storageFile = new PluginFile(this, "storage.yml", "storage.yml");
        final PluginManager pm = Bukkit.getServer().getPluginManager();

        pm.registerEvents(new SpawnHandler(), this);
        pm.registerEvents(new Chat(), this);
        pm.registerEvents(new MySQL(), this);

        getCommand("setlevel").setExecutor(new CmdSetLevel());
        getCommand("getlevel").setExecutor(new CmdGetLevel());
        getCommand("setcoins").setExecutor(new CmdSetCoins());
        getCommand("getcoins").setExecutor(new CmdGetCoins());

        MySQL.createTables();
    }

    @Override
    public void onDisable() {
        MySQL.disconnect();
    }

}
