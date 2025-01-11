package com.vikovaro.sprintplugin.Handlers;

import org.bukkit.ChatColor;

import java.util.logging.Logger;

public class ErrorHandler {
    private static final Logger logger = Logger.getLogger("SprintPlugin");

    public static void ExceptionHandler(Exception e, String methodName) {
        logger.info(ChatColor.RED + "[SprintPlugin] ["+methodName+"] Exception: "+e.getMessage());
    }
}
