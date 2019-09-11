package fr.tarkod.kitpvp.event.custom.soulpotion;

import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.HashMap;
import java.util.Map;

public class PlayerPotionEffectsInformations {

    private Map<PotionEffectType, Integer> effectsLevels = new HashMap<>();
    private Map<PotionEffectType, TimeAndAmplifier> temporaryEffects = new HashMap<>();

    public void addTemporaryEffects(PotionEffect potionEffect) {
        temporaryEffects.put(potionEffect.getType(), new TimeAndAmplifier(potionEffect.getAmplifier(), potionEffect.getDuration()));
    }

    public void decrementAndCheckTemporaryEffects(Player player) {
        for (Map.Entry<PotionEffectType, TimeAndAmplifier> informations : new HashMap<>(temporaryEffects).entrySet()) {
            if (temporaryEffects.get(informations.getKey()).getTimeInSecond() <= 1) {
                temporaryEffects.remove(informations.getKey());
                setEffectsOnPlayer(player, informations.getKey());
            }

            informations.getValue().minusOneSecond();
        }
    }

    public void removeTemporaryEffects(PotionEffectType type) {
        temporaryEffects.remove(type);
    }

    public void cleanTemporaryEffects() {
        temporaryEffects.clear();
    }

    public void setEffectsOnPlayer(Player player) {
        effectsLevels.keySet().forEach(type -> setEffectsOnPlayer(player, type));
    }

    public void setEffectsOnPlayer(Player player, PotionEffectType type) {
        Integer time = null;
        Integer amplifier = null;

        if (effectsLevels.containsKey(type)) {
            time = Integer.MAX_VALUE;
            amplifier = effectsLevels.get(type)-1;
        }

        if (temporaryEffects.containsKey(type)) {
            time = temporaryEffects.get(type).getTimeInTick();
            amplifier += temporaryEffects.get(type).getAmplifier();
        }

        if (time != null) {
            player.addPotionEffect(new PotionEffect(type, time, amplifier));
        }
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
