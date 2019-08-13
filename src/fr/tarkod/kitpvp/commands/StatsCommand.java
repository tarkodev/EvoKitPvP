package fr.tarkod.kitpvp.commands;

import fr.tarkod.kitpvp.KitPvP;
import fr.tarkod.kitpvp.profile.Profile;
import fr.tarkod.kitpvp.utils.ItemBuilder;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class StatsCommand extends Command {

    private KitPvP main;

    public StatsCommand(KitPvP main) {
        super("stats");
        this.main = main;
    }

    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {
        if(sender instanceof Player){
            Player player = (Player) sender;
            List<Profile> profileList = main.getDataManager().getProfileManager().getAllOfflineAndOnlineProfile();
            if(args.length == 1){
                Profile profile = profileList.stream().filter(p -> p.getName().equalsIgnoreCase(args[0])).findFirst().orElse(null);
                if(profile != null){
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append(ChatColor.AQUA + "Profile de " + profile.getLevelPrefix() + ChatColor.AQUA + " " + profile.getName() + "\n");
                    stringBuilder.append("Kills: " + profile.getKill() + "\n");
                    stringBuilder.append("Morts: " + profile.getDeath() + "\n");
                    stringBuilder.append("Ratio K/D: " + profile.getKD() + "\n");
                    stringBuilder.append("Argent: " + profile.getMoney() + "\n");
                    player.sendMessage(stringBuilder.toString());
                } else {
                    player.sendMessage(ChatColor.RED + "Erreur: Le joueur n'existe pas !");
                }
            }
        }
        return false;
    }
}