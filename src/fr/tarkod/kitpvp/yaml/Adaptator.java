package fr.tarkod.kitpvp.yaml;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import fr.tarkod.kitpvp.profile.Profile;

public class Adaptator {
	
	public static String convert(String str, Profile profile) {
		str = ChatColor.translateAlternateColorCodes('&', str);
		str = str.replace("%player%", profile.getName())
		.replace("%ks%", profile.getKillStreak() + "")
		.replace("%bks%", profile.getBestKillStreak() + "")
		.replace("%kills%", profile.getKill() + "")
		.replace("%deaths%", profile.getDeath() + "")
		.replace("%kd%", profile.getKD() + "")
		.replace("%money%", profile.getMoney() + "")
		.replace("%bounty%", profile.getBounty() + "")
		.replace("%xp%", profile.getExperience() + "")
		.replace("%uuid%", profile.getPlayer().getUniqueId().toString() + "")
		.replace("%online%", Bukkit.getOnlinePlayers().size() + "");
		return str;
	}

}
