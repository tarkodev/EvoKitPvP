package fr.tarkod.kitpvp.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffectType;

public class PlayerUtils {
	
	public static List<ItemStack> getRealInventory(Player player) {
		return Arrays.asList(player.getInventory().getContents());
	}

	public static void removePotionEffect(Player player){
		List<PotionEffectType> potionEffectTypes = new ArrayList<>();
		player.getActivePotionEffects().forEach(potionEffect -> potionEffectTypes.add(potionEffect.getType()));
		potionEffectTypes.forEach(player::removePotionEffect);
	}
}
