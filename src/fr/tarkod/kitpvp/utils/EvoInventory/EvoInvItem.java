package fr.tarkod.kitpvp.utils.EvoInventory;

import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.function.Consumer;

public class EvoInvItem {

    private ItemStack itemStack;

    private Consumer<InventoryClickEvent> clickEventConsumer;

    public EvoInvItem(ItemStack itemStack, Consumer<InventoryClickEvent> clickEventConsumer) {
        this.itemStack = itemStack;
        this.clickEventConsumer = clickEventConsumer;
    }

    public EvoInvItem(Material material, Consumer<InventoryClickEvent> clickEventConsumer) {
        this.itemStack = new ItemStack(material);
        this.clickEventConsumer = clickEventConsumer;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public Consumer<InventoryClickEvent> getClickEventConsumer() {
        return clickEventConsumer;
    }
}
