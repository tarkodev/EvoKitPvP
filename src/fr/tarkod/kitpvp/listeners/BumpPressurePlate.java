package fr.tarkod.kitpvp.listeners;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.util.Vector;

public class BumpPressurePlate implements Listener {

    @EventHandler
    public void onBump(PlayerInteractEvent event){
        Player player = event.getPlayer();
        if(event.getAction() == Action.PHYSICAL){
            if(event.getClickedBlock().getType() == Material.STONE_PLATE){
                event.setCancelled(true);
                player.setVelocity(player.getLocation().getDirection().multiply(2).setY(1));
            }
        }
    }
}
