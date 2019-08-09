package fr.tarkod.kitpvp.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.tarkod.kitpvp.KitPvP;
import fr.tarkod.kitpvp.profile.Profile;

public class ExperienceCmd extends Command {

	private KitPvP main;
	
    public ExperienceCmd(KitPvP main) {
        super("experience");
        this.setDescription("experience command.");
        this.setUsage(ChatColor.RED + "Usage: /experience");
        this.main = main;
    }
	
	@Override
	public boolean execute(CommandSender sender, String label, String[] args) {
		if(sender.hasPermission("kitpvp.admin")) {
			if (args.length == 3) {
				String a = args[0];
				String b = args[1];
				String c = args[2];
				if(Bukkit.getPlayer(b) == null) {
					sender.sendMessage("player is null");
					return false;
				}
				Player receiver = Bukkit.getPlayer(b);
				switch (a){
					case "give":
						Profile receiverProfile = main.getDataManager().getProfileManager().get(receiver.getUniqueId());
						int i = Integer.parseInt(c);
						receiverProfile.setExperience(receiverProfile.getExperience() + i);
						receiver.sendMessage(ChatColor.AQUA + "Vous venez de recevoir " + ChatColor.AQUA + i + "xp");
						sender.sendMessage(i + "xp " + "give to " + receiver.getName());
						break;
					default:break;
				}
			}
		}
		return false;
	}

}
