package fr.tarkod.kitpvp;

import fr.tarkod.kitpvp.utils.EvoItem.EvoItem;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import fr.tarkod.kitpvp.utils.ItemBuilder;

public class ItemLibrary {
	
	public static ItemStack GOLDEN_APPLE = new ItemBuilder(Material.GOLDEN_APPLE).toItemStack();
	public static Location SPAWN_LOCATION = new Location(Bukkit.getWorld("world"), 0.5, 25, 0.5);

	private KitPvP main;

	public EvoItem kitSelectItem;

	public ItemLibrary(KitPvP main){
		this.main = main;
		kitSelectItem = new EvoItem(new ItemBuilder(Material.NETHER_STAR).setName("Sélection de kit").toItemStack(), event -> {
			event.getPlayer().performCommand("kit");
		}, this.main);
	}
}
