package com.p3t0plugin.villagenetwork.commands;

import com.p3t0plugin.villagenetwork.VillageManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ReloadCommand implements CommandExecutor {

    private final VillageManager villageManager;

    public ReloadCommand(VillageManager villageManager) {
        this.villageManager = villageManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        villageManager.reload();
        sender.sendMessage("§aVillages rechargés !");
        return true;
    }
}
