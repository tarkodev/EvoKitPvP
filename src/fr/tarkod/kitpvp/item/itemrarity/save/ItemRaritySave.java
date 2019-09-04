package fr.tarkod.kitpvp.item.itemrarity.save;

import com.google.gson.Gson;
import fr.tarkod.kitpvp.KitPvP;
import fr.tarkod.kitpvp.item.itemrarity.ItemRarity;
import fr.tarkod.kitpvp.item.itemrarity.ItemRarityManager;
import fr.tarkod.kitpvp.kit.FileUtilsKit;

import java.io.File;
import java.util.List;
import java.util.stream.Stream;

public class ItemRaritySave {

    private Gson gson;
    private File folder;
    private KitPvP main;

    public ItemRaritySave(Gson gson, File folder, KitPvP main) {
        this.gson = gson;
        this.folder = folder;
        this.main = main;
        this.loadItemRarity();
    }

    public void loadItemRarity() {
        List<ItemRarity> itemRarityList = ItemRarityManager.getItemRarityList();
        itemRarityList.clear();
        if (folder.exists()) {
            File[] files = folder.listFiles();
            if (files != null) {
                Stream.of(files).filter(f -> f.getName().endsWith(".json")).forEach(file -> {
                    String json = FileUtilsKit.load(file);
                    if (!json.equals("")) itemRarityList.add(this.gson.fromJson(json, ItemRarity.class));
                });
            }
        }
    }

    public void saveItemRarity() {
        List<ItemRarity> itemRarityList = ItemRarityManager.getItemRarityList();
        itemRarityList.forEach(
                kit -> FileUtilsKit.save(
                        new File(folder, kit.getID()+".json"),
                        this.gson.toJson(kit)
                )
        );
    }
}
