package fr.tarkod.kitpvp.listeners;

import fr.tarkod.kitpvp.KitPvP;
import fr.tarkod.kitpvp.listeners.custom.EGPlayerRespawnEvent;
import fr.tarkod.kitpvp.utils.EvoItem.EvoItem;
import fr.tarkod.kitpvp.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;

public class PlayerRespawn implements Listener {

    private KitPvP main;

    public PlayerRespawn(KitPvP main) {
        this.main = main;
    }

    @EventHandler
    public void onRespawn(EGPlayerRespawnEvent event){
        Player player = event.getPlayer();
        player.getInventory().setItem(0, main.getItemLibrary().kitSelectItem.getItemStack());
    }
}
