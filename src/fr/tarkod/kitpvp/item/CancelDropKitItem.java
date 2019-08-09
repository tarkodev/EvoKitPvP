package fr.tarkod.kitpvp.item;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.ItemStack;

public class CancelDropKitItem implements Listener {
    @EventHandler
    public void onPlayerDropItem(PlayerDropItemEvent e) {
        Player player = e.getPlayer();
        ItemStack itemStack = e.getItemDrop().getItemStack();
        if(itemStack == null){
            return;
        }
        if(ItemRarity.getRarity(itemStack) == ItemRarity.KIT) {
            e.setCancelled(true);
        }
    }
}