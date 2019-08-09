package fr.tarkod.kitpvp.utils;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BroadcastRunnable extends BukkitRunnable {

    List<String> stringName = new ArrayList<>();

    @Override
    public void run() {
        Bukkit.broadcastMessage(stringName.get(new Random().nextInt(stringName.size())));
    }
}
