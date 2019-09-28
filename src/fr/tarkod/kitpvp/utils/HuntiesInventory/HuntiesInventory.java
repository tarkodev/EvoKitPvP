package fr.tarkod.kitpvp.utils.HuntiesInventory;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public abstract class HuntiesInventory {

    private JavaPlugin main;

    private Inventory inventory;
    private Map<Integer, HuntiesInventoryItem> invItemMap;

    private HuntiesInventoryListener oldListener;

    public HuntiesInventory(int size, String name, JavaPlugin main) {
        this.main = main;
        this.invItemMap = new HashMap<>();
        this.inventory = Bukkit.createInventory(null, size, name);
    }

    public HuntiesInventory(InventoryType inventoryType, String name, JavaPlugin main) {
        this.main = main;
        this.invItemMap = new HashMap<>();
        this.inventory = Bukkit.createInventory(null, inventoryType, name);
    }

    public abstract void load();

    public void open(Player player) {
        load();
        player.openInventory(inventory);
    }

    public void setItem(int slot, ItemStack itemStack, Consumer<InventoryClickEvent> inventoryClickEventConsumer) {
        this.invItemMap.put(slot, new HuntiesInventoryItem(itemStack, inventoryClickEventConsumer));
        update();
    }

    public void setItem(int slot, ItemStack itemStack) {
        setItem(slot, itemStack, inventoryClickEvent -> {});
    }

    public void removeItem(int slot) {
        this.invItemMap.remove(slot);
        update();
    }

    public void clear() {
        this.invItemMap.clear();
        update();
    }

    public void update() {
        registerItem();
        registerEvent();
    }

    private void registerItem() {
        this.invItemMap.forEach((slot, item)-> inventory.setItem(slot, item.getItemStack()));
    }

    private void registerEvent(){
        if(oldListener != null) HandlerList.unregisterAll(oldListener);
        HuntiesInventoryListener event = new HuntiesInventoryListener(this);
        Bukkit.getServer().getPluginManager().registerEvents(
                event,
                main
        );
        oldListener = event;
    }

    public Map<Integer, HuntiesInventoryItem> getInvItemMap() {
        return invItemMap;
    }

    public Inventory getInventory() {
        return inventory;
    }
}

class HuntiesInventoryListener implements Listener {

    private HuntiesInventory huntiesInventory;

    public HuntiesInventoryListener(HuntiesInventory huntiesInventory) {
        this.huntiesInventory = huntiesInventory;
    }

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        Inventory inventory = huntiesInventory.getInventory();
        ItemStack item = event.getCurrentItem();
        if (item != null) {
            if (!item.getType().equals(Material.AIR)) {
                if (event.getInventory().equals(inventory)) {
                    Map.Entry<Integer, HuntiesInventoryItem> i = huntiesInventory.getInvItemMap().entrySet().stream().filter(e -> e.getValue().getItemStack().equals(item)).findFirst().orElse(null);
                    if (i != null) i.getValue().getClickEventConsumer().accept(event);
                    event.setCancelled(true);
                }
            }
        }
    }
}

class HuntiesInventoryItem {

    private ItemStack itemStack;

    private Consumer<InventoryClickEvent> clickEventConsumer;

    public HuntiesInventoryItem(ItemStack itemStack, Consumer<InventoryClickEvent> clickEventConsumer) {
        this.itemStack = itemStack;
        this.clickEventConsumer = clickEventConsumer;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public Consumer<InventoryClickEvent> getClickEventConsumer() {
        return clickEventConsumer;
    }
}

