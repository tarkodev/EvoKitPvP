package fr.tarkod.kitpvp.profile.cooldown;

import fr.tarkod.kitpvp.profile.Profile;

import java.time.Instant;
import java.util.*;

public class CooldownManager {

    private transient Profile profile;

    private Map<String, Long> uuidStringIntegerMap;

    public CooldownManager(Profile profile) {
        this.uuidStringIntegerMap = new HashMap<>();
        defaultLoad(profile);
    }

    public void defaultLoad(Profile profile){
        this.profile = profile;
    }

    public void update(){
        List<String> removedKit = new ArrayList<>();
        uuidStringIntegerMap.keySet().stream().filter(s -> getTimeLeft(s) <= 0).forEach(removedKit::add);
        removedKit.forEach(s -> uuidStringIntegerMap.remove(s));
    }

    public void set(String string, long l){
        uuidStringIntegerMap.put(string, l);
    }

    public long getTimeLeft(String kit){
        return uuidStringIntegerMap.get(kit) - Instant.now().getEpochSecond();
    }

    public boolean canGet(String kit){
        update();
        if(profile.getPlayer().hasPermission("kitpvp.cooldown")) {
            return true;
        }
        if (uuidStringIntegerMap.get(kit) == null) {
            return true;
        }
        return false;
    }
}
