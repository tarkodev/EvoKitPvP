package fr.tarkod.kitpvp.event.custom.soulpotion;

import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.HashMap;
import java.util.Map;

public class PlayerPotionEffectsInformations {

    private Map<PotionEffectType, Integer> effectsLevels = new HashMap<>();

    /**
     * @param player
     * Clear all active effect and apply all effects stored for an infinite times
     */
    public void setEffectsOnPlayer(Player player) {
        player.getActivePotionEffects().clear();
        effectsLevels.forEach((type, level) -> player.addPotionEffect(new PotionEffect(type, Integer.MAX_VALUE, level-1)));
    }

    public int getEffectLevel(PotionEffectType type) {
        return effectsLevels.getOrDefault(type, 0);
    }

    public void addEffect(PotionEffectType type) {
        addEffect(type, 1);
    }

    public void addEffect(PotionEffectType type, int level) {
        if (effectsLevels.containsKey(type)) {
            effectsLevels.put(type, effectsLevels.get(type) + level);
        } else {
            effectsLevels.put(type, level);
        }
    }

    public void removeEffect(PotionEffectType type) {
        removeEffect(type, 1);
    }

    public void removeEffect(PotionEffectType type, int level) {
        if (effectsLevels.containsKey(type)) {
            effectsLevels.put(type, effectsLevels.get(type) - level);

            if (effectsLevels.get(type) <= 0) {
                effectsLevels.remove(type);
            }
        }
    }
}
