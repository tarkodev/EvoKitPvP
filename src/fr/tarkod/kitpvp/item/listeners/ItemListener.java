package fr.tarkod.kitpvp.item.listeners;

import fr.tarkod.kitpvp.KitPvP;
import fr.tarkod.kitpvp.item.ItemRarity;
import fr.tarkod.kitpvp.item.itemspecificity.ItemSpecificity;
import fr.tarkod.kitpvp.item.itemspecificity.ItemSpecificityManager;
import fr.tarkod.kitpvp.listeners.custom.EGPlayerDeathEvent;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.List;

public class ItemListener implements Listener {

    private KitPvP main;

    public ItemListener(KitPvP main) {
        this.main = main;
    }

    @EventHandler
    public void whenPlayerDropItem(PlayerDropItemEvent e) {
        ItemStack itemStack = e.getItemDrop().getItemStack();
        if(itemStack == null){
            return;
        }
        ItemSpecificity itemSpecificity = main.getDataManager().getItemSpecificityManager().getItemSpecificity(itemStack);
        if(!itemSpecificity.isLootWhenDrop()) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void whenPlayerDeath(EGPlayerDeathEvent e) {
        Player player = e.getVictim();
        ItemSpecificityManager itemSpecificityManager = main.getDataManager().getItemSpecificityManager();
        for (ItemStack itemStack : player.getInventory().getContents()) {
            if(itemStack != null){
                if(itemStack.getType() != Material.AIR){
                    if(itemSpecificityManager.hasItemSpecificity(itemStack)){
                        ItemSpecificity itemSpecificity = itemSpecificityManager.getItemSpecificity(itemStack);
                        if(itemSpecificity.isLootOnDeath()){
                            player.getWorld().dropItemNaturally(player.getLocation(), itemStack);
                        }
                    }
                }
            }
        }
    }

    /*@EventHandler
    public void whenCurrentItemInventoryMove(InventoryClickEvent e) {
        ItemStack itemStack = e.getCurrentItem();
        ItemSpecificityManager itemSpecificityManager = main.getDataManager().getItemSpecificityManager();

        if(itemStack == null) return;
        if(itemStack.getType() == Material.AIR) return;
        if(!itemSpecificityManager.hasItemSpecificity(itemStack)) return;

        ItemSpecificity itemSpecificity = itemSpecificityManager.getItemSpecificity(itemStack);

        if(e.getKitSelection().equals(e.getClickedInventory())) {
            if (!itemSpecificity.isLootWhenDrop()) e.setCancelled(true);
        }
    }

    @EventHandler
    public void whenCursorItemInventoryMove(InventoryClickEvent e) {
        ItemStack itemStack = e.getCursor();
        ItemSpecificityManager itemSpecificityManager = main.getDataManager().getItemSpecificityManager();

        if(itemStack == null) return;
        if(itemStack.getType() == Material.AIR) return;
        if(!itemSpecificityManager.hasItemSpecificity(itemStack)) return;

        ItemSpecificity itemSpecificity = itemSpecificityManager.getItemSpecificity(itemStack);

        if(e.getKitSelection().equals(e.getClickedInventory())) {
            if (!itemSpecificity.isLootWhenDrop()) e.setCancelled(true);
        }
    }*/
}
