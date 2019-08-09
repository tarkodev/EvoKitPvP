package fr.tarkod.kitpvp.commands;

import fr.tarkod.kitpvp.KitPvP;
import fr.tarkod.kitpvp.profile.Profile;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import ru.tehkode.permissions.PermissionUser;
import ru.tehkode.permissions.bukkit.PermissionsEx;

import java.util.Comparator;
import java.util.List;

public class TopCommand extends Command {

    private KitPvP main;

    public TopCommand(KitPvP main) {
        super("top");
        this.main = main;
    }

    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {
        if(sender instanceof Player) {
            Player player = (Player) sender;
            List<Profile> profileList = main.getDataManager().getProfileManager().getAllOfflineAndOnlineProfile();
            Comparator<Profile> com = Comparator.comparingInt(Profile::getKill).reversed();
            profileList.sort(com);
            StringBuilder stringBuilder = new StringBuilder();
            int totalMoney = 0;
            for (Profile profile : profileList) {
                totalMoney = totalMoney + profile.getKill();
            }
            stringBuilder.append(ChatColor.YELLOW + "----" + "Classement des kills" + "----" + "\n");
            stringBuilder.append(ChatColor.YELLOW + "Total du Serveur: " + ChatColor.RED + totalMoney + "\n" + ChatColor.WHITE);
            for (int i = 0; i < 8 ; i++) {
                Profile profile = profileList.get(i);
                PermissionUser pex = PermissionsEx.getUser(profile.getUniqueID().toString());
                stringBuilder.append((i + 1) + ". " + profile.getLevelPrefix() + " " + ChatColor.translateAlternateColorCodes('&', pex.getPrefix()) + pex.getOption("group_name") + " " + profile.getName() + ChatColor.WHITE + ", " + profile.getKill() + "\n");
            }
            //stringBuilder.append(ChatColor.YELLOW + "-------------------------");
            player.sendMessage(stringBuilder.toString());
        }
        return false;
    }

}
