package fr.tarkod.kitpvp.event.gui;

import fr.tarkod.kitpvp.KitPvP;
import fr.tarkod.kitpvp.event.Event;
import fr.tarkod.kitpvp.event.EventManager;
import fr.tarkod.kitpvp.utils.HuntiesInventory.HuntiesInventory;
import fr.tarkod.kitpvp.utils.ItemBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class EventGui extends HuntiesInventory {

    private KitPvP main;

    public EventGui(KitPvP main) {
        super(9*3, "Sélection d'Event", main);
        this.main = main;
    }

    public void load() {
        EventManager eventManager = main.getEventManager();

        // Set all event items
        for (int i = 0; i < eventManager.getRegisteredEvents().size(); i++) {
            Event event = eventManager.getRegisteredEvents().get(i);
            setItem(i, new ItemBuilder(event.getMaterial())
                    .setName(ChatColor.GOLD + event.getName())
                    .setLore(ChatColor.AQUA + event.getDescription())
                    .toItemStack(),
                    e -> eventManager.launchEvent(event));
        }

        // Set cancel event item
        setItem(26, new ItemBuilder(Material.BARRIER).setName(ChatColor.RED + "Arreter les events en cours").toItemStack(), e -> eventManager.stopEvent());
    }
}
