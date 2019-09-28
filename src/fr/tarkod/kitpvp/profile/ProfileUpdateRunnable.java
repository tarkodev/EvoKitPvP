package fr.tarkod.kitpvp.profile;

import fr.tarkod.kitpvp.KitPvP;
import org.bukkit.scheduler.BukkitRunnable;

public class ProfileUpdateRunnable extends BukkitRunnable {

    private KitPvP main;

    public ProfileUpdateRunnable(KitPvP main) {
        this.main = main;
    }

    @Override
    public void run() {
        main.getDataManager().getProfileManager().getUuidProfileMap().values().forEach(profile -> {
            main.getLevelManager().update(profile);
        });
    }
}
