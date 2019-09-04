package fr.tarkod.kitpvp.item;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import fr.tarkod.kitpvp.utils.ItemBuilder;

public enum ItemRarity {
	
	KIT("KIT ITEM", ChatColor.GRAY),
	COMMON("COMMON", ChatColor.WHITE),
	UNCOMMON("UNCOMMON", ChatColor.GREEN),
	RARE("RARE", ChatColor.BLUE),
	EPIC("EPIC", ChatColor.LIGHT_PURPLE),
	LEGENDARY("LEGENDARY", ChatColor.GOLD),
	DEMONIAC("DEMONIAC", ChatColor.RED);
	
	private String name;
	private ChatColor color;
	
	private ItemRarity(String name, ChatColor color) {
		this.name = name;
		this.color = color;	
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ChatColor getColor() {
		return color;
	}

	public void setColor(ChatColor color) {
		this.color = color;
	}
	
	public String getLoreLine() {
		return getColor() + getName();
	}
	
	public static ItemStack setRarity(ItemStack item, ItemRarity rarity) {
		if(hasRarity(item)) {
			removeRarity(item);
		}
		ItemBuilder ib = new ItemBuilder(item).addLoreLine(rarity.getLoreLine());
		
		if(rarity.equals(ItemRarity.KIT)) {
			ib.setUnbreakable(true);
		}
		
		return ib.toItemStack();
	}
	
	public static ItemStack removeRarity(ItemStack item) {
		if(item.getItemMeta().getLore() == null) {
			return item;
		}
		
		for(ItemRarity rarity : ItemRarity.values()) {
			for(String str : item.getItemMeta().getLore()) {
				if(str.equals(rarity.getLoreLine())) {
					List<String> lore = item.getItemMeta().getLore();
					lore.remove(str);
					item.getItemMeta().setLore(lore);
					return item;
				}
			}
		}
		return item;
	}
	
	public static ItemRarity getRarity(ItemStack item) {
		if(!item.hasItemMeta() || item.getItemMeta().getLore() == null) {
			return null;
		}
		
		for(ItemRarity rarity : ItemRarity.values()) {
			for(String str : item.getItemMeta().getLore()) {
				if(str.equals(rarity.getLoreLine())) {
					return rarity;
				}
			}
		}
		return null;
	}
	
	public static boolean hasRarity(ItemStack item, List<ItemRarity> rarityList) {
		if(!item.hasItemMeta()) {
			return false;
		}
		if(item.getItemMeta().getLore() == null) {
			return false;
		}
		
		for(ItemRarity rarity : rarityList) {
			for(String str : item.getItemMeta().getLore()) {
				if(str.equals(rarity.getLoreLine())) {
					return true;
				}
			}
		}
		return false;
	}
	
	public static boolean hasRarity(ItemStack item) {
		return hasRarity(item, new ArrayList<ItemRarity>(Arrays.asList(ItemRarity.values())));
	}
	
	public static HashMap<ItemRarity, List<ItemStack>> inventoryChecker(Player player) {
		Inventory inv = player.getInventory();
		List<ItemStack> invContents = new ArrayList<ItemStack>(Arrays.asList(inv.getContents()));
		for(ItemStack item : player.getEquipment().getArmorContents()) {
			if(item != null) {
				invContents.add(item);
			}
		}
		HashMap<ItemRarity, List<ItemStack>> itemList = new HashMap<ItemRarity, List<ItemStack>>();
		for(ItemRarity rarity : ItemRarity.values()) {
			List<ItemStack> rarityList = new ArrayList<ItemStack>();
			for(ItemStack item : invContents) {
				if(item != null) {
					if(hasRarity(item)) {
						if(getRarity(item).equals(rarity)) {
							rarityList.add(item);
						}
					}
				}
			}
			itemList.put(rarity, rarityList);
		}
		return itemList;
	}
}
