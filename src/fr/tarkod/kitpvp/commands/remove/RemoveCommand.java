package fr.tarkod.kitpvp.commands.remove;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class RemoveCommand implements Listener {

    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent event){
        Player player = (Player) event.getPlayer();
        if(!player.hasPermission("kitpvp.pl")) {
            Set<String> commandBlocked = new HashSet<>(Arrays.asList(
                    "?",
                    "bukkit",
                    "me",
                    "pl",
                    "plugin",
                    "help",
                    "ver",
                    "version",
                    "tell",
                    "about"
            ));
            String str = event.getMessage()
                    .replace("/", "")
                    .replace("bukkit:", "")
                    .replace("spigot:", "")
                    .replace("minecraft:", "")
                    .split(" ")[0].toLowerCase();
            if(commandBlocked.contains(str)){
                player.sendMessage(ChatColor.RED + "Erreur: Cette commande a été bloquée");
                event.setCancelled(true);
            }
        }
    }
}
