package fr.tarkod.kitpvp.item;

import java.util.HashMap;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import fr.tarkod.kitpvp.listeners.custom.EGPlayerDeathEvent;

public class LootItemOnDeath implements Listener {
	
	@EventHandler
	public void onPlayerDropItem(EGPlayerDeathEvent e) {
		Player player = e.getVictim();
		Location loc = player.getLocation();
		Inventory inv = player.getInventory();
		HashMap<ItemRarity, List<ItemStack>> map = ItemRarity.inventoryChecker(player);
		for(ItemRarity rarity : map.keySet()) {
			if(rarity != ItemRarity.KIT) {
				List<ItemStack> itemList = map.get(rarity);
				for(ItemStack item : itemList) {
					inv.remove(item);
					loc.getWorld().dropItemNaturally(loc, item);
				}
			}
		}
	}
}
