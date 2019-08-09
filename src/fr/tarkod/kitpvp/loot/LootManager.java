package fr.tarkod.kitpvp.loot;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;

import fr.tarkod.kitpvp.KitPvP;
import fr.tarkod.kitpvp.utils.FileUtils;
import fr.tarkod.kitpvp.profile.ProfileSerializationManager;
import fr.tarkod.kitpvp.utils.ItemBuilder;

public class LootManager {
	
	private List<Loot> lootList;
	private KitPvP main;
	private LootProfile lootProfile;
	private File file;
	
	public LootManager(KitPvP main) {
		this.lootList = new ArrayList<Loot>();
		this.main = main;
		file = new File(main.getDataFolder(), "loot" + ".json");
		defaultLoad();
		new LootRunnable().runTaskTimer(main, 0, 20);
	}
	
	private void defaultLoad() {
		loadKits();
		loadFile();
	}
	
	private void loadFile() {
		
		ProfileSerializationManager profileSerializationManager = main.getProfileSerializationManager();
		
		if(file.exists()) {
			String json = FileUtils.loadContent(file);
			lootProfile = profileSerializationManager.deserializeLocationList(json);
		} else {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
			lootProfile = new LootProfile(new ArrayList<BlockLocation>(), "world", true);
			FileUtils.saveFile(file, profileSerializationManager.serializeLocationList(lootProfile));
		}
	}
	
	public void saveFile() {
		ProfileSerializationManager profileSerializationManager = main.getProfileSerializationManager();
		FileUtils.saveFile(file, profileSerializationManager.serializeLocationList(lootProfile));
	}
	
	private void loadKits() {
		Arrays.asList(
				new ItemBuilder(Material.ARROW).setAmount(24).toItemStack()
		).forEach(item -> LootRarity.UNCOMMON.getListItem().add(item));
		
		Arrays.asList(
				new ItemBuilder(Material.IRON_AXE).addEnchantment(Enchantment.DAMAGE_ALL, 1).toItemStack(),
				new ItemBuilder(Material.GOLDEN_APPLE).setAmount(2).toItemStack(),
				new ItemBuilder(Material.POTION).setDurability((short) 16453).toItemStack(),
				new ItemBuilder(Material.GOLD_HELMET).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1).toItemStack(),
				new ItemBuilder(Material.GOLD_CHESTPLATE).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1).toItemStack(),
				new ItemBuilder(Material.GOLD_LEGGINGS).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1).toItemStack(),
				new ItemBuilder(Material.GOLD_BOOTS).addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 1).toItemStack()
		).forEach(item -> LootRarity.RARE.getListItem().add(item));
		
		Arrays.asList(
				new ItemBuilder(Material.DIAMOND_AXE).toItemStack(),
				new ItemBuilder(Material.POTION).setDurability((short) 16421).toItemStack(),
				new ItemBuilder(Material.IRON_HELMET).toItemStack(),
				new ItemBuilder(Material.IRON_CHESTPLATE).toItemStack(),
				new ItemBuilder(Material.IRON_LEGGINGS).toItemStack(),
				new ItemBuilder(Material.IRON_BOOTS).toItemStack(),
				new ItemBuilder(Material.BOW).toItemStack()
		).forEach(item -> LootRarity.EPIC.getListItem().add(item));
		
		Arrays.asList(
				new ItemBuilder(Material.DIAMOND_SWORD).toItemStack(),
				new ItemBuilder(Material.POTION).setDurability((short) 8225).toItemStack(),
				new ItemBuilder(Material.POTION).setDurability((short) 16417).toItemStack(),
				new ItemBuilder(Material.BOW).addEnchantment(Enchantment.ARROW_DAMAGE, 1).toItemStack(),
				new ItemBuilder(Material.BOW).addEnchantment(Enchantment.ARROW_INFINITE, 1).toItemStack()
		).forEach(item -> LootRarity.LEGENDARY.getListItem().add(item));
		
		Arrays.asList(
				new ItemBuilder(Material.DIAMOND_SWORD).addEnchantment(Enchantment.DAMAGE_ALL, 2).addEnchantment(Enchantment.FIRE_ASPECT, 1).toItemStack()
		).forEach(item -> LootRarity.DEMONIAC.getListItem().add(item));
	}
	
	

	public List<Loot> getLootList() {
		return lootList;
	}
	
	public void removeAllLoot() {
		for(Loot loot : lootList) {
			loot.remove();
		}
	}

	public LootProfile getLootProfile() {
		return lootProfile;
	}
}