package fr.tarkod.kitpvp.kit.gui;

import fr.tarkod.kitpvp.KitPvP;
import fr.tarkod.kitpvp.kit.kit.Kit;
import fr.tarkod.kitpvp.kit.kit.KitArmor;
import fr.tarkod.kitpvp.utils.HuntiesInventory.HuntiesInventory;
import fr.tarkod.kitpvp.utils.ItemBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Material;

public class KitViewerGui extends HuntiesInventory {

    private KitPvP main;

    private Kit kit;

    public KitViewerGui(Kit kit, KitPvP main) {
        super(5*9, "Visualisation du kit", main);
        this.kit = kit;
        this.main = main;
    }

    public void load(){
        clear();
        kit.getItemMap().forEach((integer, kitPvPItem) -> setItem(integer, kitPvPItem.toItemStack(main), event -> event.setCancelled(true)));
        KitArmor kitArmor = kit.getArmor();
        if(kitArmor.getHelmet() != null) {
            setItem(9 * 4, kitArmor.getHelmet().toItemStack(main), e -> e.setCancelled(true));
        }
        if(kitArmor.getChestplate() != null) {
            setItem(9 * 4 + 1, kitArmor.getChestplate().toItemStack(main), e -> e.setCancelled(true));
        }
        if(kitArmor.getLeggings() != null) {
            setItem(9 * 4 + 2, kitArmor.getLeggings().toItemStack(main), e -> e.setCancelled(true));
        }
        if(kitArmor.getBoots() != null) {
            setItem(9 * 4 + 3, kitArmor.getBoots().toItemStack(main), e -> e.setCancelled(true));
        }
        setItem(9*4 + 8, new ItemBuilder(Material.GOLD_NUGGET).setName(ChatColor.GOLD + "Cooldown: " + kit.getCooldown() + "s").toItemStack(), e -> e.setCancelled(true));
    }
}
