package fr.tarkod.kitpvp.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;

import org.bukkit.Color;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.Utility;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.material.MaterialData;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.google.gson.Gson;

import net.minecraft.server.v1_8_R3.NBTTagCompound;

public class ItemBuilder implements Cloneable {
	
	private ItemStack stack;
	
	@Utility
	public ItemBuilder() {}
	
	public ItemBuilder(final Material m) {
		this.stack = new ItemStack(m);
	}
	
	public ItemBuilder(final Material m, final int amount) {
		this.stack = new ItemStack(m, amount);
	}
	
	public ItemBuilder(final Material m, final int amount, final short damage) {
		this.stack = new ItemStack(m, amount, damage);
	}
	
	public ItemBuilder(final ItemStack stack) {
		this.stack = new ItemStack(stack);
	}
	
	public ItemBuilder(final ItemBuilder build) {
		this.stack = build.toItemStack();
	}
	
	public ItemBuilder(final String json) {
		new Gson().fromJson(json, ItemBuilder.class);
	}
	
	@Deprecated
	public ItemBuilder(final Material m, final int amount, final short damage, @Nullable final Byte data) {
		this.stack = new ItemStack(m, amount, damage, data);
	}
	
	public ItemBuilder addEnchantment(final Enchantment ench, final int level) {
		this.stack.addEnchantment(ench, level);
		return this;
	}
	
	public ItemBuilder addEnchantments(final Map<Enchantment, Integer> enchantments) {
		this.stack.addEnchantments(enchantments);
		return this;
	}
	
	public ItemBuilder addUnsafeEnchantment(final Enchantment ench, final int level) {
		this.stack.addUnsafeEnchantment(ench, level);
		return this;
	}
	
	public ItemBuilder addUnsafeEnchantments(final Map<Enchantment, Integer> enchantments) {
		this.stack.addUnsafeEnchantments(enchantments);
		return this;
	}

	public ItemBuilder clone() {
		return new ItemBuilder(this);
	}
	
	public ItemBuilder setAmount(final int amount) {
		this.stack.setAmount(amount);
		return this;
	}
	
	public ItemBuilder setData(final MaterialData data) {
		this.stack.setData(data);
		return this;
	}
	
	public ItemBuilder setDurability(final short durability) {
		this.stack.setDurability(durability);
		return this;
	}
	
	public ItemBuilder setType(final Material type) {
		this.stack.setType(type);
		return this;
	}
	
	@Deprecated
	public void setTypeId(final int type) {
		this.stack.setTypeId(type);
	}
	
	public ItemBuilder setStack(final ItemStack stack) {
		this.stack = stack;
		return this;
	}
	
	public ItemBuilder setName(final String name) {
		ItemMeta im = this.stack.getItemMeta(); 
		im.setDisplayName(name);
		this.stack.setItemMeta(im);
		
		return this;
	}

	public ItemBuilder setSkullOwner(final String owner) {
		try {
			SkullMeta sm = (SkullMeta) this.stack.getItemMeta();
			sm.setOwner(owner);
			this.stack.setItemMeta(sm);
			
		} catch (ClassCastException localClassCastException) {
			System.out.println("ItemBuilder : Skull Error");
		}
		
		return this;
	}

	public ItemBuilder addEnchantment(final Enchantment ench, final int level, final boolean ignoreLevelRestriction) {
		ItemMeta im = this.stack.getItemMeta();
		im.addEnchant(ench, level, ignoreLevelRestriction);
		this.stack.setItemMeta(im);
		
		return this;
	}

	public ItemBuilder setUnbreakable(final boolean state) {
		ItemMeta im = this.stack.getItemMeta();
		im.spigot().setUnbreakable(state);
		this.stack.setItemMeta(im);
		
		return this;
	}
	
    public ItemBuilder setGlowing(final boolean state) {
    	ItemMeta im = this.stack.getItemMeta();
        try {
        	
        	if (state) {
        		im.addEnchant(Enchantment.DURABILITY, 0, true);
                im.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        	} else {
        		im.removeEnchant(Enchantment.DURABILITY);
                im.removeItemFlags(ItemFlag.HIDE_ENCHANTS);
        	}
        	
            this.stack.setItemMeta(im);

        } catch (Exception ex) {
        	System.out.println("ItemBuilder : Glowing Error");
        }
        
        return this;
    }

