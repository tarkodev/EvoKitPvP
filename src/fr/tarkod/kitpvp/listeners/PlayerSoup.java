package fr.tarkod.kitpvp.listeners;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerSoup implements Listener {
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent e) {
		Player player = e.getPlayer();
		ItemStack itemInHand = player.getItemInHand();
		if(itemInHand.getType().equals(Material.MUSHROOM_SOUP)) {
			if(player.getHealth() == player.getMaxHealth()){
				return;
			}
			double result = player.getHealth() + 8.0;
			if(result > player.getMaxHealth()){
				player.setHealth(player.getMaxHealth());
			} else {
				player.setHealth(result);
			}
			itemInHand.setType(Material.BOWL);
		}
	}
}
