package com.p3t0plugin.villagenetwork.commands;

import com.p3t0plugin.villagenetwork.VillageLife;
import com.p3t0plugin.villagenetwork.models.Village;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class VillageCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("Commande réservée aux joueurs.");
            return true;
        }

        if (args.length == 0) {
            player.sendMessage("§e/village create <nom>, /village sethome, /village home");
            return true;
        }

        switch (args[0].toLowerCase()) {
            case "create" -> {
                if (args.length < 2) {
                    player.sendMessage("§cUsage: /village create <nom>");
                    return true;
                }
                if (VillageLife.getInstance().getVillageManager().getVillage(player.getUniqueId()) != null) {
                    player.sendMessage("§cTu as déjà un village !");
                    return true;
                }
                String name = args[1];
                Location home = player.getLocation();
                VillageLife.getInstance().getVillageManager().createVillage(player.getUniqueId(), name, home);
                player.sendMessage("§aVillage créé avec succès : " + name);
            }

            case "home" -> {
                Village village = VillageLife.getInstance().getVillageManager().getVillage(player.getUniqueId());
                if (village == null) {
                    player.sendMessage("§cTu n'as pas encore de village !");
                    return true;
                }
                player.teleport(village.getHome());
                player.sendMessage("§aTéléporté à ton village.");
            }

            case "sethome" -> {
                Village village = VillageLife.getInstance().getVillageManager().getVillage(player.getUniqueId());
                if (village == null) {
                    player.sendMessage("§cTu n'as pas encore de village !");
                    return true;
                }
                village.setHome(player.getLocation());
                VillageLife.getInstance().getVillageManager().createVillage(player.getUniqueId(), village.getName(), village.getHome());
                player.sendMessage("§aNouveau home défini !");
            }

            default -> player.sendMessage("§cCommande inconnue.");
        }

        return true;
    }
}
