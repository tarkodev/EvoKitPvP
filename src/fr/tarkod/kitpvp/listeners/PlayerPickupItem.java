package fr.tarkod.kitpvp.listeners;

import org.bukkit.ChatColor;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import fr.tarkod.kitpvp.ItemLibrary;

public class PlayerPickupItem implements Listener {
	
	@EventHandler
	public void onPlayerPickupItem(PlayerPickupItemEvent e) {
		Player player = e.getPlayer();
		Item item = e.getItem();
		ItemStack itemStack = item.getItemStack();
		/*if(!(player.isDead())) {
			if(itemStack.equals(ItemLibrary.GOLDEN_APPLE)) {
				e.setCancelled(true);
				e.toItemStack().remove();
				player.removePotionEffect(PotionEffectType.REGENERATION);
				player.removePotionEffect(PotionEffectType.ABSORPTION);
				player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 5*20, 1));
				player.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 120*20, 0));
				player.sendMessage(ChatColor.GRAY + "Vous venez de récupérer l'effet d'une " + ChatColor.GOLD + "Pomme dorée");
			}
		}*/
	}
}
