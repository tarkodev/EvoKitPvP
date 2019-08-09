package fr.tarkod.kitpvp.listeners;

import fr.tarkod.kitpvp.utils.PlayerUtils;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import fr.tarkod.kitpvp.ItemLibrary;
import fr.tarkod.kitpvp.KitPvP;
import fr.tarkod.kitpvp.utils.Title;

public class PlayerJoin implements Listener {
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		Player player = e.getPlayer();
		player.setGameMode(GameMode.SURVIVAL);
		player.teleport(ItemLibrary.SPAWN_LOCATION);
		PlayerUtils.removePotionEffect(player);
	}
}
