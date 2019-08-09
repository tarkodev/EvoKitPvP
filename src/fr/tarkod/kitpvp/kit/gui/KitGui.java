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

public class KitGui {

    private EvoInventory inventory;
    private KitPvP main;
    private KitManager kitManager;
    private Profile profile;

    public KitGui(Profile profile){
        this.profile = profile;
        inventory = new EvoInventory("Kit Selection", 9*3, KitPvP.getInstance());
        main = KitPvP.getInstance();
        kitManager = main.getDataManager().getKitManager();
        load();
    }

    public void open(){
        load();
        profile.getPlayer().openInventory(inventory.getInventory());
    }

    public void load(){
        Player player = profile.getPlayer();
        for (int i = 0; i < kitManager.getKits().size(); i++) {
            Kit kit = kitManager.getKits().get(i);
            ItemStack itemStack = null;
            if(kitManager.isLock(kit, profile)){
                itemStack = itemLocked(kit);
            } else {
                itemStack = itemUnlocked(kit);
            }
            inventory.setItem(kit.getSlot(), new EvoInvItem(itemStack, event -> {
                if(event.getClick() == ClickType.LEFT) {
                    for (int j = 0; j < kit.getPermissionList().size(); j++) {
                        String perm = kit.getPermissionList().get(0);
                        if (!player.hasPermission(perm)) {
                            player.sendMessage(ChatColor.RED + "Tu ne peux pas acheter ce kit car tu n'as pas la permission " + ChatColor.GOLD + perm);
                            return;
                        }
                    }
                    HashMap<ItemRarity, List<ItemStack>> map = ItemRarity.inventoryChecker(player);
                    if (kitManager.isLock(kit, profile)) {
                        if ((profile.getMoney() - kit.getMoneyCost()) >= 0) {
                            if ((profile.getLevel() - kit.getLevelCost()) >= 0) {
                                kitManager.unlock(kit, profile);
                                profile.setMoney(profile.getMoney() - kit.getMoneyCost());
                                player.sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "Kit Débloqué !");
                                player.playSound(player.getLocation(), Sound.LEVEL_UP, 1, 1);
                            } else {
                                player.sendMessage(ChatColor.RED + "Erreur: Tu n'as pas le niveau requis pour acheter ceci.");
                                player.playSound(player.getLocation(), Sound.ANVIL_LAND, 1, 1);
                            }
                        } else {
                            player.sendMessage(ChatColor.RED + "Erreur: Tu n'as pas assez d'argent pour acheter ceci.");
                            player.playSound(player.getLocation(), Sound.ANVIL_LAND, 1, 1);
                        }
                    } else {
                        if (profile.getCooldownManager().canGet(kit.getName())) {
                            kit.apply(player);
                            profile.getCooldownManager().set(kit.getName(), Instant.now().getEpochSecond() + kit.getCooldown());
                            player.sendMessage(ChatColor.GREEN + "Tu as choisi le kit " + kit.getName());
                        } else {
                            player.sendMessage(ChatColor.RED + "Ton kit est réutilisable dans " + profile.getCooldownManager().getTimeLeft(kit.getName()) + "s");
                        }
                    }
                }
                else if(event.getClick() == ClickType.RIGHT){
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
        if(kit.getMoneyCost() >= 0) {
            item.addLoreLine(ChatColor.GREEN + "Débloqué !");
        }
        if(!profile.getCooldownManager().canGet(kit.getName())){
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

        if(kit.getMoneyCost() >= 0) {
            item.addLoreLine(ChatColor.RED + "Bloqué" + " " + kit.getMoneyCost() + "$");
        }
        if(kit.getLevelCost() > 0) {
            item.addLoreLine(ChatColor.RED + "Il faut être niveau " + kit.getLevelCost() + " !");
        }
        return item.toItemStack();
    }
}
