package fr.tarkod.kitpvp.event.custom.soulpotion;

import org.bukkit.potion.PotionEffectType;

public enum PVPImpactPotionEffectType {
    SPEED, // positif
    SLOW, // négatif
    INCREASE_DAMAGE, // positif
    JUMP, // neutre
    REGENERATION, // positif
    DAMAGE_RESISTANCE, // positif
    WEAKNESS, // négatif
    POISON; // négatif

    public PotionEffectType getPotionEffectType() {
        return PotionEffectType.getByName(this.toString());
    }
}
