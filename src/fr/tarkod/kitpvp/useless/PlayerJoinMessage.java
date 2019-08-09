package fr.tarkod.kitpvp.useless;

import fr.tarkod.kitpvp.listeners.PlayerJoin;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import ru.tehkode.permissions.bukkit.PermissionsEx;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class PlayerJoinMessage implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();
        player.sendMessage(ChatColor.AQUA + "" + ChatColor.STRIKETHROUGH + "---------------------------------------");
        player.sendMessage(ChatColor.GOLD + "Bienvenue " + ChatColor.translateAlternateColorCodes('&', PermissionsEx.getUser(player).getPrefix()) + player.getName() + ChatColor.GOLD + " sur le KitPvP d'EvoGames");
        player.sendMessage(" ");
        player.sendMessage(getRandomColor() + "Amuse-toi bien !");
        player.sendMessage(" ");
        player.performCommand("boutique");
        player.performCommand("twitter");
        player.performCommand("teamspeak");
        player.performCommand("discord");
        player.sendMessage(ChatColor.AQUA + "" + ChatColor.STRIKETHROUGH + "---------------------------------------");
    }

    public ChatColor getRandomColor(){
        List<ChatColor> chatColorBanned = Arrays.asList(
                ChatColor.BLACK,
                ChatColor.DARK_GRAY,
                ChatColor.DARK_RED,
                ChatColor.DARK_BLUE,
                ChatColor.STRIKETHROUGH,
                ChatColor.UNDERLINE,
                ChatColor.ITALIC,
                ChatColor.MAGIC,
                ChatColor.RESET
        );
        int random = new Random().nextInt(ChatColor.values().length);
        ChatColor chatColor = ChatColor.values()[random];
        if(chatColorBanned.contains(chatColor)){
            return getRandomColor();
        }
        return chatColor;
    }
}
