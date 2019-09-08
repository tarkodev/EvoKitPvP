package fr.tarkod.kitpvp.kit.commands;

import fr.tarkod.kitpvp.KitPvP;
import fr.tarkod.kitpvp.item.ItemRarity;
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

public class KitCommand extends Command {

    private KitPvP main;

    public KitCommand(KitPvP main) {
        super("kit");
        this.main = main;
    }

    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {
        if(sender instanceof Player){
            Player player = (Player) sender;
            Profile playerProfile = main.getDataManager().getProfileManager().get(player.getUniqueId());
            KitManager kitManager = main.getDataManager().getKitManager();
            if(args.length == 0){
                //if(!playerProfile.getFight().isFighting()) {
                    main.getDataManager().getProfileManager().get(player.getUniqueId()).getInventory().open();
                /*} else {
                    player.sendMessage(ChatColor.RED + "Tu ne peux pas faire ceci car tu es en combat pendant encore " + ChatColor.GRAY + playerProfile.getFight().getTimeLeft() + ChatColor.RED + " secondes !");
                }*/
            }
            if (args.length == 1) {
                String a = args[0];
                boolean canContinue = true;
                switch (a) {
                    case "list":
                        if(playerProfile.getUnlockedKit().size() != 0){
                            StringBuilder stringBuilder = new StringBuilder();
                            stringBuilder.append("Tu as: ");
                            playerProfile.getUnlockedKit().forEach(kit -> {
                                stringBuilder.append(kit + ", ");
                            });
                            stringBuilder.delete(stringBuilder.length() - 2, stringBuilder.length() - 1);
                            player.sendMessage(stringBuilder.toString());
                            canContinue = false;
                        }
                    default:break;
                }
                if(player.isOp()) {
                    if (canContinue) {
                        if (kitManager.getKitByName(a) != null) {
                            Kit kit = kitManager.getKitByName(a);
                            String string = playerProfile.getUnlockedKit().stream().filter(s -> s.equalsIgnoreCase(kit.getName())).findFirst().orElse(null);
                            if (string != null) {
                                kit.apply(player, main);
                            } else {
                                player.sendMessage(ChatColor.RED + "Erreur: Tu n'as pas ce kit");
                            }
                        }
                    }
                }
            }
            if(player.isOp()) {
                if (args.length == 1) {
                    String a = args[0];
                    switch (a) {
                        case "listold":
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
                            kitManager.addKit(new Kit("DefaultKit"));
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
                            kitManager.addKit(new Kit(b));
                            player.sendMessage("Kit Added");
                            break;
                        case "delete":
                            Kit kita = kitManager.getKitByName(b);
                            if(kita != null) {
                                kitManager.removeKit(kita);
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
                                if(inventory.getHelmet() != null) {
                                    armor.setHelmet(new KitPvPItem(inventory.getHelmet()));
                                }
                                if(inventory.getChestplate() != null) {
                                    armor.setChestplate(new KitPvPItem(inventory.getChestplate()));
                                }
                                if(inventory.getLeggings() != null) {
                                    armor.setLeggings(new KitPvPItem(inventory.getLeggings()));
                                }
                                if(inventory.getBoots() != null) {
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
        }
        return false;
    }
}