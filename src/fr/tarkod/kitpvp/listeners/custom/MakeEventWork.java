package fr.tarkod.kitpvp.listeners.custom;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

import fr.tarkod.kitpvp.KitPvP;
import fr.tarkod.kitpvp.timer.RespawnTimer;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class MakeEventWork implements Listener {

	private KitPvP main;

	public MakeEventWork(KitPvP main) {
		this.main = main;
	}

	@EventHandler
	public void onPlayerDeathByCause(EntityDamageEvent e) {
		if(e.getEntity() instanceof Player) {
			Player victim = (Player) e.getEntity();
			PlayerInventory inventory = victim.getInventory();
			if(e.getCause().equals(DamageCause.FALL)) {
				e.setCancelled(true);
				return;
			}
			if(e.getCause().equals(DamageCause.VOID) && victim.getGameMode().equals(GameMode.SPECTATOR)) {
				e.setCancelled(true);
				return;
			}
			if((victim.getHealth() - e.getFinalDamage()) <= 0) {
				e.setCancelled(true);
				EGPlayerDeathEvent event = new EGPlayerDeathEvent(victim, e.getCause(), e.getFinalDamage());
				Bukkit.getPluginManager().callEvent(event);
				if(!(event.isCancelled())) {
		        	new RespawnTimer(victim, 3).runTaskTimer(main, 20, 20);
		        	ItemStack air = new ItemStack(Material.AIR);
		        	inventory.clear();
		        	inventory.setArmorContents(new ItemStack[]{air, air, air, air});
				}
			}
		}
	}
}