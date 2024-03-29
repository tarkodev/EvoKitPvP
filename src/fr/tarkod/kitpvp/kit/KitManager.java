package fr.tarkod.kitpvp.kit;

import com.google.gson.Gson;
import fr.tarkod.kitpvp.KitPvP;
import fr.tarkod.kitpvp.kit.gui.KitConfirmGui;
import fr.tarkod.kitpvp.kit.gui.KitSelectionGui;
import fr.tarkod.kitpvp.kit.gui.KitViewerGui;
import fr.tarkod.kitpvp.kit.kit.Kit;
import fr.tarkod.kitpvp.profile.Profile;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.io.File;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class KitManager {

    private Gson gson;
    private List<Kit> kits;
    private File kitFile;
    private KitPvP main;

    private boolean status;

    public KitManager(Gson gson, File kitFile, KitPvP main) {
        this.gson = gson;
        this.main = main;
        this.kits = new ArrayList<>();
        this.kitFile = kitFile;
        this.status = true;
        this.loadKits();
    }

    public void registerKit(Kit kit) {
        kits.add(kit);
    }

    public void unregisterKit(Kit kit) {
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

    public void openSelectionGui(Profile profile) {
        new KitSelectionGui(profile, main).open(profile.getPlayer());
    }

    public void openConfirmGui(Kit kit, Profile profile) {
        new KitConfirmGui(kit, profile, main).open(profile.getPlayer());
    }

    public void openKitViewerGui(Kit kit, Profile profile) {
        new KitViewerGui(kit, main).open(profile.getPlayer());
    }

    public void remove(Kit kit, Profile profile) {
        profile.getUnlockedKit().remove(kit.getName());
    }

    public void refund(Kit kit, Profile profile) {
        if(profile.getUnlockedKit().contains(kit.getName())) {
            profile.sendMessage(ChatColor.WHITE + "Ton kit " + ChatColor.AQUA + kit.getName() + ChatColor.WHITE + " a été remboursé !" + ChatColor.GREEN + " +" + kit.getMoneyCost() + "$");
            profile.setMoney(profile.getMoney() + kit.getMoneyCost());
            remove(kit, profile);
        }
    }

    public void unlock(Kit kit, Profile profile) {
        if(canUnlock(kit, profile)) {
            openConfirmGui(kit, profile);
        }
    }

    public boolean canUnlock(Kit kit, Profile profile){
        Player player = profile.getPlayer();
        for (int j = 0; j < kit.getPermissionList().size(); j++) {
            String perm = kit.getPermissionList().get(0);
            if (!player.hasPermission(perm)) {
                player.sendMessage(ChatColor.RED + "Tu ne peux pas acheter ce kit car tu n'as pas la permission " + ChatColor.GOLD + perm);
                return false;
            }
        }
        if(!isLock(kit, profile)){
            player.sendMessage(ChatColor.RED + "Erreur: Tu as déjà le kit !");
            return false;
        }
        if ((profile.getLevel() - kit.getLevelCost()) <= 0) {
            player.sendMessage(ChatColor.RED + "Tu n'as pas le niveau requis pour acheter ceci.");
            player.playSound(player.getLocation(), Sound.ANVIL_LAND, 1, 1);
            return false;
        }
        if (!((profile.getMoney() - kit.getMoneyCost()) >= 0)) {
            player.sendMessage(ChatColor.RED + "Tu n'as pas assez d'argent pour acheter ceci.");
            player.playSound(player.getLocation(), Sound.ANVIL_LAND, 1, 1);
            return false;
        }
        return true;
    }

    public void unlockForce(Kit kit, Profile profile) {
        if(!(profile.getUnlockedKit().contains(kit.getName()))) {
            Player player = profile.getPlayer();
            profile.getUnlockedKit().add(kit.getName());
            player.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "Kit Débloqué !");
            player.playSound(player.getLocation(), Sound.LEVEL_UP, 1, 1);
        }
    }

    public boolean isLock(Kit kit, Profile profile) {
        return !profile.getUnlockedKit().contains(kit.getName());
    }

    public void setKit(Kit kit, Profile profile) {
        Player player = profile.getPlayer();
        if(!isLock(kit, profile)) {
            if(profile.getCooldownManager().canGet(kit.getName())) {
                kit.apply(player, main);
                profile.getCooldownManager().set(kit.getName(), Instant.now().getEpochSecond() + kit.getCooldown());
                player.sendMessage(ChatColor.GREEN + "Kit appliqué !");
                player.closeInventory();
            } else {
                player.sendMessage(ChatColor.RED + "Ton kit est réutilisable dans " + profile.getCooldownManager().getTimeLeft(kit.getName()) + "s");
            }
        } else {
            player.sendMessage(ChatColor.RED + "Tu n'as pas ce kit !");
        }
    }

    public void setKitForce(Kit kit, Profile profile){
        kit.apply(profile.getPlayer(), main);
    }

    public List<Kit> getKits() {
        return kits;
    }

    public boolean isEnable() {
        return status;
    }

    public void setEnable(boolean status) {
        this.status = status;
    }
}