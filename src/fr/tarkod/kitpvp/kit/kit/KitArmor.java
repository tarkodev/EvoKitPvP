package fr.tarkod.kitpvp.kit.kit;

public class KitArmor {

    private KitPvPItem helmet;
    private KitPvPItem chestplate;
    private KitPvPItem leggings;
    private KitPvPItem boots;

    public KitArmor(KitPvPItem helmet, KitPvPItem chestplate, KitPvPItem leggings, KitPvPItem boots) {
        this.helmet = helmet;
        this.chestplate = chestplate;
        this.leggings = leggings;
        this.boots = boots;
    }

    public void clear(){
        helmet = null;
        chestplate = null;
        leggings = null;
        boots = null;
    }

    public KitPvPItem getHelmet() {
        return helmet;
    }

    public void setHelmet(KitPvPItem helmet) {
        this.helmet = helmet;
    }

    public KitPvPItem getChestplate() {
        return chestplate;
    }

    public void setChestplate(KitPvPItem chestplate) {
        this.chestplate = chestplate;
    }

    public KitPvPItem getLeggings() {
        return leggings;
    }

    public void setLeggings(KitPvPItem leggings) {
        this.leggings = leggings;
    }

    public KitPvPItem getBoots() {
        return boots;
    }

    public void setBoots(KitPvPItem boots) {
        this.boots = boots;
    }
}
