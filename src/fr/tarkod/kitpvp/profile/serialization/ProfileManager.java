package fr.tarkod.kitpvp.profile.serialization;

import com.google.gson.Gson;
import fr.tarkod.kitpvp.KitPvP;
import fr.tarkod.kitpvp.profile.Profile;
import fr.tarkod.kitpvp.profile.ProfileSerializationManager;
import fr.tarkod.kitpvp.profile.atm.ATMRunnable;
import fr.tarkod.kitpvp.utils.FileUtils;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.util.*;

public class ProfileManager {

    private Gson gson;
    private File folder;
    private KitPvP main;

    private ProfileSerializationManager profileSerializationManager;
    private Map<UUID, Profile> uuidProfileMap = new HashMap<>();

    public ProfileManager(Gson gson, File folder, KitPvP main) {
        this.gson = gson;
        this.folder = folder;
        this.main = main;
        this.profileSerializationManager = new ProfileSerializationManager();
        folder.mkdirs();
        launchRunnable();
    }

    public Profile get(UUID uuid){
        return get(uuid, true);
    }

    public Profile get(UUID uuid, boolean defaultLoad){
        File file = new File(folder, uuid + ".json");

        Profile profile;
        if(uuidProfileMap.containsKey(uuid)){
            profile = uuidProfileMap.get(uuid);
        } else {
            if (file.exists()) {
                ProfileSerializationManager profileSerializationManager = main.getProfileSerializationManager();
                String json = FileUtils.loadContent(file);
                profile = profileSerializationManager.deserialize(json);
            } else {
                profile = new Profile(uuid, main);
            }
            if (defaultLoad) {
                profile.defaultLoad(main);
            } else {
                profile.defaultLoad(false, false, false, main);
            }
        }
        if(defaultLoad) {
            uuidProfileMap.putIfAbsent(uuid, profile);
        }
        return profile;
    }

    public void remove(UUID uuid){
        uuidProfileMap.remove(uuid);
    }

    public Map<UUID, Profile> getUuidProfileMap(){
        return uuidProfileMap;
    }

    public List<Profile> getAllOfflineAndOnlineProfile(){
        save();
        List<File> profilesFile = new ArrayList<>(Arrays.asList(Objects.requireNonNull(folder.listFiles())));
        List<Profile> profiles = new ArrayList<>();
        profilesFile.forEach(file -> profiles.add(get(UUID.fromString(file.getName().replace(".json", "")), false)));
        return profiles;
    }

    public void launchRunnable(){
        new BukkitRunnable(){

            @Override
            public void run() {
                save();
            }

        }.runTaskTimer(main, 20, 20*15);
        new ATMRunnable(main).runTaskTimer(main, 20, 20);
    }

    public void save(){
        uuidProfileMap.forEach((uuid, profile) -> save(uuid));
    }

    public void save(UUID uuid){
        File file = new File(folder, uuid + ".json");
        Profile profile = get(uuid);

        FileUtils.saveFile(file, profileSerializationManager.serialize(profile));
    }
}
