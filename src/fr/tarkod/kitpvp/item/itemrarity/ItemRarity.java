package fr.tarkod.kitpvp.item.itemrarity;

import org.bukkit.ChatColor;

public class ItemRarity {

    private String ID;

    private String name;
    private String prefix;

    private int lootChance;

    public ItemRarity(String id) {
        this.ID = id;
        this.name = "Default Name";
        this.prefix = "&6";
        this.lootChance = 100;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getNameWithPrefix() {
        return getPrefix() + getName();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrefix() {
        return ChatColor.translateAlternateColorCodes('&', prefix);
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public int getLootChance() {
        return lootChance;
    }

    public void setLootChance(int lootChance) {
        this.lootChance = lootChance;
    }
}
