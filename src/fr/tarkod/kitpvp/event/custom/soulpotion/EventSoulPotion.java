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
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class EventSoulPotion extends Event {

    private final List<PotionEffectType> pvpEffect = Stream.of(PVPImpactPotionEffectType.values()).map(PVPImpactPotionEffectType::getPotionEffectType).collect(Collectors.toList());

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

    @EventHandler
    public void onRespawn(EGPlayerRespawnEvent event) {
        Player player = event.getPlayer();

        playersEffects.get(player.getUniqueId()).setEffectsOnPlayer(player);
    }

    @EventHandler
    public void onGoldAppleAte(PlayerItemConsumeEvent event) {
        Player player = event.getPlayer();
        ItemStack itemConsumed = event.getItem();

        switch (itemConsumed.getType()) {
            case GOLDEN_APPLE:

                break;

            case POTION:
                Collection<PotionEffect> effects = Potion.fromItemStack(itemConsumed).getEffects();
                break;

            case MILK_BUCKET:
                clearPotionEffects(player);
                playersEffects.get(player.getUniqueId()).setEffectsOnPlayer(player);
                break;

            default:
                break;
        }
    }

    private void test() {

    }

    @Override
    public void everySecond() {}

    @Override
    public void onEnable() {}

    @Override
    public void onDisable() {
        playersEffects.keySet().forEach(uuid -> clearPotionEffects(Bukkit.getPlayer(uuid)));
        playersEffects.clear();
    }

    private void clearPotionEffects(Player player) {
        player.getActivePotionEffects().forEach(potionEffect -> {
            PotionEffectType type = potionEffect.getType();

            if (pvpEffect.contains(type)) {
                player.removePotionEffect(type);
            }
        });
    }
}
