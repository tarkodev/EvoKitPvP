package fr.tarkod.kitpvp.utils;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import net.minecraft.server.v1_8_R3.EntityArmorStand;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityDestroy;
import net.minecraft.server.v1_8_R3.PacketPlayOutSpawnEntityLiving;

public class HologramManager {
	
	private EntityArmorStand hologram;
	private Location loc;
	private List<Player> playerList = new ArrayList<Player>();
	
	public HologramManager(Location loc, String customName) {
		this.loc = loc;
		defaultLoad(customName);
	}
	
	private void defaultLoad(String customName) {
		hologram = new EntityArmorStand(((CraftWorld) loc.getWorld()).getHandle(), loc.getX()+0.5, loc.getY()+1, loc.getZ()+0.5);
		
		hologram.setInvisible(true);
		hologram.setCustomNameVisible(true);
		hologram.setSmall(true);
		
		hologram.setCustomName(customName);
	}
	
	public EntityArmorStand getHologram() {
		return hologram;
	}
	
	public void add(Player player) {
		if(!(playerList.contains(player))) {
			playerList.add(player);
		}
		update(player);
	}
	
	public void remove(Player player) {
		if(playerList.contains(player)) {
			playerList.remove(player);
		}
		PacketPlayOutEntityDestroy packet = new PacketPlayOutEntityDestroy(hologram.getId());
		((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
	}
	
	public void update(Player player) {
		if(playerList.contains(player)) {
			PacketPlayOutSpawnEntityLiving packet = new PacketPlayOutSpawnEntityLiving(hologram);
			((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
		}
	}
	
	public void update() {
		playerList.forEach(player -> update(player));
	}
	
	public void reset() {
		for(Player player : playerList) {
			remove(player);
		}
	}
	
	public void setName(String customName) {
		hologram.setCustomName(customName);
		update();
	}
	
	public void setLocation(Location loc) {
		hologram.setLocation(loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch());
		update();
	}
	
	public boolean isVisible(Player player) {
		if(playerList.contains(player)) {
			return true;
		} else {
			return false;
		}
	}
}