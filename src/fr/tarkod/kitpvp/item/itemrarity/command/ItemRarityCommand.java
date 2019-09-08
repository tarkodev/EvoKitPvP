package fr.tarkod.kitpvp.item.itemrarity.command;

import fr.tarkod.kitpvp.KitPvP;
import fr.tarkod.kitpvp.item.itemrarity.ItemRarity;
import fr.tarkod.kitpvp.item.itemrarity.ItemRarityManager;
import fr.tarkod.kitpvp.item.itemspecificity.ItemSpecificityManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ItemRarityCommand extends Command {

    private KitPvP main;

    public ItemRarityCommand(KitPvP main) {
        super("itemrarity");
        this.main = main;
    }

    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {
        if(sender instanceof Player){
            Player player = (Player) sender;

            ItemRarityManager itemRarityManager = main.getDataManager().getItemRarityManager();
            ItemSpecificityManager itemSpecificityManager = main.getDataManager().getItemSpecificityManager();

            if(!player.hasPermission("item.itemrarity")) {
                return false;
            }

            if(args.length == 1){
                String a = args[0];
                switch (a){
                    case "save":
                        itemRarityManager.saveItemRarity();
                        player.sendMessage("Save !");
                        break;
                    case "load":
                        itemRarityManager.loadItemRarity();
                        player.sendMessage("Load !");
                        break;
                    case "remove":
                        ItemStack itemStack = player.getItemInHand();
                        ItemStack result = itemSpecificityManager.setItemSpecificity(itemStack, itemSpecificity -> itemSpecificity.setItemRarityID(null));
                        player.setItemInHand(result);
                        player.sendMessage(ChatColor.GREEN + "Rareté enlevé !");
                        break;
                    case "test":
                        itemRarityManager.add(new ItemRarity("DEFAULTID"));
                        break;
                    default:break;
                }
            }
            if(args.length == 2){
                String a = args[0];
                String b = args[1];
                switch (a){
                    case "set":
                        ItemStack itemStack = player.getItemInHand();
                        if(itemRarityManager.getByID(b) != null) {
                            ItemRarity itemRarity = itemRarityManager.getByID(b);
                            ItemStack result = itemSpecificityManager.setItemSpecificity(itemStack, itemSpecificity -> itemSpecificity.setItemRarityID(itemRarity.getID()));
                            player.setItemInHand(result);
                            player.sendMessage(ChatColor.GREEN + "Rareté " + itemRarity.getNameWithPrefix() + ChatColor.GREEN + " rajouté à l'arme !");
                        } else {
                            player.sendMessage(ChatColor.RED + "Erreur: Rareté inconnu !");
                        }
                        break;
                }
            }
        }
        return false;
    }
}
