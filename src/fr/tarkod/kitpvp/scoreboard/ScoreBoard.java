package fr.tarkod.kitpvp.scoreboard;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import fr.tarkod.kitpvp.profile.Profile;
import fr.tarkod.kitpvp.utils.ScoreboardSign;
import ru.tehkode.permissions.PermissionUser;
import ru.tehkode.permissions.bukkit.PermissionsEx;

public class ScoreBoard {
	
	public static ScoreboardSign createScoreboard(Profile profile) {
		ScoreboardSign ss = new ScoreboardSign(profile.getPlayer(), ChatColor.AQUA + "EvoKitPvP");
		ss.create();
		return ss;
	}
	
	public static void update(Profile profile) {
		if(profile.getScoreBoard() != null) {
			ScoreboardSign ss = profile.getScoreBoard();
			
			ss.setObjectiveName(ChatColor.AQUA + "Evo" + ChatColor.GRAY + "-" + ChatColor.AQUA + "PvP" + ChatColor.GRAY + " (" + ChatColor.GOLD + Bukkit.getOnlinePlayers().size() + ChatColor.GRAY + ")");
			
			PermissionUser permissionUser = PermissionsEx.getUser(profile.getPlayer());
			String c = ChatColor.translateAlternateColorCodes('&' ,permissionUser.getPrefix());
			ss.setLine(0, "§1");
			//ss.setLine(1, ChatColor.GREEN + "" + ChatColor.BOLD + "StatsCommand");
			//ss.setLine(2, c + "Pseudo: " + ChatColor.WHITE + profile.getName());
			ss.setLine(3, c + "Grade: " + ChatColor.WHITE + ChatColor.translateAlternateColorCodes('&', permissionUser.getOption("group_name")));
			ss.setLine(4, c + "Kills: " + ChatColor.WHITE + profile.getKill());
			ss.setLine(5, c + "Morts: " + ChatColor.WHITE + profile.getDeath());
			ss.setLine(6, c + "Ratio K/D: " + ChatColor.WHITE + profile.getKD());
			//ss.setLine(6, "§2");
			//ss.setLine(7, ChatColor.GOLD + "" + ChatColor.BOLD + "Niveau");
			ss.setLine(7, c + "Level: " + ChatColor.WHITE + profile.getLevel() + " (" + (int) profile.getExperience() + "/" + (int) profile.getLevelManager().howXpToLevelUp(profile.getLevel()) + ")");
			ss.setLine(8, c + "Prestige: " + ChatColor.WHITE + profile.getPrestige());
			//ss.setLine(8, c + "PrestigeCoins: " + ChatColor.WHITE + profile.getPrestigeCoins());
			//ss.setLine(8, ChatColor.GREEN + "Expérience: " + ChatColor.WHITE + profile.getExperience());
			ss.setLine(9, c + "Argent: " + ChatColor.WHITE + (int) profile.getMoney());
			ss.setLine(10, c + "Bounty: " + ChatColor.WHITE + profile.getBounty());
			ss.setLine(11, "§9");
			ss.setLine(12, ChatColor.RED + "" + ChatColor.BOLD + "Kill Streak");
			ss.setLine(13, c + "Actuel: " + ChatColor.WHITE + profile.getKillStreak());
			ss.setLine(14, c + "Maximum: " + ChatColor.WHITE + profile.getBestKillStreak());
		}
	}
}
