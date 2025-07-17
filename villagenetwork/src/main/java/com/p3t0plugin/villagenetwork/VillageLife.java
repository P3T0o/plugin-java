package com.p3t0plugin.villagenetwork;

import com.p3t0plugin.villagenetwork.data.VillageManager;
import org.bukkit.plugin.java.JavaPlugin;

public class VillageLife extends JavaPlugin {

    private static VillageLife instance;
    private VillageManager villageManager;

    public static VillageLife getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();
        villageManager = new VillageManager();
        getLogger().info("VillageNetwork activé !");
        getCommand("village").setExecutor(new ReloadCommand(villageManager));

        getLogger().info("VillageNetwork activé !");
    }

    public VillageManager getVillageManager() {
        return villageManager;
    }
}