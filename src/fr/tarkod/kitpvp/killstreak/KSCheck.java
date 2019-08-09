package fr.tarkod.kitpvp.killstreak;

import fr.tarkod.kitpvp.listeners.custom.EGPlayerDeathByEntityEvent;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import fr.tarkod.kitpvp.KitPvP;
import fr.tarkod.kitpvp.profile.Profile;

public class KSCheck implements Listener {
	
	private KitPvP main;
	
	public KSCheck(KitPvP main) {
		this.main = main;
	}

	@EventHandler
	public void onPlayerKill(EGPlayerDeathByEntityEvent event){
		if(event.getKiller() instanceof Player) {
			Player player = (Player) event.getKiller();
			Profile profile = main.getDataManager().getProfileManager().get(player.getUniqueId());
			int ks = profile.getKillStreak() + 1;

			int money = 0;

			if(5 <= ks && ks < 20){
				if(ks % 5 == 0){
					money = 20;
				}
			} else if(20 <= ks && ks < 45){
				if(ks % 5 == 0){
					money = 30;
				}
			} else if(45 <= ks && ks < 70){
				if(ks % 5 == 0){
					money = 40;
				}
			} else if(70 <= ks && ks < 100){
				if(ks % 5 == 0){
					money = 50;
				}
			} else if(100 <= ks){
				if(ks % 5 == 0){
					money = 100;
				}
			}
			if(money != 0){
				player.sendMessage(ChatColor.GREEN + " +" + money + "$ " + ChatColor.GOLD + " (bonus " + ks + " killstreak)");
				profile.setMoney(profile.getMoney() + money);
			}
		}
	}
	@EventHandler
	public void onPlayerDamage(EntityDamageByEntityEvent e) {
		if(e.getDamager() instanceof Player) {
			Player player = (Player) e.getDamager();
			Profile profile = main.getDataManager().getProfileManager().get(player.getUniqueId());
			
			int ks = profile.getKillStreak();
			double damage = e.getDamage();
			
			if(10 <= ks && ks < 20) {
				damage = e.getDamage() * 1.02;
			}
			if(20 <= ks && ks < 30) {
				damage = e.getDamage() * 1.05;
			}
			if(30 <= ks && ks < 40) {
				damage = e.getDamage() * 1.07;
			}
			if(40 <= ks && ks < 50) {
				damage = e.getDamage() * 1.10;
			}
			if(50 <= ks && ks < 100) {
				damage = e.getDamage() * 1.13;
			}
			if(100 <= ks) {
				damage = e.getDamage() * 1.16;
			}
			e.setDamage(damage);
		}
	}
}