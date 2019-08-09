package fr.tarkod.kitpvp.kit;

import com.google.gson.Gson;
import fr.tarkod.kitpvp.KitPvP;
import fr.tarkod.kitpvp.kit.kit.Kit;
import fr.tarkod.kitpvp.profile.Profile;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class KitManager {

    private Gson gson;
    private List<Kit> kits;
    private File kitFile;
    private KitPvP main;

    public KitManager(Gson gson, File kitFile, KitPvP main) {
        this.gson = gson;
        this.main = main;
        this.kits = new ArrayList<>();
        this.kitFile = kitFile;
        this.loadKits();
    }

    public void addKit(Kit kit){
        kits.add(kit);
    }

    public void removeKit(Kit kit){
        kits.remove(kit);
    }

    public Kit getKitByName(String name) {
        return this.kits.stream().filter(kit -> kit.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
    }

    public void loadKits() {
        kits.clear();
        if (kitFile.exists()) {
            File[] files = kitFile.listFiles();
            if (files != null) {
                Stream.of(files).filter(f -> f.getName().endsWith(".json")).forEach(file -> {
                    String json = FileUtilsKit.load(file);
                    if (!json.equals("")) this.kits.add(this.gson.fromJson(json, Kit.class));
                });
            }
        }
    }

    public void saveKits() {
        this.kits.forEach(
                kit -> FileUtilsKit.save(
                        new File(kitFile, kit.getName()+".json"),
                        this.gson.toJson(kit)
                )
        );
    }

    public void lock(Kit kit, Profile profile) {
        profile.getUnlockedKit().remove(kit.getName());
    }

    public void unlock(Kit kit, Profile profile) {
        if(!(profile.getUnlockedKit().contains(kit.getName()))){
            profile.getUnlockedKit().add(kit.getName());
        }
    }

    public boolean isLock(Kit kit, Profile profile) {
        return !profile.getUnlockedKit().contains(kit.getName());
    }

    public List<Kit> getKits() {
        return kits;
    }
}