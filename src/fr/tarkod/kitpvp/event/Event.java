package fr.tarkod.kitpvp.event;

import fr.tarkod.kitpvp.KitPvP;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.HandlerList;
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

<<<<<<< HEAD
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
=======
    public void run() {
>>>>>>> e1d8667eb15692e6b15799bbaaa0aff4db8a9c2d
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
