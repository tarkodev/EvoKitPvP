package fr.tarkod.kitpvp.profile.atm;

import fr.tarkod.kitpvp.KitPvP;
import org.bukkit.scheduler.BukkitRunnable;

public class ATMRunnable extends BukkitRunnable {

    private KitPvP main;

    public ATMRunnable(KitPvP main) {
        this.main = main;
    }

    @Override
    public void run() {
        main.getDataManager().getProfileManager().getUuidProfileMap().values().forEach(profile -> profile.getAtmManager().setLastATM(profile.getAtmManager().getLastATM() + 1));
    }
}
