package fr.tarkod.kitpvp.item.itemrarity.command;

import fr.tarkod.kitpvp.KitPvP;
import fr.tarkod.kitpvp.item.itemrarity.ItemRarity;
import fr.tarkod.kitpvp.item.itemrarity.ItemRarityManager;
import fr.tarkod.kitpvp.item.itemrarity.save.ItemRaritySave;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

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

            ItemRaritySave itemRaritySave = main.getDataManager().getItemRarityManager();

            if(args.length == 1){
                String a = args[0];
                switch (a){
                    case "save":
                        itemRaritySave.saveItemRarity();
                        player.sendMessage("Save !");
                        break;
                    case "load":
                        itemRaritySave.loadItemRarity();
                        player.sendMessage("Load !");
                        break;
                    case "add":
                        ItemRarityManager.add(new ItemRarity("DEFAULTID"));
                        break;
                    default:break;
                }
            }
        }
        return false;
    }
}
