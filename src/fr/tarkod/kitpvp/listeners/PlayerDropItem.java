package fr.tarkod.kitpvp.listeners;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerDropItem implements Listener {
	
	@EventHandler
	public void onPlayerDropItem(PlayerDropItemEvent e) {
		ItemStack itemStack = e.getItemDrop().getItemStack();
		if(itemStack.getType().equals(Material.BOWL) || itemStack.getType().equals(Material.GLASS_BOTTLE)) {
			e.getItemDrop().remove();
		}
		/*if(ItemRarity.hasRarity(itemStack)) {
			if(ItemRarity.getRarity(itemStack).equals(ItemRarity.KIT)) {
				e.getItemDrop().remove();
			}
		}*/
	}
}
