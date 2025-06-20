package com.p3t0plugin.villagenetwork.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class PostMessageCommand implements CommandExecutor {
    private final String apiUrl = "http://192.168.1.50/api/messages"; // à mettre en config plus tard
    private final JavaPlugin plugin;

    public PostMessageCommand(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) return false;

        String message = String.join(" ", args);
        Player player = (Player) sender;

        new Thread(() -> {
            try {
                URL url = new URL(apiUrl);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setDoOutput(true);
                conn.setRequestProperty("Content-Type", "application/json");

                String jsonInputString = String.format("{\"username\": \"%s\", \"message\": \"%s\"}", player.getName(), message);
                try (OutputStream os = conn.getOutputStream()) {
                    byte[] input = jsonInputString.getBytes("utf-8");
                    os.write(input, 0, input.length);
                }

                int responseCode = conn.getResponseCode();
                plugin.getLogger().info("POST sent, response code: " + responseCode);
            } catch (Exception e) {
                plugin.getLogger().severe("Error posting message: " + e.getMessage());
            }
        }).start();

        player.sendMessage("📬 Message envoyé !");
        return true;
    }
}
