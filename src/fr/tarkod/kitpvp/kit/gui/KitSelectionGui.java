package fr.tarkod.kitpvp.kit.gui;

import fr.tarkod.kitpvp.KitPvP;
import fr.tarkod.kitpvp.kit.KitManager;
import fr.tarkod.kitpvp.kit.kit.Kit;
import fr.tarkod.kitpvp.profile.Profile;
import fr.tarkod.kitpvp.utils.HuntiesInventory.HuntiesInventory;
import fr.tarkod.kitpvp.utils.ItemBuilder;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

public class KitSelectionGui extends HuntiesInventory {

    private KitPvP main;

    private Profile profile;

    public KitSelectionGui(Profile profile, KitPvP main) {
        super(9*3, "Sélection des kits", main);
        this.profile = profile;
        this.main = main;

        load();
    }

    public void load() {
        KitManager kitManager = main.getDataManager().getKitManager();
        Player player = profile.getPlayer();
        for (int i = 0; i < kitManager.getKits().size(); i++) {
            Kit kit = kitManager.getKits().get(i);
            ItemStack itemStack = null;
            if (kitManager.isLock(kit, profile)) {
                itemStack = itemLocked(kit);
            } else {
                itemStack = itemUnlocked(kit);
            }
            setItem(kit.getSlot(), itemStack, event -> {
                if (event.getClick() == ClickType.LEFT) {
                    if (kitManager.isLock(kit, profile)) {
                        kitManager.unlock(kit, profile);
                    } else {
                        kitManager.setKit(kit, profile);
                    }
                } else if (event.getClick() == ClickType.RIGHT) {
                    kitManager.openKitViewerGui(kit, profile);
                }
                load();
            });
        }
    }

    private ItemStack itemUnlocked(Kit kit) {
        ItemBuilder item = new ItemBuilder(kit.getMaterial())
                .setName(ChatColor.GREEN + kit.getName());

        item.addLoreLine(ChatColor.GRAY + kit.getDescription());

        item.addLoreLine(ChatColor.DARK_GRAY + "♦ Clique gauche pour s'équiper le kit");
        item.addLoreLine(ChatColor.DARK_GRAY + "♦ Clique droit pour prévisualiser le kit");

        item.addLoreLine(" ");

        if (kit.getMoneyCost() >= 0) {
            item.addLoreLine(ChatColor.GREEN + "Tu possède ce kit !");
        }
        if (!profile.getCooldownManager().canGet(kit.getName())) {
            item.addLoreLine(ChatColor.RED + "Tu peux réutiliser ce kit dans " + profile.getCooldownManager().getTimeLeft(kit.getName()) + "s");
        }
        return item.toItemStack();
    }

    private ItemStack itemLocked(Kit kit) {
        ItemBuilder item = new ItemBuilder(kit.getMaterial())
                .setName(ChatColor.RED + kit.getName());

        item.addLoreLine(ChatColor.GRAY + kit.getDescription());

        item.addLoreLine(ChatColor.DARK_GRAY + "♦ Clique gauche pour s'équiper le kit");
        item.addLoreLine(ChatColor.DARK_GRAY + "♦ Clique droit pour prévisualiser le kit");



        item.addLoreLine(" ");

        if (kit.getMoneyCost() >= 0) {
            item.addLoreLine(ChatColor.RED + "Tu ne possède pas ce kit, il coûte " + kit.getMoneyCost() + "$");
        }
        if (kit.getLevelCost() > 0) {
            item.addLoreLine(ChatColor.RED + "et il faut être niveau " + kit.getLevelCost() + " !");
        }
        return item.toItemStack();
    }
}
