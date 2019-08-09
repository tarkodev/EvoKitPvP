package fr.tarkod.kitpvp.timer;

import fr.tarkod.kitpvp.utils.PlayerUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;

import fr.tarkod.kitpvp.ItemLibrary;
import fr.tarkod.kitpvp.KitPvP;
import fr.tarkod.kitpvp.listeners.custom.EGPlayerRespawnEvent;
import fr.tarkod.kitpvp.profile.Profile;
import fr.tarkod.kitpvp.utils.Title;
import ru.tehkode.permissions.bukkit.PermissionsEx;

public class RespawnTimer extends BukkitRunnable implements Listener {

	private Player player;
	private int timeLeft;
	private boolean status;
	private KitPvP main;
	
	public RespawnTimer(Player player, int respawnTime) {
		this.player = player;
		this.timeLeft = respawnTime;
		this.status = true;
		this.main = KitPvP.getInstance();
		defaultLoad();
	}
	
	public void defaultLoad() {
		main.getServer().getPluginManager().registerEvents(this, main);
		player.setHealth(player.getMaxHealth());
		Profile playerProfile = KitPvP.getInstance().getDataManager().getProfileManager().get(player.getUniqueId());
		playerProfile.setDeath(playerProfile.getDeath() + 1);
		playerProfile.setKillStreak(0);
		player.setGameMode(GameMode.SPECTATOR);
		PlayerUtils.removePotionEffect(player);
		
		respawnTitle();
	}

	@Override
	public void run() {
		timeLeft--;
		if(timeLeft == 0) {
			spawn();
		} 
		else if(timeLeft > 0) {
			respawnTitle();
		}
	}
	
	private void spawn() {
		status = false;
		player.teleport(ItemLibrary.SPAWN_LOCATION);
		player.setGameMode(GameMode.SURVIVAL);
		player.setFallDistance(0);
		player.setFireTicks(0);
		Title.sendTitle(player, 5, 10, 5, ChatColor.GOLD + "Réapparition !");
		Bukkit.getPluginManager().callEvent(new EGPlayerRespawnEvent(player));
		main.getDataManager().getProfileManager().get(player.getUniqueId()).getFight().setTimeLeft(0);
		cancel();
	}
	
	private void respawnTitle() {
		Title.sendFullTitle(player, 0, 50, 0, ChatColor.RED + "Tu es mort !", ChatColor.GRAY + "Réapparition dans " + timeLeft);
	}
	
	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		if(isEnable()) {
			if (timeLeft > 0) {
				spawn();
			}
		}
	}

	public boolean isEnable(){
		return status;
	}
}