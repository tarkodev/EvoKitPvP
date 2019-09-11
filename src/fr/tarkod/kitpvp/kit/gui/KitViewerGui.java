package fr.tarkod.kitpvp.kit.gui;

import fr.tarkod.kitpvp.KitPvP;
import fr.tarkod.kitpvp.kit.kit.Kit;
import fr.tarkod.kitpvp.kit.kit.KitArmor;
import fr.tarkod.kitpvp.utils.EvoInventory.EvoInvItem;
import fr.tarkod.kitpvp.utils.EvoInventory.EvoInventory;
import fr.tarkod.kitpvp.utils.ItemBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class KitViewerGui {

    private KitPvP main;

    private Kit kit;
    private EvoInventory inventory;

    public KitViewerGui(Kit kit, KitPvP main) {
        this.kit = kit;
        this.main = main;
        this.inventory = new EvoInventory("Visualisation du kit " + kit.getName(), 9*5, main);
    }

    public void open(Player player){
        player.openInventory(inventory.getInventory());
        load();
    }

    public void load(){
        inventory.clear();
        kit.getItemMap().forEach((integer, kitPvPItem) -> inventory.setItem(integer, new EvoInvItem(kitPvPItem.toItemStack(main), event -> event.setCancelled(true))));
        KitArmor kitArmor = kit.getArmor();
        if(kitArmor.getHelmet() != null) {
            inventory.setItem(9 * 4, new EvoInvItem(kitArmor.getHelmet().toItemStack(main), e -> e.setCancelled(true)));
        }
        if(kitArmor.getChestplate() != null) {
            inventory.setItem(9 * 4 + 1, new EvoInvItem(kitArmor.getChestplate().toItemStack(main), e -> e.setCancelled(true)));
        }
        if(kitArmor.getLeggings() != null) {
            inventory.setItem(9 * 4 + 2, new EvoInvItem(kitArmor.getLeggings().toItemStack(main), e -> e.setCancelled(true)));
        }
        if(kitArmor.getBoots() != null) {
            inventory.setItem(9 * 4 + 3, new EvoInvItem(kitArmor.getBoots().toItemStack(main), e -> e.setCancelled(true)));
        }
        inventory.setItem(9*4 + 8, new EvoInvItem(new ItemBuilder(Material.GOLD_NUGGET).setName(ChatColor.GOLD + "Cooldown: " + kit.getCooldown() + "s").toItemStack(), e -> e.setCancelled(true)));
    }
}
