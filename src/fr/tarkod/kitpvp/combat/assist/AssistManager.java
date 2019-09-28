package fr.tarkod.kitpvp.combat.assist;

import fr.tarkod.kitpvp.KitPvP;
import fr.tarkod.kitpvp.profile.Profile;
import fr.tarkod.kitpvp.profile.ProfileManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class AssistManager implements Listener {

    private KitPvP main;

    private ConcurrentMap<Profile, ConcurrentMap<Profile, Integer>> assistMap = new ConcurrentHashMap<>();

    public AssistManager(KitPvP main){
        this.main = main;
        main.getServer().getPluginManager().registerEvents(this, main);
        runnable();
    }

    public void runnable(){
        new BukkitRunnable(){

            @Override
            public void run() {
                assistMap.forEach((profile, profileIntegerMap) -> {
                    profileIntegerMap.forEach((damager, integer) -> {
                        profileIntegerMap.put(damager, integer - 1);
                        if(profileIntegerMap.get(damager) <= 0) profileIntegerMap.remove(damager);
                    });
                    if(profileIntegerMap.size() == 0) assistMap.remove(profile);
                });
            }
        }.runTaskTimer(main, 20, 20);
    }

    public void addAssist(Profile profile, Profile damager, int time) {
        assistMap.putIfAbsent(profile, new ConcurrentHashMap<>());
        assistMap.get(profile).put(damager, time);
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event){
        if(event.getEntity() instanceof Player && event.getDamager() instanceof Player) {
            ProfileManager profileManager = main.getDataManager().getProfileManager();
            Player victim = (Player) event.getEntity();
            Profile victimProfile = profileManager.get(victim.getUniqueId());
            Player damager = (Player) event.getDamager();
            Profile damagerProfile = profileManager.get(damager.getUniqueId());

            addAssist(victimProfile, damagerProfile, 7);
        }
    }

    public ConcurrentMap<Profile, ConcurrentMap<Profile, Integer>> getAssistMap(){
        return assistMap;
    }
}
