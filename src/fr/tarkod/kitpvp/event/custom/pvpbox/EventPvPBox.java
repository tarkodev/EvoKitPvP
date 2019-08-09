package fr.tarkod.kitpvp.event.custom.pvpbox;

import fr.tarkod.kitpvp.KitPvP;
import fr.tarkod.kitpvp.event.Event;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.Listener;

public class EventPvPBox extends Event implements Listener {

    public EventPvPBox(String name, String description, Material material, int maxTime, KitPvP main) {
        super(name, description, material, maxTime, main);
    }

    @Override
    public void everySecond() {

    }

    @Override
    public void onEnable() {
        Location location = new Location(Bukkit.getWorld("world"), 1.5, 9, 10.5);
        Bukkit.getOnlinePlayers().forEach(player -> player.teleport(location));
        getMain().getEventManager().stopEvent();
    }

    @Override
    public void onDisable() {

    }
}
