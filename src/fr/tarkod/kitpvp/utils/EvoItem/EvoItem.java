package fr.tarkod.kitpvp.utils.EvoItem;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.function.Consumer;

public class EvoItem {

    private ItemStack itemStack;

    private Consumer<PlayerInteractEvent> playerInteractEventConsumer;
    private JavaPlugin main;

    public EvoItem(ItemStack itemStack, Consumer<PlayerInteractEvent> playerInteractEventConsumer, JavaPlugin main) {
        this.itemStack = itemStack;
        this.playerInteractEventConsumer = playerInteractEventConsumer;
        this.main = main;
        defaultLoad();
    }

    public EvoItem(Material material, Consumer<PlayerInteractEvent> playerInteractEventConsumer, JavaPlugin main) {
        this.itemStack = new ItemStack(material);
        this.playerInteractEventConsumer = playerInteractEventConsumer;
        this.main = main;
        defaultLoad();
    }

    public void defaultLoad(){
        Bukkit.getPluginManager().registerEvents(new EvoItemListener(this), main);
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public Consumer<PlayerInteractEvent> getPlayerInteractEvent() {
        return playerInteractEventConsumer;
    }
}
