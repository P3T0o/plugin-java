package com.p3t0plugin.villagenetwork.data;

import com.p3t0plugin.villagenetwork.models.Village;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;

public class VillageManager extends JavaPlugin {

    private final HashMap<UUID, Village> villages = new HashMap<>();
    private final File file;
    private final YamlConfiguration config;

    public VillageManager() {
        file = new File(Bukkit.getPluginManager().getPlugin("VillageLife").getDataFolder(), "config.yml");
        config = YamlConfiguration.loadConfiguration(file);
        loadVillages();
    }

    public void createVillage(UUID ownerId, String name, Location home) {
        Village village = new Village(ownerId, name, home);
        villages.put(ownerId, village);
        saveVillage(village);
    }

    public void reload() {
        this.config = YamlConfiguration.loadConfiguration(file);
        loadVillages();
    }


    public Village getVillage(UUID ownerId) {
        return villages.get(ownerId);
    }

    private void saveVillage(Village village) {
        String key = village.getOwnerId().toString();
        config.set(key + ".name", village.getName());
        config.set(key + ".world", village.getHome().getWorld().getName());
        config.set(key + ".x", village.getHome().getX());
        config.set(key + ".y", village.getHome().getY());
        config.set(key + ".z", village.getHome().getZ());
        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private File villagesFolder;

    public VillageManager(JavaPlugin plugin) {
        this.plugin = plugin;

        if (!plugin.getDataFolder().exists()) {
            plugin.getDataFolder().mkdirs();
        }

        villagesFolder = new File(plugin.getDataFolder(), "villages");
        if (!villagesFolder.exists()) {
            villagesFolder.mkdirs();
        }

        file = new File(plugin.getDataFolder(), "villages.yml");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        config = YamlConfiguration.loadConfiguration(file);
        loadVillages();
    }

    public void saveVillage(String name) {
        config.set("villages." + name + ".mayor", "Aucun");
        config.set("villages." + name + ".population", 0);
        try {
            config.save(file);
            plugin.getLogger().info("Village " + name + " sauvegardé.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadVillages() {
        for (String key : config.getKeys(false)) {
            UUID ownerId = UUID.fromString(key);
            String name = config.getString(key + ".name");
            String world = config.getString(key + ".world");
            double x = config.getDouble(key + ".x");
            double y = config.getDouble(key + ".y");
            double z = config.getDouble(key + ".z");
            Location loc = new Location(Bukkit.getWorld(world), x, y, z);
            villages.put(ownerId, new Village(ownerId, name, loc));
        }
        if (config.getConfigurationSection("villages") == null) return;

        for (String villageName : config.getConfigurationSection("villages").getKeys(false)) {
            plugin.getLogger().info("Village chargé : " + villageName);
        }
    }
}
