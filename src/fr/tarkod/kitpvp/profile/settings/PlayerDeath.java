package fr.tarkod.kitpvp.profile.settings;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import fr.tarkod.kitpvp.KitPvP;
import fr.tarkod.kitpvp.listeners.custom.EGPlayerDeathByEntityEvent;
import fr.tarkod.kitpvp.profile.Profile;

public class PlayerDeath implements Listener {
	
	@EventHandler
	public void playerDeath(EGPlayerDeathByEntityEvent e) {
		if(e.getKiller() instanceof Player) {
			Player killer = (Player) e.getKiller();
			for(Player players : Bukkit.getOnlinePlayers()) {
				Profile profile = KitPvP.getInstance().getDataManager().getProfileManager().get(players.getUniqueId());
				if(profile.getSettings().isKillMessageVisible()) {
					players.sendMessage(ChatColor.AQUA + killer.getName() + ChatColor.WHITE + " à été tué(e) par " + ChatColor.AQUA + e.getVictim().getName());
				}
			}
		}
	}
}
