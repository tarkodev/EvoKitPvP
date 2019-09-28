package fr.tarkod.kitpvp.kit.chestselector;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import fr.tarkod.kitpvp.KitPvP;
import fr.tarkod.kitpvp.utils.HologramManager;

public class KitChest implements Listener {

    private Location loc;
    private HologramManager hm;

    public KitChest(Location loc) {
        this.loc = loc;
        this.hm = new HologramManager(loc, ChatColor.GOLD + "Cliquez ici pour ouvrir le menu de kits !");
        Bukkit.getPluginManager().registerEvents(this, KitPvP.getInstance());
        for(Player players : Bukkit.getOnlinePlayers()) {
            hm.add(players);
        }
        spawn();
    }

    public Location getLoc() {
        return loc;
    }

    public void setLoc(Location loc) {
        this.loc = loc;
    }

    public void spawn() {
        loc.getBlock().setType(Material.CHEST);
    }

    public void remove() {
        for(Player player : Bukkit.getOnlinePlayers()) {
            hm.remove(player);
        }
        loc.getBlock().setType(Material.AIR);
    }

    @EventHandler
    public void onInteractWithKitChest(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        if(e.getClickedBlock() == null) {
            return;
        }

        if(e.getClickedBlock().getLocation().getBlock().equals(loc.getBlock())) {
            if(e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
                e.setCancelled(true);
                player.performCommand("kit");
            }
        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        hm.add(player);
    }
}
