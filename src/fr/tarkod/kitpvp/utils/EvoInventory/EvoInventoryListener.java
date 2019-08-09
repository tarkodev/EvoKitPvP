package fr.tarkod.kitpvp.utils.EvoInventory;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

public class EvoInventoryListener implements Listener {

    private EvoInventory evoInventory;

    public EvoInventoryListener(EvoInventory evoInventory) {
        this.evoInventory = evoInventory;
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        Inventory inventory = evoInventory.getInventory();
        ItemStack item = event.getCurrentItem();
        if (item != null) {
            if (!item.getType().equals(Material.AIR)) {
                if (event.getInventory().equals(inventory)) {
                    Map.Entry<Integer, EvoInvItem> i = evoInventory.getInvItemMap().entrySet().stream().filter(e -> e.getValue().getItemStack().equals(item)).findFirst().orElse(null);
                    //evoInventory.getInvItemMap().forEach((slot, it)-> it.getClickEventConsumer().accept(event));
                    if (i != null) i.getValue().getClickEventConsumer().accept(event); //small fix
                    event.setCancelled(true);
                }
            }
        }
    }
}

