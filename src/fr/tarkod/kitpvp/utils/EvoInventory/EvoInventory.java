package fr.tarkod.kitpvp.utils.EvoInventory;

import org.bukkit.Bukkit;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;

public class EvoInventory {

    private transient JavaPlugin main;
    private transient Inventory inventory;
    private transient EvoInventoryListener oldListener;

    private String name;
    private int size;
    private Map<Integer, EvoInvItem> invItemMap;

    public EvoInventory(String name, int size, JavaPlugin main) {
        this.name = name;
        this.size = size;
        this.main = main;
        this.invItemMap = new HashMap<>();
        defaultLoad(main);
    }

    public void defaultLoad(JavaPlugin main) {
        this.main = main;
        this.inventory = Bukkit.createInventory(null, this.size, this.name);
        update();
    }

    public void setItem(int slot, EvoInvItem item) {
        this.invItemMap.put(slot, item);
        update();
    }

    public void clear(){
        this.invItemMap.clear();
        update();
    }

    public void removeItem(int slot){
        this.invItemMap.remove(slot);
    }

    public void update() {
        registerItem();
        registerEvent();
    }

    private void registerItem() {
        this.invItemMap.forEach((slot, item)-> inventory.setItem(slot, item.getItemStack()));
    }

    private void registerEvent(){
        if(oldListener != null) InventoryClickEvent.getHandlerList().unregister(oldListener);
        EvoInventoryListener event = new EvoInventoryListener(this);
        Bukkit.getServer().getPluginManager().registerEvents(
                event,
                main
        );
        oldListener = event;
    }

    public String getName() {
        return name;
    }

    public int getSize() {
        return size;
    }

    public Map<Integer, EvoInvItem> getInvItemMap() {
        return invItemMap;
    }

    public Inventory getInventory() {
        return inventory;
    }
}
