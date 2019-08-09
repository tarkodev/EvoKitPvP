package fr.tarkod.kitpvp.commands;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.tarkod.kitpvp.KitPvP;
import fr.tarkod.kitpvp.profile.Profile;

public class MoneyCmd extends Command {

	private KitPvP main;
	
	public MoneyCmd(KitPvP main) {
		super("money");
		this.main = main;
	}
	
	@Override
	public boolean execute(CommandSender sender, String label, String[] args) {
		if(sender instanceof Player) {
			Player player = (Player) sender;
			UUID uuid = player.getUniqueId();
			Profile playerProfile = main.getDataManager().getProfileManager().get(uuid);
			double money = playerProfile.getMoney();
			
			if(args.length == 0) {
				player.sendMessage(ChatColor.AQUA + "Vous possédez " + ChatColor.GREEN + money + "€");
			}
		}
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
						receiverProfile.setMoney(receiverProfile.getMoney() + i);
						receiver.sendMessage(ChatColor.AQUA + "Vous venez de recevoir " + ChatColor.GREEN + i + "€");
						sender.sendMessage(i + "€ " + "give to " + receiver.getName());
						break;
					default:break;
				}
			}
		}
		return false;
	}

}
