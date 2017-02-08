package com.computerdude.epsilon;

import com.computerdude.epsilon.command.CmdGetLevel;
import com.computerdude.epsilon.command.CmdSetLevel;
import com.computerdude.epsilon.game.Spawn;
import com.computerdude.epsilon.player.Chat;
import com.computerdude.epsilon.util.MySQL;
import com.computerdude.epsilon.util.PluginFile;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    public static PluginFile storageFile = null;

    @Override
    public void onEnable() {
        storageFile = new PluginFile(this, "storage.yml", "storage.yml");
        PluginManager pm = Bukkit.getServer().getPluginManager();

        pm.registerEvents(new Spawn(), this);
        pm.registerEvents(new Chat(), this);
        pm.registerEvents(new MySQL(), this);

        getCommand("setlevel").setExecutor(new CmdSetLevel());
        getCommand("getlevel").setExecutor(new CmdGetLevel());
    }

    @Override
    public void onDisable() {
        MySQL.disconnect();
    }

}
