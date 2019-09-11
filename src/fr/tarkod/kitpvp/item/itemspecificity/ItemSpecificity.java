package fr.tarkod.kitpvp.item.itemspecificity;

import fr.tarkod.kitpvp.KitPvP;
import fr.tarkod.kitpvp.item.itemrarity.ItemRarity;
import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class ItemSpecificity {

    private String name;

    private String itemRarityID;
    private boolean lootOnDeath;
    private boolean lootWhenDrop;

    public ItemSpecificity() {
        this.name = null;
        this.itemRarityID = "NONE";
        this.lootOnDeath = true;
        this.lootWhenDrop = true;
    }

    public ItemSpecificity(ItemSpecificityBase itemSpecificityBase) {
        this.name = null;
        if(itemSpecificityBase == ItemSpecificityBase.KIT) {
            this.lootWhenDrop = false;
            this.lootOnDeath = false;
            this.itemRarityID = "KIT";
        }
    }

    public ItemRarity getItemRarity(KitPvP main) {
        return main.getDataManager().getItemRarityManager().getByID(itemRarityID);
    }

    public ItemStack getItem(ItemStack itemStack, KitPvP main) {
        ItemStack result = itemStack.clone();
        ItemMeta itemMeta = result.getItemMeta();

        itemMeta.setLore(getLore(main));

        if(getName() != null) {
            StringBuilder displayName = new StringBuilder();
            if(getItemRarity(main) != null){
                displayName.append(getItemRarity(main).getPrefix());
            }
            displayName.append(getName());
            itemMeta.setDisplayName(displayName.toString());
        }

        result.setItemMeta(itemMeta);
        return result;
    }

    public List<String> getLore(KitPvP main) {
        List<String> stringList = new ArrayList<>();
        if(!isLootWhenDrop()){
            stringList.add(ChatColor.GRAY + "LOOTWHENDROP: OFF");
        }
        if(!isLootOnDeath()){
            stringList.add(ChatColor.GRAY + "LOOTONDEATH: OFF");
        }
        if(getItemRarity(main) != null) {
            ItemRarity itemRarity = getItemRarity(main);
            stringList.add(itemRarity.getPrefix() + itemRarity.getName());
        }
        return stringList;
    }

    public String getItemRarityID() {
        return itemRarityID;
    }

    public void setItemRarityID(String itemRarityID) {
        this.itemRarityID = itemRarityID;
    }

    public boolean isLootOnDeath() {
        return lootOnDeath;
    }

    public void setLootOnDeath(boolean lootOnDeath) {
        this.lootOnDeath = lootOnDeath;
    }

    public boolean isLootWhenDrop() {
        return lootWhenDrop;
    }

    public void setLootWhenDrop(boolean lootWhenDrop) {
        this.lootWhenDrop = lootWhenDrop;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}