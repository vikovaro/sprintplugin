package com.vikovaro.sprintplugin.Listeners;

import com.vikovaro.sprintplugin.Plugin;
import com.vikovaro.sprintplugin.Utilities.Helpers;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerToggleSprintEvent;
import org.bukkit.scheduler.BukkitTask;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

public class JoinListener implements Listener {
    private static final Logger logger = Logger.getLogger("SprintPlugin");
    private final Map<String, BukkitTask> playerTasks = new HashMap<>();

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        double sprintTime = Helpers.getSprintTime(player);

        Plugin.playersMaxSprintTime.put(player.getName(), sprintTime);
        Plugin.playersCurrentSprintTime.put(player.getName(), (double) sprintTime);
        createSprintTask(player);
    }

    @EventHandler
    public void onPlayerToggleSprint(PlayerToggleSprintEvent event)
    {
        Player player = event.getPlayer();

        double currentSprintTime = Plugin.playersCurrentSprintTime.get(player.getName());
        if (currentSprintTime == 0) {
            player.setSprinting(false);
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        Plugin.playersMaxSprintTime.remove(player.getName());
        Plugin.playersCurrentSprintTime.remove(player.getName());
        BukkitTask task = playerTasks.remove(player.getName());
        if (task != null) {
            task.cancel();
        }
    }

    private void createSprintTask(Player player) {
        BukkitTask task = Bukkit.getScheduler().runTaskTimerAsynchronously(Plugin.getInstance(), () -> {
            if (player.isOnline()) {

                double maxSprintTime = Plugin.playersMaxSprintTime.get(player.getName());
                double currentSprintTime = Plugin.playersCurrentSprintTime.get(player.getName());

//                player.sendMessage("max sprint time: "+maxSprintTime);
//                player.sendMessage("current sprint time: "+currentSprintTime);

                if (!player.isSprinting()) {
                    if (currentSprintTime < maxSprintTime) {
                        Plugin.playersCurrentSprintTime.put(player.getName(), currentSprintTime + 0.5);
                    }
                } else {
                    if (currentSprintTime > 0) {
                        Plugin.playersCurrentSprintTime.put(player.getName(), currentSprintTime - 0.5);
                    } else {
                        player.setSprinting(false);
                    }
                }
            }
        }, 0L, 10L);

        playerTasks.put(player.getName(), task);
    }
}