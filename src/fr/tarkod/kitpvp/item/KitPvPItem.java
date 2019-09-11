package fr.tarkod.kitpvp.item;

import fr.tarkod.kitpvp.KitPvP;
import fr.tarkod.kitpvp.item.itemspecificity.ItemSpecificity;
import fr.tarkod.kitpvp.item.itemspecificity.ItemSpecificityBase;
import fr.tarkod.kitpvp.item.itemspecificity.ItemSpecificityManager;
import fr.tarkod.kitpvp.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class KitPvPItem {

    private Material material;
    private short id;
    private int amount;

    private Map<String, Integer> enchantmentMap;

    private ItemSpecificity itemSpecificity;

    public KitPvPItem(ItemStack itemStack, KitPvP main) {
        this.material = itemStack.getType();
        this.id = itemStack.getDurability();
        this.amount = itemStack.getAmount();

        this.enchantmentMap = new HashMap<>();
        itemStack.getEnchantments().forEach((enchantment, integer) -> enchantmentMap.put(enchantment.getName(), integer));

        ItemSpecificityManager itemSpecificityManager = main.getDataManager().getItemSpecificityManager();
        this.itemSpecificity = itemSpecificityManager.hasItemSpecificity(itemStack) ? itemSpecificityManager.getItemSpecificity(itemStack) : new ItemSpecificity();
    }

    public ItemStack toItemStack(KitPvP main) {
        ItemBuilder itemBuilder = new ItemBuilder(material)
                .setDurability(id)
                .setAmount(amount);
        getEnchantmentMap().forEach(itemBuilder::addEnchantment);
        ItemStack itemStack = itemBuilder.toItemStack();
        itemStack = main.getDataManager().getItemSpecificityManager().setItemSpecificity(itemStack, itemSpecificity);
        return itemStack;
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public short getId() {
        return id;
    }

    public void setId(short id) {
        this.id = id;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Map<String, Integer> getEnchantmentMapString() {
        return enchantmentMap;
    }

    public Map<Enchantment, Integer> getEnchantmentMap() {
        Map<Enchantment, Integer> enchantmentIntegerMap = new HashMap<>();
        enchantmentMap.forEach((s, integer) -> enchantmentIntegerMap.put(Enchantment.getByName(s), integer));
        return enchantmentIntegerMap;
    }

    public void addEnchantment(Enchantment enchantment, int level) {
        enchantmentMap.put(enchantment.getName(), level);
    }

    public void removeEnchantment(Enchantment enchantment) {
        enchantmentMap.remove(enchantment.getName());
    }

    public void setEnchantmentMapString(Map<String, Integer> enchantmentMap) {
        this.enchantmentMap = enchantmentMap;
    }

    public ItemSpecificity getItemSpecificity() {
        return itemSpecificity;
    }

    public void setItemSpecificity(ItemSpecificity itemSpecificity) {
        this.itemSpecificity = itemSpecificity;
    }
}