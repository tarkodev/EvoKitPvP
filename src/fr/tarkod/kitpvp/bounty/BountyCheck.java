package fr.tarkod.kitpvp.bounty;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import fr.tarkod.kitpvp.KitPvP;
import fr.tarkod.kitpvp.listeners.custom.EGPlayerDeathByEntityEvent;
import fr.tarkod.kitpvp.profile.Profile;

public class BountyCheck implements Listener {

	private KitPvP main;

	public BountyCheck(KitPvP main) {
		this.main = main;
	}

	@EventHandler
	public void onEntityDamageByEntity(EGPlayerDeathByEntityEvent e) {
		if (e.getKiller() instanceof Player) {
			Player killer = (Player) e.getKiller();
			Player victim = e.getVictim();
			reward(killer, victim);
		}
	}

	public void reward(Player killer, Player victim) {
		victimChecker(killer, victim);
		killerChecker(killer, victim);
	}

	public void victimChecker(Player killer, Player victim) {
		Profile victimProfile = main.getDataManager().getProfileManager().get(victim.getUniqueId());
		Profile killerProfile = main.getDataManager().getProfileManager().get(killer.getUniqueId());
		int victimBounty = victimProfile.getBounty();

		if (victimBounty >= 1000) {
			Bukkit.broadcastMessage(ChatColor.AQUA + killer.getName() + ChatColor.RED
					+ " vient de toucher le pactole en tuant " + ChatColor.AQUA + victim.getName() + ChatColor.RED
					+ " qui avait un bounty de " + ChatColor.GREEN + victimBounty + "€" + ChatColor.RED + " !");
		}

		if (victimProfile.getBounty() != 0) {
			killerProfile.getPlayer()
					.sendMessage(ChatColor.GOLD + "Bravo ! " + ChatColor.AQUA + "Tu viens de gagner " + ChatColor.GREEN
							+ victimBounty + ChatColor.AQUA + "€ de " + victimProfile.getName() + " en le tuant !");
			killerProfile.setMoney(killerProfile.getMoney() + victimProfile.getBounty());
			victimProfile.setBounty(0);
		}
	}

	public void killerChecker(Player killer, Player victim) {
		Profile killerProfile = main.getDataManager().getProfileManager().get(killer.getUniqueId());

		int killerKS = killerProfile.getKillStreak();

		if(isDivible(killerKS)) {
			killerProfile.setBounty(killerProfile.getBounty() + 50);
			Bukkit.broadcastMessage(ChatColor.AQUA + killer.getName() + ChatColor.RED + " est à focus ! "
					+ ChatColor.GREEN + killerProfile.getBounty() + "€" + ChatColor.RED + " à gagner !");
		}
	}

	public int isDivibleBy5(int min, int max) {
		if (min > max) {
			throw new IllegalArgumentException(min + " <= " + max);
		}

		for (int nb = min; nb <= max; ++nb) {
			if (nb != 0 && (nb % 5 == 0)) {
				return nb;
			}
		}

		return 0;
	}
	
	public boolean isDivible(int value, int diviser) {
		if (value != 0 && (value % diviser == 0)) {
			return true;
		}
		return false;
	}
	
	public boolean isDivible(int value) {
		return isDivible(value, 5);
	}

}
