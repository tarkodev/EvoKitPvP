package fr.tarkod.kitpvp.combat;

import fr.tarkod.kitpvp.KitPvP;
import fr.tarkod.kitpvp.profile.Profile;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.scheduler.BukkitRunnable;


public class FightManager implements Listener {

    private KitPvP main;

    public FightManager(KitPvP main) {
        this.main = main;
        runnable();
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onDamage(EntityDamageByEntityEvent event){
        if(event.getEntity() instanceof Player && event.getDamager() instanceof Player){
            Player victim = (Player) event.getEntity();
            Profile victimProfile = main.getDataManager().getProfileManager().get(victim.getUniqueId());
            Player damager = (Player) event.getDamager();
            Profile damagerProfile = main.getDataManager().getProfileManager().get(damager.getUniqueId());
            victimProfile.getFight().setTimeLeft(17);
            damagerProfile.getFight().setTimeLeft(17);
        }
    }

    public void runnable(){
        new BukkitRunnable(){
            @Override
            public void run() {
                main.getDataManager().getProfileManager().getUuidProfileMap().values().stream()
                        .filter(profile -> profile.getFight().getTimeLeft() > 0)
                        .forEach(profile -> profile.getFight().setTimeLeft(profile.getFight().getTimeLeft() - 1));
            }
        }.runTaskTimer(main, 0, 20);
    }
}
