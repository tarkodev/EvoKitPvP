package fr.tarkod.kitpvp.kit.gui;

import fr.tarkod.kitpvp.KitPvP;
import fr.tarkod.kitpvp.kit.KitManager;
import fr.tarkod.kitpvp.kit.kit.Kit;
import fr.tarkod.kitpvp.profile.Profile;
import fr.tarkod.kitpvp.utils.EvoInventory.EvoInvItem;
import fr.tarkod.kitpvp.utils.EvoInventory.EvoInventory;
import fr.tarkod.kitpvp.utils.ItemBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class ConfirmKitGui {

    private EvoInventory inventory;
    private KitPvP main;

    private Profile profile;
    private Kit kit;

    public ConfirmKitGui(Kit kit, Profile profile, KitPvP main) {
        this.profile = profile;
        this.kit = kit;
        this.main = main;

        inventory = new EvoInventory("Confirmation d'achat d'un Kit", 9 * 5, main);

        load();
    }

    public void open() {
        load();
        profile.getPlayer().openInventory(inventory.getInventory());
    }

    public void load() {
        KitManager kitManager = main.getDataManager().getKitManager();
        Player player = profile.getPlayer();
        inventory.setItem(13, new EvoInvItem(new ItemBuilder(kit.getMaterial()).setName(ChatColor.GOLD + kit.getName()).toItemStack(), event -> {
            load();
        }));
        inventory.setItem(29, new EvoInvItem(new ItemBuilder(Material.INK_SACK).setDurability((short) 1).setName(ChatColor.RED + "Retour").toItemStack(), event -> {
            profile.getKitSelection().open();
        }));
        inventory.setItem(33, new EvoInvItem(new ItemBuilder(Material.INK_SACK).setDurability((short) 10).setName(ChatColor.GREEN + "Confirmer").toItemStack(), event -> {
            if ((profile.getMoney() - kit.getMoneyCost()) >= 0) {
                profile.setMoney(profile.getMoney() - kit.getMoneyCost());
                kitManager.unlockForce(kit, profile);
                profile.getKitSelection().open();
            }
        }));
    }
}
