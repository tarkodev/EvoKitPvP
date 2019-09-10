package fr.tarkod.kitpvp.event;

import fr.tarkod.kitpvp.KitPvP;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.Listener;

public abstract class Event implements Listener {

    private KitPvP main;

    private String name;
    private String description;
    private Material material;

    private int maxTime;

    abstract public void everySecond();
    abstract public void onEnable();
    abstract public void onDisable();

    public Event(String name, String description, Material material, int maxTime, KitPvP main) {
        this.main = main;
        this.name = name;
        this.description = description;
        this.material = material;
        this.maxTime = maxTime;
    }

    public void run() {
        Bukkit.broadcastMessage(
                ChatColor.GOLD + "[EVENT] " + ChatColor.AQUA + "L'event " + name + " commence" + "\n" +
                        ChatColor.AQUA + getDescription()
        );

        onEnable();
    }

    public void stop() {
        Bukkit.broadcastMessage(ChatColor.GOLD + "[EVENT] " + ChatColor.AQUA + "L'event " + name + " est terminé");
        onDisable();
    }

    public KitPvP getMain(){
        return main;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getMaxTime() {
        return maxTime;
    }

    public EventManager getEventManager(){
        return main.getEventManager();
    }

    public Material getMaterial() {
        return material;
    }
}
