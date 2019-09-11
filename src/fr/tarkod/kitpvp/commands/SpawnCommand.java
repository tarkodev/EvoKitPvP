package fr.tarkod.kitpvp.commands;

import fr.tarkod.kitpvp.KitPvP;
import fr.tarkod.kitpvp.combat.FightManager;
import fr.tarkod.kitpvp.profile.Profile;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SpawnCommand extends Command {

    private KitPvP main;

    public SpawnCommand(KitPvP main) {
        super("spawn");
        this.main = main;
    }

    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {
        if(sender instanceof Player){
            Player player = (Player) sender;
            Profile profile = main.getDataManager().getProfileManager().get(player.getUniqueId());
            FightManager fightManager = main.getFightManager();
            if(!fightManager.isFighting(profile)){
                player.teleport(new Location(player.getWorld(), 0.5, 25, 0.5));
                player.setHealth(player.getMaxHealth());
                player.sendMessage(ChatColor.GOLD + "Téléportation au Spawn...");
            } else {
                player.sendMessage(ChatColor.RED + "Tu ne peux pas faire ceci car tu es en combat pendant encore " + ChatColor.GRAY + fightManager.getTimeLeft(profile) + ChatColor.RED + " secondes !");
            }
        }
        return false;
    }
}

