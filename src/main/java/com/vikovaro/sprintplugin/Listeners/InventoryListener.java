package com.vikovaro.sprintplugin.Listeners;

import com.vikovaro.sprintplugin.Plugin;
import com.vikovaro.sprintplugin.Utilities.Helpers;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class InventoryListener implements Listener {
    @EventHandler
    public void onItemDrop(PlayerDropItemEvent event) {
        updatePlayerInventoryWeight(event.getPlayer());
    }

    @EventHandler
    public void onItemPickup(EntityPickupItemEvent event) {
        if (event.getEntity() instanceof Player player) {
            new BukkitRunnable() {
                @Override
                public void run() {
                    updatePlayerInventoryWeight(player);
                }
            }.runTaskLater(Plugin.getInstance(), 10L);
        }
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent event) {
        if (event.getPlayer() instanceof Player) {
            Player player = (Player) event.getPlayer();
            updatePlayerInventoryWeight(player);
        }
    }

    private void updatePlayerInventoryWeight(Player player) {
        double maxSprintTime = Helpers.getSprintTime(player);
        Plugin.playersMaxSprintTime.put(player.getName(), maxSprintTime);

        double currentSprintTime = Plugin.playersCurrentSprintTime.get(player.getName());
        if (currentSprintTime > maxSprintTime) {
            Plugin.playersCurrentSprintTime.put(player.getName(), (double) maxSprintTime);
        }
    }
}
