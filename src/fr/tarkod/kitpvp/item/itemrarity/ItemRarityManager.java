package fr.tarkod.kitpvp.item.itemrarity;

import com.google.gson.Gson;
import fr.tarkod.kitpvp.KitPvP;
import fr.tarkod.kitpvp.kit.FileUtilsKit;
import org.bukkit.Bukkit;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class ItemRarityManager {

    private Gson gson;
    private File folder;
    private KitPvP main;

    private List<ItemRarity> itemRarityList = new ArrayList<>();

    public ItemRarityManager(Gson gson, File folder, KitPvP main) {
        this.gson = gson;
        this.folder = folder;
        this.main = main;
        this.loadItemRarity();
    }

    public ItemRarity getByID(String string){
        if(string == null) return null;

        return itemRarityList.stream().filter(itemRarity -> itemRarity.getID().equalsIgnoreCase(string)).findFirst().orElse(null);
    }

    public void add(ItemRarity itemRarity) {
        itemRarityList.add(itemRarity);
    }

    public List<ItemRarity> getItemRarityList() {
        return itemRarityList;
    }

    public void loadItemRarity() {
        List<ItemRarity> itemRarityList = getItemRarityList();
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
        List<ItemRarity> itemRarityList = getItemRarityList();
        itemRarityList.forEach(
                kit -> FileUtilsKit.save(
                        new File(folder, kit.getID()+".json"),
                        this.gson.toJson(kit)
                )
        );
    }
}
