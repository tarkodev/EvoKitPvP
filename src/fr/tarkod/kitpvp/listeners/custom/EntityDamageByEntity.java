package fr.tarkod.kitpvp.listeners.custom;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.permissions.Permission;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import fr.tarkod.kitpvp.KitPvP;
import fr.tarkod.kitpvp.listeners.custom.EGPlayerDeathByEntityEvent;
import fr.tarkod.kitpvp.profile.Profile;
import ru.tehkode.permissions.bukkit.PermissionsEx;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class EntityDamageByEntity implements Listener {
	
	private KitPvP main;
	
	public EntityDamageByEntity(KitPvP main) {
		this.main = main;
	}
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerDeathByEntity(EntityDamageByEntityEvent e) {
		if(!(e.getEntity() instanceof Player)){
			return;
		}
		Player victim = (Player) e.getEntity();

		Player killer = null;
		if((victim.getHealth() - e.getFinalDamage()) <= 0) {
			e.setCancelled(true);

			if (e.getDamager() instanceof Projectile) {
				if (((Projectile) e.getDamager()).getShooter() instanceof Player) {
					killer = (Player) ((Projectile) e.getDamager()).getShooter();
				}
			}

			if (e.getDamager() instanceof Player) {
				killer = (Player) e.getDamager();
			}

			if (killer == victim) {
				e.setCancelled(true);
				return;
			}

			Profile killerProfile = main.getDataManager().getProfileManager().get(killer.getUniqueId());
			Profile victimProfile = main.getDataManager().getProfileManager().get(victim.getUniqueId());

			Set<Profile> assistList = main.getFightManager().getAssistManager().getAssistMap().containsKey(victimProfile) ? main.getFightManager().getAssistManager().getAssistMap().get(victimProfile).keySet() : new HashSet<>();
			assistList.remove(killerProfile);

			EGPlayerDeathByEntityEvent event = new EGPlayerDeathByEntityEvent(victim, killer, e.getFinalDamage());
			Bukkit.getPluginManager().callEvent(event);


			StringBuilder sbKillerMessage = new StringBuilder();

			sbKillerMessage.append("Vous venez de tuer " + ChatColor.translateAlternateColorCodes('&', PermissionsEx.getUser(victim).getPrefix()) + victim.getName());

			killerProfile.setKill(killerProfile.getKill() + 1);
			killerProfile.setKillStreak(killerProfile.getKillStreak() + 1);
			if (killerProfile.getBestKillStreak() < killerProfile.getKillStreak()) {
				killerProfile.setBestKillStreak(killerProfile.getKillStreak());
			}

			if (event.getDroppedMoney() > 0) {
				sbKillerMessage.append(ChatColor.GOLD + " +" + event.getDroppedMoney() + "$");
				killerProfile.setMoney(killerProfile.getMoney() + event.getDroppedMoney());
			}
			if (event.getDroppedExperience() > 0) {
				sbKillerMessage.append(ChatColor.LIGHT_PURPLE + " +" + event.getDroppedExperience() + "xp");
				killerProfile.setExperience(killerProfile.getExperience() + event.getDroppedExperience());
			}

			killer.playSound(killer.getLocation(), Sound.ORB_PICKUP, 2, 2);

			killer.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 4 * 20, 0));

			if (killer.getHealth() + 6 > killer.getMaxHealth()) {
				killer.setHealth(killer.getMaxHealth());
			} else {
				killer.setHealth(killer.getHealth() + 6);
			}

			event.getKiller().sendMessage(sbKillerMessage.toString());
			victim.sendMessage("Vous avez été tué par " + ChatColor.translateAlternateColorCodes('&', PermissionsEx.getUser(killer).getPrefix()) + event.getKiller().getName());
		}
	}
}