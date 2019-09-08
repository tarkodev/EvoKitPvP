package fr.tarkod.kitpvp.event.custom.boss;

import fr.tarkod.kitpvp.KitPvP;
import fr.tarkod.kitpvp.event.Event;
import fr.tarkod.kitpvp.loot.BlockLocation;
import fr.tarkod.kitpvp.profile.Profile;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.util.Random;

public class EventBoss extends Event implements Listener {

    private Zombie boss;

    public EventBoss(String name, String description, Material material, int maxTime, KitPvP main) {
        super(name, description, material, maxTime, main);
    }

    @Override
    public void everySecond() {

    }

    @Override
    public void onEnable() {
        BlockLocation blockLocation = getMain().getDataManager().getLootManager().getLootProfile().getLootLocationList().get(new Random().nextInt(getMain().getDataManager().getLootManager().getLootProfile().getLootLocationList().size()));
        Location location = blockLocation.getLocation("world");
        boss = (Zombie) Bukkit.getWorld("world").spawnEntity(location, EntityType.ZOMBIE);
        boss.setMaxHealth(1000);
        boss.setHealth(100);
        boss.setCustomNameVisible(true);
        boss.setCustomName("ZOMBIE DE LA MORT QUI TUE");
        Bukkit.broadcastMessage(ChatColor.RED + "UN BOSS A SPAWN !!!, X" + blockLocation.getX() + " Y" + blockLocation.getY() + " Z" + blockLocation.getZ() + ".");
    }

    @EventHandler
    public void onEntityDeath(EntityDamageByEntityEvent event){
        if(isEnable()) {
            if (event.getEntity().getEntityId() == boss.getEntityId()) {
                Entity killer = event.getDamager();
                if (boss.getHealth() - event.getFinalDamage() <= 0) {
                    Bukkit.broadcastMessage(ChatColor.AQUA + "Bravo à " + ChatColor.AQUA + killer.getName() + ChatColor.AQUA + " pour avoir tué le boss !");
                    if (killer instanceof Player) {
                        Player player = ((Player) killer).getPlayer();
                        int moneyWin = 1000;
                        Profile profile = getMain().getDataManager().getProfileManager().get(player.getUniqueId());
                        profile.setMoney(profile.getMoney() + moneyWin);
                        Bukkit.broadcastMessage(ChatColor.GOLD + "Il gagne " + ChatColor.GREEN + moneyWin + "€");
                    }
                    stop();
                }
            }
        }
    }

    @Override
    public void onDisable() {
        boss.remove();
    }
}
