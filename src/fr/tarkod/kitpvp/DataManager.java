package fr.tarkod.kitpvp;

import com.google.gson.Gson;
import fr.tarkod.kitpvp.item.itemrarity.ItemRarityManager;
import fr.tarkod.kitpvp.item.itemspecificity.ItemSpecificityManager;
import fr.tarkod.kitpvp.item.loot.LootManager;
import fr.tarkod.kitpvp.kit.KitManager;
import fr.tarkod.kitpvp.profile.serialization.ProfileManager;

import java.io.File;

public class DataManager {

    private KitManager kitManager;
    private ProfileManager profileManager;

    private ItemRarityManager itemRarityManager;
    private ItemSpecificityManager itemSpecificityManager;
    private LootManager lootManager;

    public DataManager(Gson gson, File dataFolder, KitPvP main) {
        kitManager = new KitManager(gson ,new File(dataFolder, "kit_data"), main);
        profileManager = new ProfileManager(gson, new File(dataFolder, "player_data"), main);
        lootManager = new LootManager(main);

        itemRarityManager = new ItemRarityManager(gson, new File(dataFolder, "itemrarity_data"), main);
        itemSpecificityManager = new ItemSpecificityManager(main);
    }

    public KitManager getKitManager() {
        return kitManager;
    }

    public ProfileManager getProfileManager() {
        return profileManager;
    }

    public ItemRarityManager getItemRarityManager() {
        return itemRarityManager;
    }

    public ItemSpecificityManager getItemSpecificityManager() {
        return itemSpecificityManager;
    }

    public LootManager getLootManager() {
        return lootManager;
    }
}
