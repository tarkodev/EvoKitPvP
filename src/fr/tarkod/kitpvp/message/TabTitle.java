package fr.tarkod.kitpvp.message;

import fr.tarkod.kitpvp.KitPvP;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import ru.tehkode.permissions.PermissionUser;
import ru.tehkode.permissions.bukkit.PermissionsEx;

public class TabTitle implements Listener {

    private KitPvP main;

    public TabTitle(KitPvP main) {
        this.main = main;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();
        PermissionUser permissionUser = PermissionsEx.getUser(player);
        String prefix = ChatColor.translateAlternateColorCodes('&', permissionUser.getPrefix() + permissionUser.getOption("group_name") + " ");
        //player.setPlayerListName(prefix + player.getName());
    }
}
