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
}