package com.vikovaro.sprintplugin;

import com.vikovaro.sprintplugin.Handlers.CommandHandler;
import com.vikovaro.sprintplugin.Listeners.InventoryListener;
import com.vikovaro.sprintplugin.Listeners.JoinListener;
import com.vikovaro.sprintplugin.Utilities.Load;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import java.util.HashMap;

public final class Plugin extends JavaPlugin {
    private static Plugin instance;
    public Plugin() {
        instance = this;
    }
    public static Plugin getInstance() {
        return instance;
    }

    public FileConfiguration config = null;
    public static HashMap<String, Integer> itemsConfig = new HashMap<>();

    public static HashMap<String, Double> playersMaxSprintTime = new HashMap<>();
    public static HashMap<String, Double> playersCurrentSprintTime = new HashMap<>();

    public static int MAX_RUN_TIME;
    public static int MIN_RUN_TIME;
    public static int MAX_WEIGHT;

    @Override
    public void onEnable() {
        // Config
        Load.startup();

        // Commands
        CommandHandler commandHandler = new CommandHandler(this);
        getCommand("itemmeta").setExecutor(commandHandler);
        getCommand("sprintplugin").setExecutor(commandHandler);

        // Listeners
        getServer().getPluginManager().registerEvents(new InventoryListener(), this);
        getServer().getPluginManager().registerEvents(new JoinListener(), this);

        getServer().getLogger().warning("SprintPlugin enabled");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
