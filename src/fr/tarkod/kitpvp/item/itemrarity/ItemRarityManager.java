package fr.tarkod.kitpvp.item.itemrarity;

import java.util.ArrayList;
import java.util.List;

public class ItemRarityManager {

    private static List<ItemRarity> itemRarityList = new ArrayList<>();

    public static ItemRarity getByID(String string){
        return itemRarityList.stream().filter(itemRarity -> itemRarity.getID().equalsIgnoreCase(string)).findFirst().orElse(null);
    }

    public static void add(ItemRarity itemRarity) {
        itemRarityList.add(itemRarity);
    }

    public static List<ItemRarity> getItemRarityList() {
        return itemRarityList;
    }
}
