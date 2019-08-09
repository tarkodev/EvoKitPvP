package fr.tarkod.kitpvp.message.chatcooldown;

import fr.tarkod.kitpvp.KitPvP;
import fr.tarkod.kitpvp.profile.Profile;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import ru.tehkode.permissions.PermissionUser;
import ru.tehkode.permissions.bukkit.PermissionsEx;

public class ChatManager {

    private KitPvP main;

    public ChatManager(KitPvP main) {
        this.main = main;
    }

    @EventHandler
    public void onAsynPlayerChat(AsyncPlayerChatEvent e) {
        Player player = e.getPlayer();
        Profile profile = main.getDataManager().getProfileManager().get(player.getUniqueId());

        //if(player.hasPermission("chat.bypass")){
        if(player.hasPermission("chatcolor")) {
            String message = ChatColor.translateAlternateColorCodes('&', e.getMessage());
            e.setMessage(message);
        }

        PermissionUser permissionUser = PermissionsEx.getUser(player);
        String prefix = ChatColor.translateAlternateColorCodes('&', permissionUser.getPrefix());

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(getLevel(profile.getLevel())+ " ");
        stringBuilder.append(prefix + permissionUser.getOption("group_name") + " " + e.getPlayer().getName() + ": ");
        stringBuilder.append(ChatColor.WHITE + e.getMessage());

        e.setFormat(stringBuilder.toString());
    }

    public String getLevel(int l){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(ChatColor.GRAY + "[");
        ChatColor chatColor = null;
        if(0 <= l && l < 10){
            chatColor = ChatColor.GRAY;
        }
        else if (10 <= l && l < 20) {
            chatColor = ChatColor.WHITE;
        }
        else if (20 <= l && l < 30) {
            chatColor = ChatColor.GREEN;
        }
        else if (30 <= l && l < 40) {
            chatColor = ChatColor.DARK_GREEN;
        }
        else if (40 <= l && l < 50) {
            chatColor = ChatColor.YELLOW;
        }
        else if (50 <= l && l < 60) {
            chatColor = ChatColor.GOLD;
        }
        else if (60 <= l && l < 70) {
            chatColor = ChatColor.RED;
        }
        else if (70 <= l && l < 80) {
            chatColor = ChatColor.LIGHT_PURPLE;
        }
        else if (80 <= l && l < 90) {
            chatColor = ChatColor.DARK_PURPLE;
        }
        else if (90 <= l && l < 100) {
            chatColor = ChatColor.BLACK;
        }
        else if (l >= 100) {
            chatColor = ChatColor.BOLD;
            stringBuilder.append(ChatColor.RED);
        }
        stringBuilder.append(chatColor + "" + l);
        stringBuilder.append(ChatColor.GRAY + "]");
        return stringBuilder.toString();
    }
}
