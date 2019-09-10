package fr.tarkod.kitpvp.event;

import fr.tarkod.kitpvp.KitPvP;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;

public abstract class Event implements Listener {

    private KitPvP main;

    private String name;
    private String description;
    private Material material;

    private int maxTime;
    private boolean status;
    private BukkitRunnable bukkitRunnable;

    abstract public void everySecond();
    abstract public void onEnable();
    abstract public void onDisable();

    public Event(String name, String description, Material material, int maxTime, KitPvP main) {
        this.main = main;
        this.name = name;
        this.description = description;
        this.material = material;
        this.maxTime = maxTime;
        loadRunnable();
    }

    private void loadRunnable(){
        this.bukkitRunnable = new BukkitRunnable(){

            private int timeLeft = maxTime;

            @Override
            public void run() {
                if(isEnable()) {
                    everySecond();
                    if (5 >= timeLeft && timeLeft > 0) {
                        Bukkit.broadcastMessage("L'event fini dans " + ChatColor.AQUA + timeLeft + "s");
                    }
                    if (timeLeft == maxTime) {
                        enable();
                    }
                    if (timeLeft == 0) {
                        disable();
                    }
                    timeLeft--;
                }
            }
        };
    }

    public void run(){
        if(!isEnable()) {
            main.getServer().getPluginManager().registerEvents(this, main);
            status = true;
            loadRunnable();
            bukkitRunnable.runTaskTimer(main, 0, 20);
        }
    }

    public void stop(){
        if(isEnable()){
            HandlerList.unregisterAll(this);
            disable();
        }
    }

    private void enable(){
        Bukkit.broadcastMessage(
                ChatColor.GOLD + "[EVENT] " + ChatColor.AQUA + "L'event " + name + " commence" + "\n" +
                ChatColor.AQUA + getDescription()
        );
        status = true;
        onEnable();
    }

    private void disable(){
        Bukkit.broadcastMessage(ChatColor.GOLD + "[EVENT] " + ChatColor.AQUA + "L'event " + name + " est terminé");
        status = false;
        bukkitRunnable.cancel();
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

    public boolean isEnable() {
        return status;
    }

    public EventManager getEventManager(){
        return main.getEventManager();
    }

    public Material getMaterial() {
        return material;
    }
}
