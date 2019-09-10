package fr.tarkod.kitpvp.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class TphereCommand extends Command {

    public TphereCommand() {
        super("tphere");
        setAliases(Arrays.asList("tph"));
    }

    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {
        if(!(sender instanceof Player)){
            return false;
        }
        Player player = (Player) sender;
        if(!player.hasPermission("kitpvp.tphere")){
            return false;
        }
        if(args.length == 1) {
            if(Bukkit.getPlayer(args[0]) == null) {
                player.sendMessage(ChatColor.RED + "Erreur: Le joueur ciblé n'est actuellement pas connecté");
                return false;
            }
            Player victim = Bukkit.getPlayer(args[0]);
            victim.teleport(player.getLocation());
            player.sendMessage(ChatColor.GREEN + "Tu as téléporté " + victim.getName() + " à toi");
            victim.sendMessage(ChatColor.GREEN + "Tu as été téléporté à " + player.getName());
        }
        return false;
    }
}
