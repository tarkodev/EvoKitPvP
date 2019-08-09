package fr.tarkod.kitpvp.listeners.paytowin;

import fr.tarkod.kitpvp.KitPvP;
import fr.tarkod.kitpvp.listeners.custom.EGPlayerDeathByEntityEvent;
import fr.tarkod.kitpvp.profile.Profile;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import ru.tehkode.permissions.bukkit.PermissionsEx;

public class KillAdvantage implements Listener {

    private KitPvP main;

    public KillAdvantage(KitPvP main) {
        this.main = main;
    }

    @EventHandler
    public void onKillExperience(EGPlayerDeathByEntityEvent event){
        if(event.getKiller() instanceof Player) {
            Player killer = (Player) event.getKiller();
            Profile killerProfile = main.getDataManager().getProfileManager().get(killer.getUniqueId());
            if(PermissionsEx.getUser(killer).getOption("xp") != null){
                int i = Integer.parseInt( PermissionsEx.getUser(killer).getOption("xp"));
                event.setDroppedExperience(i);
            }
            if(killerProfile.getLevel() >= 100){
                event.setDroppedExperience(0);
                killer.sendMessage(ChatColor.RED + "Tu es level max, /prestige pour recommencer à gagner de l'experience !");
            }
        }
    }

    @EventHandler
    public void onKillMoney(EGPlayerDeathByEntityEvent event){
        if(event.getKiller() instanceof Player) {
            Player killer = (Player) event.getKiller();
            if(PermissionsEx.getUser(killer).getOption("money") != null){
                int i = Integer.parseInt( PermissionsEx.getUser(killer).getOption("money"));
                event.setDroppedMoney(i);
            }
        }
    }
}
