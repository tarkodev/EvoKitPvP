package fr.tarkod.kitpvp.item.itemspecificity.command;

import fr.tarkod.kitpvp.KitPvP;
import fr.tarkod.kitpvp.item.itemspecificity.ItemSpecificityManager;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class TestItemCommand extends Command{

    private KitPvP main;

    public TestItemCommand(KitPvP main) {
        super("testitem");
        this.main = main;
    }

    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {
        if(sender instanceof Player){
            Player player = (Player) sender;
            ItemStack itemStack = new ItemStack(Material.GOLD_SWORD);
            itemStack = ItemSpecificityManager.setItemSpecificity(itemStack, itemSpecificity -> itemSpecificity.setItemRarityID("DEFAULTID"));
            player.getInventory().addItem(itemStack);
        }
        return false;
    }
}
