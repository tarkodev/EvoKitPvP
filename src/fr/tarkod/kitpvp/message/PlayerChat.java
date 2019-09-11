package fr.tarkod.kitpvp.message;

import fr.tarkod.kitpvp.KitPvP;
import fr.tarkod.kitpvp.profile.Profile;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import ru.tehkode.permissions.PermissionUser;
import ru.tehkode.permissions.bukkit.PermissionsEx;

import java.util.Arrays;

public class PlayerChat implements Listener {

	private KitPvP main;

	public PlayerChat(KitPvP main) {
		this.main = main;
	}

	@EventHandler
	public void onAsynPlayerChat(AsyncPlayerChatEvent e) {
		Player player = e.getPlayer();
		Profile profile = main.getDataManager().getProfileManager().get(player.getUniqueId());

		e.setMessage(e.getMessage().replace("%", "%%").replace("<3", "❤"));
		e.setMessage(player.hasPermission("chatcolor") ? ChatColor.translateAlternateColorCodes('&', e.getMessage()) : e.getMessage());

		PermissionUser permissionUser = PermissionsEx.getUser(player);
		String prefix = ChatColor.translateAlternateColorCodes('&', permissionUser.getPrefix());

		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(profile.getLevelPrefix() + " ");
		stringBuilder.append(prefix + permissionUser.getOption("group_name") + " " + e.getPlayer().getName() + ": ");
		stringBuilder.append(ChatColor.WHITE + e.getMessage());

		e.setFormat(stringBuilder.toString());
	}
}