	public ItemBuilder setLore(String... lore) {
		ItemMeta im = this.stack.getItemMeta();
		im.setLore(Arrays.asList(lore));
		this.stack.setItemMeta(im);
		
		return this;
	}

	public ItemBuilder setLore(List<String> lore) {
		ItemMeta im = this.stack.getItemMeta();
		im.setLore(lore);
		this.stack.setItemMeta(im);
		
		return this;
	}

	public boolean removeLoreLine(String line) {
		ItemMeta im = this.stack.getItemMeta();
		List<String> lore = new ArrayList<String>(im.getLore());
		if (!lore.contains(line)) {
			return false;
		} else {
			lore.remove(line);
			im.setLore(lore);
			this.stack.setItemMeta(im);
		
			return true;
		}		
	}

	public boolean removeLoreLine(int index) {
		ItemMeta im = this.stack.getItemMeta();
		List<String> lore = new ArrayList<String>(im.getLore());
		if ((index < 0) || (index > lore.size())) {
			return false;
		} else {
			lore.remove(index);
			im.setLore(lore);
			this.stack.setItemMeta(im);
			
			return true;
		}
	}

	public ItemBuilder addLoreLine(String line) {
		ItemMeta im = this.stack.getItemMeta();
		List<String> lore = new ArrayList<String>();
		if (im.hasLore()) {
			lore = new ArrayList<String>(im.getLore());
		}
		lore.add(line);
		im.setLore(lore);
		this.stack.setItemMeta(im);
		
		return this;
	}
	
	public ItemBuilder addLoreLine(String... line) {
		ItemMeta im = this.stack.getItemMeta();
		List<String> lore = new ArrayList<String>();
		if (im.hasLore()) {
			lore = new ArrayList<String>(im.getLore());
		}
		for(String str : line) {
			lore.add(str);
		}
		im.setLore(lore);
		this.stack.setItemMeta(im);
		
		return this;
	}

	public boolean addLoreLine(String line, int index) {
		ItemMeta im = this.stack.getItemMeta();
		List<String> lore = new ArrayList<String>(im.getLore());
		if (index < 0 || index > lore.size()) {
			return false;
		} else {
			lore.set(index, line);
			im.setLore(lore);
			this.stack.setItemMeta(im);
			return true;
		}
	}

	@SuppressWarnings("deprecation")
	public ItemBuilder setDyeColor(DyeColor color) {
		this.stack.setDurability(color.getData());
		return this;
	}

	@Deprecated
	public ItemBuilder setWoolColor(DyeColor color) {
		if (!this.stack.getType().equals(Material.WOOL)) {
			return this;
		} else {
			this.stack.setDurability(color.getData());
			return this;
		}
	}

	public ItemBuilder setLeatherArmorColor(final Color color) {
		try {
			LeatherArmorMeta im = (LeatherArmorMeta) this.stack.getItemMeta();
			im.setColor(color);
			this.stack.setItemMeta(im);

		} catch (ClassCastException localClassCastException) {
			System.out.println("§4ItemBuilder : LeatherArmor Error");
		}
		
		return this;
	}
	
	public ItemStack toItemStack() {
		return this.stack;
	}

	public ItemBuilder setEffect(PotionEffectType effect, int duration, int amplifier) {
		PotionMeta pm = (PotionMeta) this.stack.getItemMeta();
		pm.addCustomEffect(new PotionEffect(effect, duration, amplifier), true);
		stack.setItemMeta(pm);
		return this;
	}
	
	public String toJsonRegular() {
	    net.minecraft.server.v1_8_R3.ItemStack nmsItemStack = CraftItemStack.asNMSCopy(stack);
	    NBTTagCompound compound = new NBTTagCompound();
	    compound = nmsItemStack.save(compound);

	    return compound.toString();
	}
	
    public String toJson() {
        return new Gson().toJson(this);
    }
    
    public ItemBuilder fromJson(String json) {
    	new Gson().fromJson(json, ItemBuilder.class);
    	return this;
    }

}