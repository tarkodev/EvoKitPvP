package fr.tarkod.kitpvp.item.itemspecificity;

import fr.tarkod.kitpvp.item.itemrarity.ItemRarity;
import fr.tarkod.kitpvp.item.itemrarity.ItemRarityManager;

public class ItemSpecificity {

    private String itemRarityID;

    public ItemSpecificity() {
        this.itemRarityID = "NONE";
    }

    public ItemRarity getItemRarity() {
        return ItemRarityManager.getByID(itemRarityID);
    }

    public String getLoreLine(){
        ItemRarity itemRarity = getItemRarity();
        return itemRarity.getPrefix() + itemRarity.getName();
    }

    public String getItemRarityID() {
        return itemRarityID;
    }

    public void setItemRarityID(String itemRarityID) {
        this.itemRarityID = itemRarityID;
    }
}