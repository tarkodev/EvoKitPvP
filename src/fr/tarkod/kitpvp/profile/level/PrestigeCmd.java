package fr.tarkod.kitpvp.profile.level;

import fr.tarkod.kitpvp.KitPvP;
import fr.tarkod.kitpvp.profile.Profile;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PrestigeCmd extends Command {

    private KitPvP main;

    public PrestigeCmd(KitPvP main) {
        super("prestige");
        this.main = main;
    }

    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {
        if(sender instanceof Player) {
            Player player = (Player) sender;
            Profile profile = main.getDataManager().getProfileManager().get(player.getUniqueId());
            if(args.length == 0){
                player.sendMessage(ChatColor.DARK_RED + "Il faut être level 100, avoir 1000$ et avoir un minimum de 10 killstreak depuis le début du serveur pour être prestige");
                player.sendMessage(ChatColor.DARK_RED + "Être prestige va te rembourser tes kits, reset tes niveaux, et diviser par 3 ton argent.");
                player.sendMessage(ChatColor.RED + "Fais /prestige confirm pour confirmer");
                player.playSound(player.getLocation(), Sound.NOTE_PLING, 1, 1);
            }
            if(args.length == 1) {
                if(args[0].equalsIgnoreCase("confirm")) {
                    profile.getPrestigeManager().update();
                }
            }
        }
        return false;
    }
}
