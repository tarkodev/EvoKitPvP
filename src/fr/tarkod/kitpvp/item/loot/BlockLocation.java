package fr.tarkod.kitpvp.item.loot;

import org.bukkit.Bukkit;
import org.bukkit.Location;

public class BlockLocation {
	
	private int x;
	private int y;
	private int z;
	
	public BlockLocation(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public BlockLocation(Location location){
		this.x = location.getBlockX();
		this.y = location.getBlockY();
		this.z = location.getBlockZ();
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getZ() {
		return z;
	}

	public Location getLocation(String world){
		return new Location(Bukkit.getWorld(world), x, y, z);
	}
}
