package fr.tarkod.kitpvp.utils;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.PlayerInventory;

public class InventoryUtils {

    public static void clearArmor(PlayerInventory inventory){
        inventory.setHelmet(null);
        inventory.setChestplate(null);
        inventory.setLeggings(null);
        inventory.setBoots(null);
    }

    public static void clear(PlayerInventory inventory){
        clearArmor(inventory);
        inventory.clear();
    }
}
