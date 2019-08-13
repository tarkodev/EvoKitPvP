package fr.tarkod.kitpvp.profile.level;

import fr.tarkod.kitpvp.KitPvP;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import fr.tarkod.kitpvp.profile.Profile;
import fr.tarkod.kitpvp.utils.Title;
import org.bukkit.scheduler.BukkitRunnable;

public class LevelManager {
	
	private Profile profile;
	private KitPvP main;
	
	public LevelManager(Profile profile, KitPvP main) {
		this.profile = profile;
		this.main = main;
		update();
	}

	public void update(){
		new BukkitRunnable(){
			@Override
			public void run() {
				if(howXpToLevelUp(profile.getLevel()) <= profile.getExperience()){
					levelUp();
				} else {
					cancel();
				}
			}
		}.runTaskTimer(main, 0, 1);
	}

	private void levelUp() {
		Player player = profile.getPlayer();

		profile.setExperience(profile.getExperience() - howXpToLevelUp(profile.getLevel()));
		profile.setLevel(profile.getLevel() + 1);
		profile.setMoney(profile.getMoney() + 20);

		Title.sendFullTitle(player, 10, 40, 10, ChatColor.AQUA + "LEVEL UP!", ChatColor.GRAY + "[" + (profile.getLevel() - 1) + "] -> [" + profile.getLevel() + "]");
		player.sendMessage(ChatColor.GREEN + "+20$ (LEVEL UP)");
		player.setHealth(player.getMaxHealth());
		player.playSound(player.getLocation(), Sound.LEVEL_UP, 1, 1);
	}
	
	public double howXpToLevelUp(int level) {
		int p = profile.getPrestige()*2;
		if(0 <= level && level < 10) {
			return 15 + p;
		}
		if(10 <= level && level < 20) {
			return 20 + p;
		}
		if(20 <= level && level < 30) {
			return 25 + p;
		}
		if(30 <= level && level < 40) {
			return 30 + p;
		}
		if(40 <= level && level < 50) {
			return 35 + p;
		}
		if(50 <= level && level < 60) {
			return 40 + p;
		}
		if(60 <= level && level < 70) {
			return 45 + p;
		}
		if(70 <= level && level < 80) {
			return 50 + p;
		}
		if(80 <= level && level < 90) {
			return 60 + p;
		}
		if(90 <= level && level < 100) {
			return 75 + p;
		}
		if(100 <= level) {
			return 9999999;
		}
		return 42;
	}

	public String getLevelColor(){
		int l = profile.getLevel();
		StringBuilder stringBuilder = new StringBuilder();
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
		stringBuilder.append(chatColor);
		return stringBuilder.toString();
	}
}
