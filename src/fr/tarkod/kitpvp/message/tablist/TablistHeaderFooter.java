package fr.tarkod.kitpvp.message.tablist;

import fr.tarkod.kitpvp.utils.Title;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import ru.tehkode.permissions.bukkit.PermissionsEx;

public class TablistHeaderFooter implements Listener {

    @EventHandler
    public void playerJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();
        String color = ChatColor.translateAlternateColorCodes('&', PermissionsEx.getUser(player).getPrefix());
        Title.sendTabTitle(player,
                ChatColor.AQUA + "Bienvenue " + color + player.getName() + ChatColor.AQUA + " sur EvoGames",
                ChatColor.AQUA + "Tu joues sur " + ChatColor.GOLD + "KITPVP.EVOGAMES.FR"
        );
    }
}
