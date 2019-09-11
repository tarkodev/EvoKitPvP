package fr.tarkod.kitpvp.event.custom.doubleall;

import fr.tarkod.kitpvp.KitPvP;
import fr.tarkod.kitpvp.event.Event;
import fr.tarkod.kitpvp.listeners.custom.EGPlayerRespawnEvent;
import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PotionSplashEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Collection;

public class EventDoubleAll extends Event {

    public EventDoubleAll(String name, String description, Material material, int maxTime, KitPvP main) {
        super(name, description, material, maxTime, main);
    }

    @EventHandler
    public void doubleHit(EntityDamageByEntityEvent event) {
        event.setDamage(event.getDamage(EntityDamageEvent.DamageModifier.BASE) * 2);
    }

    @Override
    public void everySecond() {}

    @Override
    public void onEnable() {
        Bukkit.getOnlinePlayers().forEach(this::doublePlayerStats);
    }

    private void doublePlayerStats(Player player) {
        player.setWalkSpeed(0.2f * 2.f);
        player.setMaxHealth(20 * 2);
        player.setHealth(20 * 2);
    }

    @EventHandler
    public void onRespawn(EGPlayerRespawnEvent event) {
        doublePlayerStats(event.getPlayer());
    }

    //

    @EventHandler
    public void doubleJumpDetection(PlayerToggleFlightEvent event) {
        // To test in real condition to get a perfect jump sensation
        Player player = event.getPlayer();

        if (!(player.getGameMode() == GameMode.CREATIVE || player.getGameMode() == GameMode.SPECTATOR ||
                player.isFlying() || player.hasPotionEffect(PotionEffectType.INVISIBILITY))) {
            noFlight(player);
            player.setVelocity(event.getPlayer().getLocation().getDirection().multiply(1.5).setY(1));
        }
    }

    @EventHandler
    public void onItemConsume(PlayerItemConsumeEvent event) {
        Player player = event.getPlayer();
        ItemStack itemConsumed = event.getItem();

        switch (itemConsumed.getType()) {
            case GOLDEN_APPLE:
                if (itemConsumed.getDurability() == 1) {    // Notch apple
                    player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 20*20, 1));
                    player.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 20*2*60, 4*2-1));
                    player.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 20*5*60, 1*2-1));
                } else {                                    // Golden apple
                    player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 20*5, 2*2-1));
                    player.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 20*2*60, 1*2-1));
                }

                break;

            case POTION:
                Potion.fromItemStack(itemConsumed).getEffects()
                        .forEach(effect -> player.addPotionEffect(new PotionEffect(effect.getType(), effect.getDuration(), effect.getAmplifier() * 2)));
                break;

            default:
                break;
        }
    }

    @EventHandler
    public void splashPotion(PotionSplashEvent event) {
        Collection<PotionEffect> effects = event.getPotion().getEffects();

        event.getAffectedEntities().forEach(entity -> {
            if (entity instanceof Player) {
                effects.forEach(effect -> entity.addPotionEffect(new PotionEffect(effect.getType(), effect.getDuration(), effect.getAmplifier() * 2)));
            }
        });
    }

    @EventHandler
    public void hitTheGround(PlayerMoveEvent event) {
        Player player = event.getPlayer();

        if (!player.getAllowFlight()) {
            player.setAllowFlight(true);
        }
    }

    @EventHandler
    public void onDisconnect(PlayerQuitEvent event) {
        resetPlayerStats(event.getPlayer());
    }

    @EventHandler
    public void onConnect(PlayerJoinEvent event) {
        doublePlayerStats(event.getPlayer());
    }

    @Override
    public void onDisable() {
        Bukkit.getOnlinePlayers().forEach(this::resetPlayerStats);
    }

    private void resetPlayerStats(Player player) {
        player.setWalkSpeed(0.2f);
        player.setMaxHealth(20);
        player.setHealth(20);
        noFlight(player);
    }

    private void noFlight(Player player) {
        player.setAllowFlight(false);
        player.setFlying(false);
    }
}
