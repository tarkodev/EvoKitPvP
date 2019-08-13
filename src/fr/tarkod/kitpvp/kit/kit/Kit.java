package fr.tarkod.kitpvp.kit.kit;

import fr.tarkod.kitpvp.utils.InventoryUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Kit {

    private String name;
    private String description;
    private Material material;
    private short id;

    private int moneyCost;
    private int levelCost;
    private int cooldown;
    private List<String> permissionList;

    private int slot;

    private Map<Integer, KitPvPItem> itemMap;
    private KitArmor armor;

    public Kit(String name) {
        this.name = name;
        this.cooldown = 60;
        this.description = "Default Description";
        this.id = 0;
        this.material = Material.STONE;
        this.armor = new KitArmor(null, null, null, null);
        this.permissionList = new ArrayList<>();
        this.itemMap = new HashMap<>();
    }

    public void setItem(int slot, KitPvPItem item) {
        this.itemMap.put(slot, item);
    }

    public Map<Integer, KitPvPItem> getItemMap(){
        return itemMap;
    }

    public void clear(){
        itemMap.clear();
        armor.clear();
    }

    public String getName() {
        return name;
    }

    public void apply(Player player) {
        PlayerInventory inventory = player.getInventory();
        player.getActivePotionEffects().forEach(potionEffect -> player.removePotionEffect(potionEffect.getType()));
        List<KitPvPItem> kitPvPItems = new ArrayList<>();
        inventory.clear();
        itemMap.forEach((integer, kitPvPItem) -> inventory.setItem(integer, kitPvPItem.toItemStack()));
        ItemStack air = new ItemStack(Material.AIR);
        player.getEquipment().setArmorContents(new ItemStack[]{air, air, air, air});
        if(armor.getHelmet() != null) {
            player.getEquipment().setHelmet(armor.getHelmet().toItemStack());
        }
        if(armor.getChestplate() != null) {
            player.getEquipment().setChestplate(armor.getChestplate().toItemStack());
        }
        if(armor.getLeggings() != null) {
            player.getEquipment().setLeggings(armor.getLeggings().toItemStack());
        }
        if(armor.getBoots() != null) {
            player.getEquipment().setBoots(armor.getBoots().toItemStack());
        }
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

    public int getMoneyCost() {
        return moneyCost;
    }

    public void setMoneyCost(int moneyCost) {
        this.moneyCost = moneyCost;
    }

    public int getLevelCost() {
        return levelCost;
    }

    public void setLevelCost(int levelCost) {
        this.levelCost = levelCost;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public KitArmor getArmor(){
        return armor;
    }

    public List<String> getPermissionList() {
        return permissionList;
    }

    public int getSlot() {
        return slot;
    }

    public void setSlot(int slot) {
        this.slot = slot;
    }

    public int getCooldown() {
        return cooldown;
    }

    public void setCooldown(int cooldown) {
        this.cooldown = cooldown;
    }
}
