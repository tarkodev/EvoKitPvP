package fr.tarkod.kitpvp.listeners;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.permissions.Permission;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import fr.tarkod.kitpvp.KitPvP;
import fr.tarkod.kitpvp.listeners.custom.EGPlayerDeathByEntityEvent;
import fr.tarkod.kitpvp.profile.Profile;
import ru.tehkode.permissions.bukkit.PermissionsEx;

public class EntityDamageByEntity implements Listener {
	
	private KitPvP main;
	
	public EntityDamageByEntity(KitPvP main) {
		this.main = main;
	}
	
	/*@EventHandler
	public void onEntityDamageByEntity(EntityDamageByEntityEvent e) {
		if(e.getEntity() instanceof Player) {
			Player victim = (Player) e.getEntity();
			World world = victim.getWorld();
			if((victim.getHealth() - e.getFinalDamage()) <= 0) {
				e.setCancelled(true);
				if(e.getDamager() instanceof Player || ((Projectile) e.getDamager()).getShooter() instanceof Player) {
					Player damager = null;
					if(e.getDamager() instanceof Player) {
						damager = (Player) e.getDamager();
					} else
					if(((Projectile) e.getDamager()).getShooter() instanceof Player) {
						damager = (Player) ((Projectile) e.getDamager()).getShooter();
					}
					Profile damagerProfile = main.getProfiles().get(damager.getUniqueId());
					reward(damager, victim);
					damagerProfile.getLevelManager().update();
					victim.getWorld().dropItem(victim.getLocation(), ItemLibrary.GOLDEN_APPLE);
				}
			}
		}
	}*/
	
	@EventHandler
	public void onPlayerDeathByEntity(EntityDamageByEntityEvent e) {
		if(e.getEntity() instanceof Player) {
			Player victim = (Player) e.getEntity();
			Player killer = null;
			if((victim.getHealth() - e.getFinalDamage()) <= 0) {
				e.setCancelled(true);
				
				if(e.getDamager() instanceof Projectile) {
					if(((Projectile) e.getDamager()).getShooter() instanceof Player) {
						killer = (Player) ((Projectile) e.getDamager()).getShooter();
					}
				}
				
				if(e.getDamager() instanceof Player) {
					killer = (Player) e.getDamager();
				}

				if(killer == victim){
					e.setCancelled(true);
					return;
				}
				
				EGPlayerDeathByEntityEvent event = new EGPlayerDeathByEntityEvent(victim, killer, e.getFinalDamage());
				Bukkit.getPluginManager().callEvent(event);
				
				if(event.getKiller() instanceof Player) {
					
					Profile killerProfile = main.getDataManager().getProfileManager().get(event.getKiller().getUniqueId());
						
					if(event.isGappleLoot()) {
						//victim.getWorld().dropItem(victim.getLocation(), ItemLibrary.GOLDEN_APPLE);
					}
					
					StringBuilder sbKillerMessage = new StringBuilder();
					
					sbKillerMessage.append("Vous venez de tuer " + ChatColor.translateAlternateColorCodes('&',PermissionsEx.getUser(victim).getPrefix()) + victim.getName());
					
					killerProfile.setKill(killerProfile.getKill() + 1);
					killerProfile.setKillStreak(killerProfile.getKillStreak() + 1);
					if(killerProfile.getBestKillStreak() < killerProfile.getKillStreak()) {
						killerProfile.setBestKillStreak(killerProfile.getKillStreak());
					}
					
					if(event.getDroppedMoney() > 0) {
						sbKillerMessage.append(ChatColor.GOLD + " +" + event.getDroppedMoney() + "€");
						killerProfile.setMoney(killerProfile.getMoney() + event.getDroppedMoney());
					}
					if(event.getDroppedExperience() > 0) {
						sbKillerMessage.append(ChatColor.LIGHT_PURPLE + " +" + event.getDroppedExperience() + "xp");
						killerProfile.setExperience(killerProfile.getExperience() + event.getDroppedExperience());
					}
					
					killerProfile.getLevelManager().update();
					killer.playSound(killer.getLocation(), Sound.ORB_PICKUP, 2, 2);

					killer.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 4*20, 0));
					
					if(killer.getHealth() + 6 > killer.getMaxHealth()) {
						killer.setHealth(killer.getMaxHealth());
					} else {
						killer.setHealth(killer.getHealth() + 6);
					}
					
					event.getKiller().sendMessage(sbKillerMessage.toString());
					victim.sendMessage("Vous avez été tué par " + ChatColor.translateAlternateColorCodes('&', PermissionsEx.getUser(killer).getPrefix()) + event.getKiller().getName());
				}
			}
		}
	}
}