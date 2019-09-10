package fr.tarkod.kitpvp.event.custom.soulpotion;

import fr.tarkod.kitpvp.KitPvP;
import fr.tarkod.kitpvp.event.Event;
import fr.tarkod.kitpvp.listeners.custom.EGPlayerDeathByEntityEvent;
import fr.tarkod.kitpvp.listeners.custom.EGPlayerRespawnEvent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.potion.PotionEffectType;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class EventSoulPotion extends Event {

    private Map<UUID, PlayerPotionEffectsInformations> playersEffects = new HashMap<>();

    public EventSoulPotion(String name, String description, Material material, int maxTime, KitPvP main) {
        super(name, description, material, maxTime, main);
    }

    @EventHandler
    public void onDeathByPlayer(EGPlayerDeathByEntityEvent event) {
        if (!(event.getKiller() instanceof Player)) return;

        Player killer = (Player) event.getKiller();
        UUID killerUUID = killer.getUniqueId();

        if (!playersEffects.containsKey(killerUUID)) {
            playersEffects.put(killerUUID, new PlayerPotionEffectsInformations());
        }

        Random rand = new Random();
        PotionEffectType type = PVPImpactPotionEffectType.values()[rand.nextInt(PVPImpactPotionEffectType.values().length)].getPotionEffectType();

        killer.sendMessage(ChatColor.GOLD + "Vous avez gagné un niveau de l'effet de potion " + ChatColor.LIGHT_PURPLE + type.getName() + ".");

        playersEffects.get(killerUUID).addEffect(type);
        playersEffects.get(killerUUID).setEffectsOnPlayer(killer);
    }

    @Override
    public void everySecond() {}

    @Override
    public void onEnable() {}

    @Override
    public void onDisable() {
        List<PotionEffectType> pvpEffect = Stream.of(PVPImpactPotionEffectType.values()).map(PVPImpactPotionEffectType::getPotionEffectType).collect(Collectors.toList());

        playersEffects.keySet().forEach(uuid -> {
            Player player = Bukkit.getPlayer(uuid);

            player.getActivePotionEffects().forEach(potionEffect -> {
                PotionEffectType type = potionEffect.getType();

                if (pvpEffect.contains(type)) {
                    player.removePotionEffect(type);
                }
            });
        });

        playersEffects.clear();
    }
}
