package fr.tarkod.kitpvp.item.loot;

import fr.tarkod.kitpvp.item.KitPvPItem;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import java.util.ArrayList;
import java.util.List;

public class LootProfile {

    private List<KitPvPItem> listItem;
    private List<BlockLocation> locationList;
    private String world;
    private boolean isEnabled;

    public LootProfile(String world) {
        this.locationList = new ArrayList<>();
        this.listItem = new ArrayList<>();
        this.world = world;
        this.isEnabled = true;
    }

    public List<Location> getLocationList() {
        List<Location> locList = new ArrayList<Location>();
        locationList.forEach(loc -> locList.add(new Location(getWorld(), loc.getX(), loc.getY(), loc.getZ())));
        return locList;
    }

    public List<BlockLocation> getLootLocationList() {
        return locationList;
    }

    public World getWorld() {
        return Bukkit.getWorld(world);
    }

    public boolean isEnable() {
        return isEnabled;
    }

    public void setEnabled(boolean isEnabled) {
        this.isEnabled = isEnabled;
    }

    public List<KitPvPItem> getListItem() {
        return listItem;
    }
}
