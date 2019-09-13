package fr.tarkod.kitpvp.combat;

import fr.tarkod.kitpvp.KitPvP;
import fr.tarkod.kitpvp.combat.assist.AssistManager;
import fr.tarkod.kitpvp.profile.Profile;
import org.bukkit.Bukkit;
import org.bukkit.PortalType;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;


public class FightManager implements Listener {

    private KitPvP main;

    private AssistManager assistManager;
    private ConcurrentMap<Profile, Integer> profileIntegerMap = new ConcurrentHashMap<>();

    public FightManager(KitPvP main) {
        this.main = main;
        this.assistManager = new AssistManager(main);
        runnable();
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onDamage(EntityDamageByEntityEvent event) {
        if(event.getEntity() instanceof Player && event.getDamager() instanceof Player){

            Player victim = (Player) event.getEntity();
            Profile victimProfile = main.getDataManager().getProfileManager().get(victim.getUniqueId());
            Player damager = (Player) event.getDamager();
            Profile damagerProfile = main.getDataManager().getProfileManager().get(damager.getUniqueId());

            profileIntegerMap.put(victimProfile, 17);
            profileIntegerMap.put(damagerProfile, 17);
        }
    }

    public boolean isFighting(Profile profile) {
        return profileIntegerMap.containsKey(profile);
    }

    public void setTimeLeft(int time, Profile profile) {
        profileIntegerMap.put(profile, time);
    }

    public int getTimeLeft(Profile profile) {
        return profileIntegerMap.get(profile);
    }

    public void runnable() {
        new BukkitRunnable(){
            @Override
            public void run() {
                profileIntegerMap.forEach((profile, integer) -> {
                    profileIntegerMap.put(profile, integer - 1);
                    if(integer <= 0) profileIntegerMap.remove(profile);
                });
            }
        }.runTaskTimer(main, 0, 20);
    }

    public AssistManager getAssistManager() {
        return assistManager;
    }

    /*public void runnable() {
        new BukkitRunnable(){
            @Override
            public void run() {
                main.getDataManager().getProfileManager().getUuidProfileMap().values().stream()
                        .filter(profile -> profile.getFight().getTimeLeft() > 0)
                        .forEach(profile -> profile.getFight().setTimeLeft(profile.getFight().getTimeLeft() - 1));
            }
        }.runTaskTimer(main, 0, 20);
    }*/
}
