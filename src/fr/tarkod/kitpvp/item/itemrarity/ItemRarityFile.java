package fr.tarkod.kitpvp.item.itemrarity;

import java.util.List;

public class ItemRarityFile {

    private List<ItemRarity> itemRarityList;

    public ItemRarityFile(List<ItemRarity> itemRarityList) {
        this.itemRarityList = itemRarityList;
    }

    public List<ItemRarity> getItemRarityList() {
        return itemRarityList;
    }
}
