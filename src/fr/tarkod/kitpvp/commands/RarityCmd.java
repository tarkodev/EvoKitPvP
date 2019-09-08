package fr.tarkod.kitpvp.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import fr.tarkod.kitpvp.KitPvP;
import fr.tarkod.kitpvp.item.ItemRarity;

public class RarityCmd extends Command {

	public KitPvP main;
	
	public RarityCmd(KitPvP main) {
		super("rarity");
		setUsage("Usage: /rarity <add> <itemrarity> | /rarity list | /rarity remove");
		this.setPermission("kitpvp.rarity");
		this.main = main;
	}
	
	@Override
	public boolean execute(CommandSender sender, String label, String[] args) {
		/*if(sender instanceof Player) {
			Player player = (Player) sender;
			ItemStack item = player.getItemInHand();
			if(item == null) {
				player.sendMessage("Vous n'avez pas d'objet en main");
				return false;
			}
			
			if(args.length == 0) {
				player.sendMessage(getUsage());
			}
			if(args.length == 1) {
				if(args[0].equalsIgnoreCase("remove")) {
					player.setItemInHand(ItemRarity.removeRarity(item));
					player.sendMessage(ChatColor.GOLD + "Rareté enlevé !");
				}
				if(args[0].equalsIgnoreCase("list")) {
					StringBuilder sb = new StringBuilder();
					sb.append(ChatColor.AQUA + "Il y a les raretés: ");
					for(ItemRarity rarity : ItemRarity.values()) {
						sb.append(rarity.getColor() + rarity.getLoreLine() + ChatColor.WHITE + ", ");
					}
					player.sendMessage(sb.toString());
				}
			}
			if(args.length == 2) {
				if(args[0].equalsIgnoreCase("add")) {
					if(ItemRarity.valueOf(args[1]) != null) {
						player.setItemInHand(ItemRarity.setRarity(item, ItemRarity.valueOf(args[1])));
					} else {
						player.sendMessage(ChatColor.RED + "Erreur: La rareté n'existe pas (/rarity list pour avoir la liste)");
					}
				}
			}
		}*/
		return false;
	}
}
