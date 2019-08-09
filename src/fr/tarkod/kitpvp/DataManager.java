package fr.tarkod.kitpvp;

import com.google.gson.Gson;
import fr.tarkod.kitpvp.kit.KitManager;
import fr.tarkod.kitpvp.profile.serialization.ProfileManager;

import java.io.File;

public class DataManager {

    private KitManager kitManager;
    private ProfileManager profileManager;

    public DataManager(Gson gson, File dataFolder, KitPvP main) {
        kitManager = new KitManager(gson ,new File(dataFolder, "kit_data"), main);
        profileManager = new ProfileManager(gson, new File(dataFolder, "player_data"), main);

    }

    public KitManager getKitManager() {
        return kitManager;
    }

    public ProfileManager getProfileManager() {
        return profileManager;
    }
}
