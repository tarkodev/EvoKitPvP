package fr.tarkod.kitpvp.loot.commands;

import fr.tarkod.kitpvp.item.KitPvPItem;
import fr.tarkod.kitpvp.loot.Loot;
import fr.tarkod.kitpvp.loot.LootProfile;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.tarkod.kitpvp.KitPvP;
import fr.tarkod.kitpvp.loot.BlockLocation;
import org.bukkit.inventory.ItemStack;

public class LootCmd extends Command {

	private KitPvP main;
	
	public LootCmd(KitPvP main) {
		
		super("loot");
		
		this.main = main;
		
		setDescription("Pour les loot :D");
		setUsage("Usaged: /loot");
		this.setPermission("kitpvp.loot");
	}

	@Override
	public boolean execute(CommandSender sender, String str, String[] args) {
		LootProfile lp = main.getDataManager().getLootManager().getLootProfile();
		if(sender instanceof Player) {
			Player player = (Player) sender;
			if(player.hasPermission("kitpvp.loot")) {
				Location loc = player.getLocation();
				if (args.length == 0) {
					Loot loot = new Loot(loc, main);
					loot.spawn(10);
				}
				if (args.length == 1) {
					if (args[0].equalsIgnoreCase("save")) {
						main.getDataManager().getLootManager().saveFile();
						player.sendMessage("File saved");
					}
					if (args[0].equalsIgnoreCase("add")) {
						lp.getLootLocationList().add(new BlockLocation(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ()));
						player.sendMessage(ChatColor.AQUA + "L'emplacement à été ajouter !");
					}
					if (args[0].equalsIgnoreCase("list")) {
						StringBuilder sb = new StringBuilder();
						sb.append(ChatColor.WHITE + "Il y a des loot en :");
						for (BlockLocation loot : lp.getLootLocationList()) {
							sb.append("\n" + ChatColor.AQUA + loot.getX() + ", " + loot.getY() + ", " + loot.getZ());
						}
						player.sendMessage(sb.toString());
					}
					if (args[0].equalsIgnoreCase("additem")) {
						ItemStack itemStack = player.getItemInHand();
						if(main.getDataManager().getItemSpecificityManager().hasItemSpecificity(itemStack)) {
							lp.getListItem().add(new KitPvPItem(player.getItemInHand(), main));
							player.sendMessage("Rajouté");
						} else {
							player.sendMessage(ChatColor.RED + "Pas de rareté :/");
						}
					}
				}
				if (args.length == 2) {
					if (args[0].equalsIgnoreCase("remove")) {
						lp.getLootLocationList().remove(Integer.parseInt(args[1]));
					}
				}
			}
		}
		return false;
	}
	
}
