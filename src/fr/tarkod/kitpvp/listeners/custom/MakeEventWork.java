package fr.tarkod.kitpvp.listeners.custom;

import fr.tarkod.kitpvp.profile.Profile;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

import fr.tarkod.kitpvp.KitPvP;
import fr.tarkod.kitpvp.utils.timer.RespawnTimer;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.HashSet;
import java.util.Set;

public class MakeEventWork implements Listener {

	private KitPvP main;

	public MakeEventWork(KitPvP main) {
		this.main = main;
	}

	@EventHandler(priority = EventPriority.HIGH)
	public void onPlayerDeathByCause(EntityDamageEvent e) {
		if(e.getEntity() instanceof Player) {
			Player victim = (Player) e.getEntity();
			Profile profile = main.getDataManager().getProfileManager().get(victim.getUniqueId());
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
				Set<Profile> assistList = main.getFightManager().getAssistManager().getAssistMap().containsKey(profile) ? main.getFightManager().getAssistManager().getAssistMap().get(profile).keySet() : new HashSet<>();
				assistList.remove(profile);
				e.setCancelled(true);
				EGPlayerDeathEvent event = new EGPlayerDeathEvent(victim, assistList, e.getCause(), e.getFinalDamage());
				Bukkit.getPluginManager().callEvent(event);

				assistList.forEach(damager -> {
					damager.getPlayer().sendMessage("Tu as participé à la mort de " + victim.getName() + ChatColor.GOLD + " +" + "8" + "$" + ChatColor.LIGHT_PURPLE + " +" + "1" + "xp");
					damager.setMoney(damager.getMoney() + 8);
					damager.setMoney(damager.getExperience() + 1);
				});

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