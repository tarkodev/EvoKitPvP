package fr.tarkod.kitpvp.kit.commands;

import fr.tarkod.kitpvp.KitPvP;
import fr.tarkod.kitpvp.kit.KitManager;
import fr.tarkod.kitpvp.kit.kit.Kit;
import fr.tarkod.kitpvp.kit.kit.KitArmor;
import fr.tarkod.kitpvp.kit.kit.KitPvPItem;
import fr.tarkod.kitpvp.profile.Profile;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class KitEditorCommand extends Command {

    private KitPvP main;

    public KitEditorCommand(KitPvP main) {
        super("kiteditor");
        this.main = main;
    }

    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            Profile profile = main.getDataManager().getProfileManager().get(player.getUniqueId());
            KitManager kitManager = main.getDataManager().getKitManager();

            if(player.isOp()) return false;

            if (args.length == 1) {
                String a = args[0];
                switch (a) {
                    case "list":
                        kitManager.getKits().forEach(kit -> {
                            player.sendMessage(ChatColor.GOLD + "-----------------------");
                            player.sendMessage(ChatColor.BLUE + "Kit: " + kit.getName());
                        });
                        break;
                    case "save":
                        kitManager.saveKits();
                        player.sendMessage("Kit Saved");
                        break;
                    case "load":
                        kitManager.loadKits();
                        player.sendMessage("Kit Reloaded");
                        break;
                    case "default":
                        kitManager.registerKit(new Kit("DefaultKit"));
                        player.sendMessage("Kit Default load (/kit load)");
                        break;
                    default:
                        break;
                }
            }
            if (args.length == 2) {
                String a = args[0];
                String b = args[1];
                switch (a) {
                    case "create":
                        kitManager.registerKit(new Kit(b));
                        player.sendMessage("Kit Added");
                        break;
                    case "delete":
                        Kit kita = kitManager.getKitByName(b);
                        if (kita != null) {
                            kitManager.unregisterKit(kita);
                            player.sendMessage("Kit Succesfully removed!");
                        } else {
                            player.sendMessage("kit null");
                        }
                        break;
                    case "set":
                        if (kitManager.getKitByName(b) != null) {
                            Kit kit = kitManager.getKitByName(b);
                            kit.clear();
                            PlayerInventory inventory = player.getInventory();
                            EntityEquipment ee = player.getEquipment();
                            for (int i = 0; i < inventory.getContents().length; i++) {
                                ItemStack[] contents = inventory.getContents();
                                if (contents[i] != null) {
                                    kit.setItem(i, new KitPvPItem(contents[i]));
                                }
                            }
                            KitArmor armor = kit.getArmor();
                            if (inventory.getHelmet() != null) {
                                armor.setHelmet(new KitPvPItem(inventory.getHelmet()));
                            }
                            if (inventory.getChestplate() != null) {
                                armor.setChestplate(new KitPvPItem(inventory.getChestplate()));
                            }
                            if (inventory.getLeggings() != null) {
                                armor.setLeggings(new KitPvPItem(inventory.getLeggings()));
                            }
                            if (inventory.getBoots() != null) {
                                armor.setBoots(new KitPvPItem(inventory.getBoots()));
                            }
                            player.sendMessage("Kit Set");
                        }
                        break;
                    case "get":
                        if (kitManager.getKitByName(b) != null) {
                            Kit kit = kitManager.getKitByName(b);
                            kit.apply(player, main);
                            player.sendMessage("Kit applied !");
                        } else {
                            player.sendMessage("kit not defined");
                        }
                    default:
                        break;
                }
            }
        }
        return false;
    }
}