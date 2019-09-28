package fr.tarkod.kitpvp.kit.commands;

import fr.tarkod.kitpvp.KitPvP;
import fr.tarkod.kitpvp.kit.KitManager;
import fr.tarkod.kitpvp.kit.kit.Kit;
import fr.tarkod.kitpvp.kit.kit.KitArmor;
import fr.tarkod.kitpvp.kit.kit.KitPvPItem;
import fr.tarkod.kitpvp.profile.Profile;
import org.bukkit.Bukkit;
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
        if(sender instanceof Player) {
            Player player = (Player) sender;
            Profile playerProfile = main.getDataManager().getProfileManager().get(player.getUniqueId());
            KitManager kitManager = main.getDataManager().getKitManager();
            if(args.length == 0) {
                if(kitManager.isEnable()) {
                    kitManager.openSelectionGui(playerProfile);
                } else {
                    player.sendMessage(ChatColor.RED + "L'ouverture du menu de kit est désactivé ! (Event ou Bug)");
                }
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
                if (canContinue) {
                    if (kitManager.getKitByName(a) != null) {
                        Kit kit = kitManager.getKitByName(a);
                        kitManager.setKit(kit, playerProfile);
                    } else {
                        player.sendMessage(ChatColor.RED + "Erreur: Ce kit n'existe pas");
                    }
                }
            }
            if(player.isOp()) {
                if(args.length == 3){
                    String a = args[0];
                    String b = args[1];
                    String c = args[2];

                    if(Bukkit.getPlayer(b) == null) return false;
                    if(kitManager.getKitByName(c) == null) return false;

                    Player victim = Bukkit.getPlayer(b);
                    Kit kit = kitManager.getKitByName(c);
                    Profile profile = main.getDataManager().getProfileManager().get(victim.getUniqueId());

                    switch (a.toLowerCase()) {
                        case "refund":
                            kitManager.refund(kit, profile);
                            break;
                        case "remove":
                            kitManager.remove(kit, profile);
                            break;
                        case "unlock":
                            kitManager.unlock(kit, profile);
                            break;
                        case "unlockForce":
                            kitManager.unlockForce(kit, profile);
                            break;
                        case "set":
                            kitManager.setKitForce(kit, profile);break;
                        default:break;
                    }
                }
            }
        }
        return false;
    }
}