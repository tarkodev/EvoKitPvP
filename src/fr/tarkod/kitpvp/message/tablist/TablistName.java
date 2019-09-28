package fr.tarkod.kitpvp.message.tablist;

import fr.tarkod.kitpvp.KitPvP;
import fr.tarkod.kitpvp.listeners.PlayerJoin;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scoreboard.Scoreboard;
import ru.tehkode.permissions.PermissionGroup;
import ru.tehkode.permissions.bukkit.PermissionsEx;

public class TablistName implements Listener {

    private KitPvP main;
    private Scoreboard sb;

    public TablistName(KitPvP main) {
        this.main = main;
        this.sb = Bukkit.getScoreboardManager().getNewScoreboard();
        onEnable();
    }

    public void onEnable(){
        PermissionsEx.getPermissionManager().getGroupList()
            .forEach(permissionGroup -> {
                sb.registerNewTeam(getGroupName(permissionGroup));
                sb.getTeam(getGroupName(permissionGroup)).setPrefix(
                    ChatColor.translateAlternateColorCodes('&', permissionGroup.getPrefix() + permissionGroup.getOption("group_name") + " "
                    ));
            });
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        PermissionGroup permissionGroup = PermissionsEx.getPermissionManager().getGroupList().stream()
                .filter(permissionGroups -> permissionGroups.getActiveUsers()
                .contains(PermissionsEx.getUser(player)))
                .findFirst().orElse(null);
        if(permissionGroup != null) {
            if(sb.getTeam(getGroupName(permissionGroup)) != null) {
                sb.getTeam(getGroupName(permissionGroup)).addPlayer(player);
            } else {
                player.sendMessage(ChatColor.RED + "Erreur: Le scoreboard n'a pas été correctement chargé");
            }
        } else {
            player.sendMessage(ChatColor.RED + "Erreur: Tu n'as pas de grade");
        }
        Bukkit.getOnlinePlayers().forEach(players -> players.setScoreboard(sb));
    }

    private String getGroupName(PermissionGroup permissionGroup){
        return permissionGroup.getWeight() + ChatColor.translateAlternateColorCodes('&', permissionGroup.getOption("group_name"));
    }
}
