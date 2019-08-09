package fr.tarkod.kitpvp.utils.welcome;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import fr.tarkod.kitpvp.KitPvP;

public class PlayerJoinLeft implements Listener{
	
	@EventHandler
	public void playerJoin(PlayerJoinEvent e) {
		e.setJoinMessage("");
	}
	
	@EventHandler
	public void playerQuit(PlayerQuitEvent e) {
		e.setQuitMessage("");
	}
	
	@EventHandler
	public void firstPlayerJoin(PlayerJoinEvent e) {
		Player player = e.getPlayer();
		if(!player.hasPlayedBefore()) {
			Bukkit.broadcastMessage(ChatColor.AQUA + "Bienvenue à " + ChatColor.GOLD + player.getName() + ChatColor.AQUA + " sur le KitPvP !");
			Bukkit.getOnlinePlayers().stream().filter(o -> o != player).forEach(o -> o.sendMessage(ChatColor.AQUA + "Écris " + ChatColor.GREEN +  "/bvn" + ChatColor.AQUA + " pour recevoir une récompense ! :D"));
			WelcomeTimer timer = new WelcomeTimer(player);
			WelcomeCmd.welcomeMap.put(player, timer);
			timer.runTaskTimer(KitPvP.getInstance(), 20, 20);
		}
	}
}
