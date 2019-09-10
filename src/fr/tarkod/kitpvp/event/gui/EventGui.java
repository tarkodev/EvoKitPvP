package fr.tarkod.kitpvp.event.gui;

import fr.tarkod.kitpvp.KitPvP;
import fr.tarkod.kitpvp.event.Event;
import fr.tarkod.kitpvp.event.EventManager;
import fr.tarkod.kitpvp.utils.EvoInventory.EvoInvItem;
import fr.tarkod.kitpvp.utils.EvoInventory.EvoInventory;
import fr.tarkod.kitpvp.utils.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class EventGui {

    private KitPvP main;
    private EvoInventory inventory;

    public EventGui(KitPvP main) {
        this.main = main;
        this.inventory = new EvoInventory("Sélection d'Event", 27, main);
    }

    public void open(Player player) {
        load();
        player.openInventory(inventory.getInventory());
    }

    private void load() {
        EventManager eventManager = main.getEventManager();

        // Set all event items
        for (int i = 0; i < eventManager.getRegisteredEvents().size(); i++) {
            Event event = eventManager.getRegisteredEvents().get(i);
            inventory.setItem(i, new EvoInvItem(new ItemBuilder(event.getMaterial())
                    .setName(ChatColor.GOLD + event.getName())
                    .setLore(ChatColor.AQUA + event.getDescription())
                    .toItemStack(),
                    e -> eventManager.launchEvent(event)));
        }

        // Set cancel event item
        inventory.setItem(26, new EvoInvItem(new ItemBuilder(Material.BARRIER).setName(ChatColor.RED + "Arreter les events en cours").toItemStack(), e -> eventManager.stopEvent()));
    }
}
