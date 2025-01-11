package com.vikovaro.sprintplugin.Utilities;

import com.vikovaro.sprintplugin.Plugin;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import java.util.Map;
import static com.vikovaro.sprintplugin.Plugin.itemsConfig;

public class Helpers {
    public static int getPlayerInventoryWeight(Player player) {
        int totalWeight = 0;

        PlayerInventory inventory = player.getInventory();

        for (ItemStack itemStack : inventory.getContents()) {
            if (itemStack != null) {
                totalWeight += itemStack.getAmount()*getItemWeight(itemStack);
            }
        }

        return totalWeight;
    }

    public static int getItemWeight(ItemStack itemStack) {
        Integer weight = Plugin.itemsConfig.get(itemStack.getType().toString());

        if (weight == null) {
            weight = 1;
        }

        return weight;
    }

    public static String getConfigMessage() {
        StringBuilder message = new StringBuilder();
        for (Map.Entry<String, Integer> entry : itemsConfig.entrySet()) {
            String itemWeight = entry.getKey()+":"+entry.getValue()+'\n';
            message.append(itemWeight);
        }
        message.append("Max weight: ").append(Plugin.MAX_WEIGHT);
        message.append("\nMax run time: ").append(Plugin.MAX_RUN_TIME);
        message.append("\nMin run time: ").append(Plugin.MIN_RUN_TIME);
        return message.toString();
    }

    public static double getSprintTime(Player player) {
        int inventoryWeight = getPlayerInventoryWeight(player);

        int maxRunTime = Plugin.MAX_RUN_TIME;
        int minRunTime = Plugin.MIN_RUN_TIME;
        int maxWeight = Plugin.MAX_WEIGHT;

        if (inventoryWeight >= maxWeight) {
            return minRunTime;
        }

        double weightRatio = (double) inventoryWeight / maxWeight;
        double runTime = (int) (maxRunTime - weightRatio * (maxRunTime - minRunTime));

        if (runTime < minRunTime) {
            runTime = minRunTime;
        } else if (runTime > maxRunTime) {
            runTime = maxRunTime;
        }

        player.sendMessage("Sprint time: " + runTime);

        return runTime;
    }
}
