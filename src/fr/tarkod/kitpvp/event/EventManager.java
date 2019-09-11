package fr.tarkod.kitpvp.event;

import fr.tarkod.kitpvp.KitPvP;
import fr.tarkod.kitpvp.event.custom.boss.EventBoss;
import fr.tarkod.kitpvp.event.custom.doubleall.EventDoubleAll;
import fr.tarkod.kitpvp.event.custom.pvpbox.EventPvPBox;
import fr.tarkod.kitpvp.event.custom.rabbit.EventRabbit;
import fr.tarkod.kitpvp.event.custom.soulpotion.EventSoulPotion;
import fr.tarkod.kitpvp.event.custom.speedfight.EventSpeedFight;
import fr.tarkod.kitpvp.event.gui.EventGui;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.HandlerList;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class EventManager {

    private List<Event> events = new ArrayList<>();

    private KitPvP main;
    private Event eventLaunched = null;
    private BukkitRunnable bukkitRunnable;

    private EventGui eventGui;

    public EventManager(KitPvP main) {
        this.main = main;
        this.eventGui = new EventGui(main);

        loadEvents();
    }

    private void loadEvents() {
        events.addAll(Arrays.asList(
                new EventSpeedFight("SpeedEvent", "Tout le monde reçoit Speed III", Material.SUGAR, 120, main),
                new EventRabbit("LAPIN", "Une queue commence à pousser derrière toi et MAIS TU PEUX SAUTER HAUT", Material.RABBIT_FOOT, 120, main),
                new EventBoss("BOSS", "Le 1er qui le tue gagne 1000$, bonne chance !", Material.ROTTEN_FLESH, 120, main),
                new EventPvPBox("PvPBox", "Qui sera le dernier ? (Le gagnant gagne 1000$)", Material.IRON_SWORD, 10, main),
                new EventSoulPotion("Potion d'âme", "Chaque kill vous donnera un niveau d'un effet de potion aléatoire !", Material.POTION, 5*60, main),
                new EventDoubleAll("Double tout", "Tout est doublé !", Material.BLAZE_POWDER, 2*60, main)
        ));
    }

    public List<Event> getRegisteredEvents() {
        return events;
    }

    public void launchRandomEvent() {
        launchEvent(events.get(new Random().nextInt(events.size())));
    }

    public boolean launchEventByName(String name) {
        Event event = events.stream().filter(e -> e.getName().equalsIgnoreCase(name)).findFirst().orElse(null);

        if (event != null){
            launchEvent(event);

            return true;
        } else {
            return false;
        }
    }

    public void launchEvent(Event event) {
        // don't forger to stop the actual event
        stopEvent();

        // load event
        eventLaunched = event;
        main.getServer().getPluginManager().registerEvents(event, main);

        // load event runnable
        loadRunnable();
        bukkitRunnable.runTaskTimer(main, 0, 20);

        // start event
        event.run();
    }

    private void loadRunnable() {
        this.bukkitRunnable = new BukkitRunnable() {

            private int timeLeft = eventLaunched.getMaxTime();

            @Override
            public void run() {
                eventLaunched.everySecond();

                if (5 >= timeLeft && timeLeft > 0) {
                    Bukkit.broadcastMessage("L'event se termine dans " + ChatColor.AQUA + timeLeft + "s.");
                }

                if (timeLeft == 0) {
                    eventLaunched.stop();
                }

                timeLeft--;
            }
        };
    }

    public void stopEvent() {
        if (eventLaunched != null) {
            // stop event
            eventLaunched.stop();
            bukkitRunnable.cancel();

            // unload event
            HandlerList.unregisterAll(eventLaunched);
            eventLaunched = null;
        }
    }

    public EventGui getGui() {
        return eventGui;
    }
}