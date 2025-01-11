package com.vikovaro.sprintplugin.Utilities;

import com.vikovaro.sprintplugin.Handlers.ErrorHandler;
import com.vikovaro.sprintplugin.Plugin;
import org.bukkit.configuration.ConfigurationSection;
import java.io.File;
import java.util.logging.Logger;
import static com.vikovaro.sprintplugin.Plugin.itemsConfig;
import static java.lang.String.valueOf;

public class Load {
    private static final Logger logger = Logger.getLogger("SprintPlugin");

    public static void startup() {
        loadConfig();
    }

    public static void loadConfig() {
        File pluginFolder = new File("plugins/SprintPlugin");
        if (!pluginFolder.exists()) {
            pluginFolder.mkdir();
        }

        Plugin instance = Plugin.getInstance();
        instance.saveDefaultConfig();
        try {
            instance.config = Plugin.getInstance().getConfig();

            Plugin.MAX_RUN_TIME = Integer.parseInt(valueOf(Plugin.getInstance().config.getString("max-run-time")));
            Plugin.MIN_RUN_TIME = Integer.parseInt(valueOf(Plugin.getInstance().config.getString("min-run-time")));
            Plugin.MAX_WEIGHT = Integer.parseInt(valueOf(Plugin.getInstance().config.getString("max-weight")));

            ConfigurationSection _convertCommands = Plugin.getInstance().config.getConfigurationSection("items-weight");
            if (_convertCommands != null) {
                for (String key : _convertCommands.getKeys(false)) {
                    int value = _convertCommands.getInt(key);
                    itemsConfig.put(key, value);
                    logger.warning(key+':'+itemsConfig.get(key));
                }
            }
        } catch(Exception e) {
            ErrorHandler.ExceptionHandler(e, "loadConfig");
        }
    }
}
