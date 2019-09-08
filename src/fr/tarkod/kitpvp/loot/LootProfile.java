package fr.tarkod.kitpvp.loot;

import java.util.ArrayList;
import java.util.List;

import fr.tarkod.kitpvp.item.loot.BlockLocation;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

public class LootProfile {
	
	private List<BlockLocation> locationList;
	private String world;
	private boolean isEnabled;
	
	public LootProfile(List<BlockLocation> locationList, String world, boolean isEnabled) {
		this.locationList = locationList;
		this.world = world;
		this.isEnabled = isEnabled;
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
	
	public boolean isEnabled() {
		return isEnabled;
	}
	
	public void setEnabled(boolean isEnabled) {
		this.isEnabled = isEnabled;
	}
}
