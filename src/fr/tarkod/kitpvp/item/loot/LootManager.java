package fr.tarkod.kitpvp.item.loot;

import fr.tarkod.kitpvp.KitPvP;
import fr.tarkod.kitpvp.loot.LootRunnable;
import fr.tarkod.kitpvp.profile.ProfileSerializationManager;
import fr.tarkod.kitpvp.utils.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LootManager {

    private KitPvP main;
    private LootProfile lootProfile;
    private List<Loot> lootList;
    private File file;

    public LootManager(KitPvP main) {
        this.lootList = new ArrayList<>();
        this.main = main;
        file = new File(main.getDataFolder(), "loot" + ".json");
        defaultLoad();
        new LootRunnable().runTaskTimer(main, 0, 20);
    }

    private void defaultLoad() {
        loadFile();
    }

    private void loadFile() {

        ProfileSerializationManager profileSerializationManager = main.getProfileSerializationManager();

        if(file.exists()) {
            String json = FileUtils.loadContent(file);
            lootProfile = profileSerializationManager.deserializeLocationList(json);
        } else {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            lootProfile = new LootProfile("world");
            FileUtils.saveFile(file, profileSerializationManager.serializeLocationList(lootProfile));
        }
    }

    public void saveFile() {
        ProfileSerializationManager profileSerializationManager = main.getProfileSerializationManager();
        FileUtils.saveFile(file, profileSerializationManager.serializeLocationList(lootProfile));
    }

    public void removeAllLoot() {
        for(Loot loot : lootList) {
            loot.remove();
        }
    }

    public LootProfile getLootProfile(){
        return lootProfile;
    }
}
