package fr.tarkod.kitpvp.loot;

import fr.tarkod.kitpvp.KitPvP;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;
import java.util.Random;

public class LootRunnable extends BukkitRunnable {

    private KitPvP main;

    private int tick;

    public LootRunnable(KitPvP main) {
        this.main = main;
    }

    @Override
    public void run() {
        if(Bukkit.getOnlinePlayers().size() >= 1) {
            tick++;
        }

        if(tick == 60*5) {
            tick = 0;
            LootManager lm = main.getDataManager().getLootManager();
            LootProfile lp = lm.getLootProfile();
            List<Location> locList = lp.getLocationList();
            if(locList.size() > 0) {
                Loot loot = new Loot(locList.get(new Random().nextInt(locList.size())), main);
                loot.spawn(60*2);
                lm.add(loot);
            } else {
                Bukkit.getConsoleSender().sendMessage("Please add a lot location.");
            }
        }
    }
}