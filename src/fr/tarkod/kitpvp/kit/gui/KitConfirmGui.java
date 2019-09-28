package fr.tarkod.kitpvp.kit.gui;

import fr.tarkod.kitpvp.KitPvP;
import fr.tarkod.kitpvp.kit.KitManager;
import fr.tarkod.kitpvp.kit.kit.Kit;
import fr.tarkod.kitpvp.profile.Profile;
import fr.tarkod.kitpvp.utils.HuntiesInventory.HuntiesInventory;
import fr.tarkod.kitpvp.utils.ItemBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class KitConfirmGui extends HuntiesInventory {

    private KitPvP main;

    private Profile profile;
    private Kit kit;

    public KitConfirmGui(Kit kit, Profile profile, KitPvP main) {
        super(9*5, "Confirmation d'achat d'un Kit", main);
        this.profile = profile;
        this.kit = kit;
        this.main = main;

        load();
    }

    public void load() {
        KitManager kitManager = main.getDataManager().getKitManager();
        Player player = profile.getPlayer();
        setItem(13, new ItemBuilder(kit.getMaterial()).setName(ChatColor.GOLD + kit.getName()).toItemStack(), event -> {
            load();
        });
        setItem(29, new ItemBuilder(Material.INK_SACK).setDurability((short) 1).setName(ChatColor.RED + "Retour").toItemStack(), event -> {
            kitManager.openSelectionGui(profile);
        });
        setItem(33, new ItemBuilder(Material.INK_SACK).setDurability((short) 10).setName(ChatColor.GREEN + "Confirmer").toItemStack(), event -> {
            if(kitManager.canUnlock(kit, profile)) {
                profile.setMoney(profile.getMoney() - kit.getMoneyCost());
                kitManager.unlockForce(kit, profile);
                kitManager.openSelectionGui(profile);
            }
        });
    }
}
