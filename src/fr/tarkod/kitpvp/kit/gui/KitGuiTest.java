package fr.tarkod.kitpvp.kit.gui;

import fr.tarkod.kitpvp.KitPvP;
import fr.tarkod.kitpvp.item.ItemRarity;
import fr.tarkod.kitpvp.kit.KitManager;
import fr.tarkod.kitpvp.kit.kit.Kit;
import fr.tarkod.kitpvp.profile.Profile;
import fr.tarkod.kitpvp.utils.EvoInventory.EvoInvItem;
import fr.tarkod.kitpvp.utils.EvoInventory.EvoInventory;
import fr.tarkod.kitpvp.utils.ItemBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;

public class KitGuiTest {

    private EvoInventory inventory;
    private KitPvP main;
    private KitManager kitManager;
    private Profile profile;

    public KitGuiTest(Profile profile) {
        this.profile = profile;
        main = KitPvP.getInstance();

        inventory = new EvoInventory("Kit Selection", 9 * 3, main);

        kitManager = main.getDataManager().getKitManager();
        load();
    }

    public void open() {
        load();
        profile.getPlayer().openInventory(inventory.getInventory());
    }

    public void load() {
        Player player = profile.getPlayer();
        for (int i = 0; i < kitManager.getKits().size(); i++) {
            Kit kit = kitManager.getKits().get(i);
            ItemStack itemStack = null;
            if (kitManager.isLock(kit, profile)) {
                itemStack = itemLocked(kit);
            } else {
                itemStack = itemUnlocked(kit);
            }
            inventory.setItem(kit.getSlot(), new EvoInvItem(itemStack, event -> {
                if (event.getClick() == ClickType.LEFT) {
                    if(kitManager.isLock(kit, profile)) {
                        kitManager.unlock(kit, profile);
                    } else {
                        kitManager.setKit(kit, profile);
                    }
                } else if (event.getClick() == ClickType.RIGHT) {
                    new ViewKitGui(kit, main).open(player);
                }
                load();
            }));
        }
    }

    private ItemStack itemUnlocked(Kit kit) {
        ItemBuilder item = new ItemBuilder(kit.getMaterial())
                .setName(ChatColor.GREEN + kit.getName())
                .addLoreLine(ChatColor.GRAY + kit.getDescription());

        /*if(kit.getSkill() != null) {
            for(String str : kit.getSkill().keySet()) {
                item.addLoreLine(ChatColor.AQUA + str + ":", ChatColor.AQUA + "- " + kit.getSkill().get(str));
            }
        }
        */
        if (kit.getMoneyCost() >= 0) {
            item.addLoreLine(ChatColor.GREEN + "Débloqué !");
        }
        if (!profile.getCooldownManager().canGet(kit.getName())) {
            item.addLoreLine(ChatColor.RED + "Tu peux réutiliser ce kit dans " + profile.getCooldownManager().getTimeLeft(kit.getName()) + "s");
        }

        return item.toItemStack();
    }

    private ItemStack itemLocked(Kit kit) {
        ItemBuilder item = new ItemBuilder(kit.getMaterial())
                .setName(ChatColor.RED + kit.getName())
                .addLoreLine(ChatColor.GRAY + kit.getDescription());

        /*if(kit.getSkill() != null) {
            for(String str : kit.getSkill().keySet()) {
                item.addLoreLine(ChatColor.AQUA + str + ":", ChatColor.AQUA + "- " + kit.getSkill().get(str));
            }
        }*/

        if (kit.getMoneyCost() >= 0) {
            item.addLoreLine(ChatColor.RED + "Bloqué" + " " + kit.getMoneyCost() + "$");
        }
        if (kit.getLevelCost() > 0) {
            item.addLoreLine(ChatColor.RED + "Il faut être niveau " + kit.getLevelCost() + " !");
        }
        return item.toItemStack();
    }
}

