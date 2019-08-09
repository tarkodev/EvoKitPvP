package fr.tarkod.kitpvp.event;

import fr.tarkod.kitpvp.KitPvP;
import fr.tarkod.kitpvp.event.custom.boss.EventBoss;
import fr.tarkod.kitpvp.event.custom.pvpbox.EventPvPBox;
import fr.tarkod.kitpvp.event.custom.rabbit.EventRabbit;
import fr.tarkod.kitpvp.event.custom.speedfight.EventSpeedFight;
import fr.tarkod.kitpvp.event.gui.EventGui;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class EventManager {

    private KitPvP main;
    private List<Event> events;
    private Event eventLaunched;

    private EventGui eventGui;

    public EventManager(KitPvP main) {
        this.main = main;
        this.events = new ArrayList<>();
        this.eventGui = new EventGui(main);
        loadEvents();
    }

    private void loadEvents(){
        Arrays.asList(
                new EventSpeedFight("SpeedEvent", "Tout le monde reçoit Speed III", Material.SUGAR, 120, main),
                new EventRabbit("LAPIN", "Une queue commence à pousser derrière toi et MAIS TU PEUX SAUTER HAUT", Material.RABBIT_FOOT,120, main),
                new EventBoss("BOSS", "Le 1er qui le tue gagne 1000$, bonne chance !", Material.ROTTEN_FLESH, 120, main),
                new EventPvPBox("PvPBox", "Qui sera le dernier ? (Le gagnant gagne 1000$)", Material.IRON_SWORD, 10, main)
        ).forEach(this::registerEvent);
    }

    public void registerEvent(Event event){
        events.add(event);
        main.getServer().getPluginManager().registerEvents(event, main);
    }

    public List<Event> getRegistedEvents(){
        return events;
    }

    public void launchRandomEvent(){
        launchEvent(events.get(new Random().nextInt(events.size())));
    }

    public boolean launchEventByName(String name) {
        Event event = events.stream().filter(e -> e.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
        if(event != null){
            launchEvent(event);
            return true;
        } else {
            return false;
        }
    }

    public void launchEvent(Event event){
        stopEvent();
        eventLaunched = event;
        event.run();
    }

    public void stopEvent(){
        if(eventLaunched != null && eventLaunched.isEnable()) {
            eventLaunched.stop();
        }
    }

    public EventGui getGui(){
        return eventGui;
    }
}