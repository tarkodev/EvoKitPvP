package fr.tarkod.kitpvp.item.itemspecificity.command;

import com.sun.org.apache.xpath.internal.operations.Bool;
import fr.tarkod.kitpvp.KitPvP;
import fr.tarkod.kitpvp.item.itemrarity.ItemRarity;
import fr.tarkod.kitpvp.item.itemrarity.ItemRarityManager;
import fr.tarkod.kitpvp.item.itemspecificity.ItemSpecificity;
import fr.tarkod.kitpvp.item.itemspecificity.ItemSpecificityBase;
import fr.tarkod.kitpvp.item.itemspecificity.ItemSpecificityManager;
import fr.tarkod.kitpvp.utils.EvoInventory.EvoInvItem;
import fr.tarkod.kitpvp.utils.EvoInventory.EvoInventory;
import fr.tarkod.kitpvp.utils.ItemBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ItemSpecificityCommand extends Command {

    private KitPvP main;

    public ItemSpecificityCommand(KitPvP main) {
        super("itemspecificity");
        this.main = main;
    }

    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {
        if(sender instanceof Player){
            Player player = (Player) sender;

            ItemRarityManager itemRarityManager = main.getDataManager().getItemRarityManager();
            ItemSpecificityManager itemSpecificityManager = main.getDataManager().getItemSpecificityManager();

            if(!player.hasPermission("item.itemspecificity")) {
                return false;
            }

            if(args.length == 0){
                EvoInventory evoInventory = new EvoInventory("Personnalisation ", 9, main);
                evoInventory.setItem(0, new EvoInvItem(new ItemBuilder(Material.BLAZE_POWDER).setName("lootOnDeath").toItemStack(), event -> {
                    player.setItemInHand(itemSpecificityManager.setItemSpecificity(player.getItemInHand(), itemSpecificity -> itemSpecificity.setLootOnDeath(!itemSpecificity.isLootOnDeath())));
                }));
                evoInventory.setItem(1, new EvoInvItem(new ItemBuilder(Material.BEDROCK).setName("lootWhenDrop").toItemStack(), event -> {
                    player.setItemInHand(itemSpecificityManager.setItemSpecificity(player.getItemInHand(), itemSpecificity -> itemSpecificity.setLootWhenDrop(!itemSpecificity.isLootWhenDrop())));
                }));
                player.openInventory(evoInventory.getInventory());
            }
            if(args.length == 1){
                String a = args[0];
                switch (a){
                    case "lootOnDeath":
                        ItemStack itemStack = player.getItemInHand();
                        ItemStack result = itemSpecificityManager.setItemSpecificity(itemStack, itemSpecificity -> {
                            if(itemSpecificity.isLootOnDeath()){
                                itemSpecificity.setLootOnDeath(false);
                                player.sendMessage(ChatColor.GREEN + "L'objet ne sera plus drop quand on meurt !");
                            } else {
                                itemSpecificity.setLootOnDeath(true);
                                player.sendMessage(ChatColor.GREEN + "L'objet sera drop quand on meurt !");
                            }
                        });
                        player.setItemInHand(result);
                        break;
                    case "lootWhenDrop":
                        player.setItemInHand(itemSpecificityManager.setItemSpecificity(player.getItemInHand(), itemSpecificity -> {
                            if(itemSpecificity.isLootWhenDrop()){
                                itemSpecificity.setLootWhenDrop(false);
                                player.sendMessage(ChatColor.GREEN + "L'objet n'est plus jetable !");
                            } else {
                                itemSpecificity.setLootWhenDrop(true);
                                player.sendMessage(ChatColor.GREEN + "L'objet est jetable !");
                            }
                        }));
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
                    case "base":
                        ItemSpecificityBase itemSpecificityBase = ItemSpecificityBase.valueOf(b);
                        player.setItemInHand(itemSpecificityManager.setItemSpecificity(player.getItemInHand(), new ItemSpecificity(itemSpecificityBase)));
                        player.sendMessage(ChatColor.GREEN + "Base " + itemSpecificityBase.name() + " mis !");
                }
            }
        }
        return false;
    }
}
