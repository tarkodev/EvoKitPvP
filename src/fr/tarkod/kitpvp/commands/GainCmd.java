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

public class GainCmd extends Command {

	private KitPvP main;
	
	public GainCmd(KitPvP main) {
		super("bounty");
		this.main = main;
	}
	
	@Override
	public boolean execute(CommandSender sender, String label, String[] args) {
		if(sender instanceof Player) {
			Player player = (Player) sender;
			UUID uuid = player.getUniqueId();
			Profile playerProfile = main.getDataManager().getProfileManager().get(uuid);
			
			if(args.length == 0 || args.length == 1) {
				player.sendMessage(ChatColor.RED + "Erreur: Utilisation /gain <player> <quantity>");
			}
			if(args.length == 2) {
				if(Bukkit.getPlayer(args[0]) != null){
					try {
				        Integer.parseInt(args[1]);
				    } catch(NumberFormatException e) {
				        e.printStackTrace();
				        player.sendMessage(ChatColor.RED + "Erreur: La quantité d'argent doit être notée en chiffre !");
				        return false;
				    }
					int quantity = Integer.parseInt(args[1]);
					
					if(quantity <= 0) {
						player.sendMessage(ChatColor.RED + "Erreur: soit généreux !");
						return false;
					}
					
					if(playerProfile.getMoney() >= quantity) {
						Player receiver = Bukkit.getPlayer(args[0]);
						Profile receiverProfile = main.getDataManager().getProfileManager().get(receiver.getUniqueId());
						if(player.equals(receiver)) {
							player.sendMessage(ChatColor.RED + "Erreur: Vous ne pouvez pas rajouter du gain à vous même!");
						} else {
							playerProfile.setMoney(playerProfile.getMoney() - quantity);
							receiverProfile.setBounty(receiverProfile.getBounty() + quantity);
							player.sendMessage("Vous venez de rajouter " + ChatColor.GREEN + quantity + ChatColor.WHITE + "€ à " + ChatColor.AQUA + receiver.getName() + ChatColor.WHITE + " !");
							receiver.sendMessage("Votre gain vient d'être augmenté de " + ChatColor.GREEN + quantity + ChatColor.WHITE + "€ de " + ChatColor.AQUA + player.getName() + ChatColor.WHITE + " !");
						}
					} else {
						player.sendMessage(ChatColor.RED + "Erreur: Vous n'avez pas les fonds nécessaire !");
					}
				} else {
					player.sendMessage(ChatColor.RED + "Erreur: Le joueur n'est pas connecté");
					return false;
				}
			}
		}
		return false;
	}
}
