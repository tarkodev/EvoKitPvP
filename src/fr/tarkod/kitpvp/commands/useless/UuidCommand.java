package fr.tarkod.kitpvp.commands.useless;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class UuidCommand extends Command {

    public UuidCommand() {
        super("uuid");
    }

    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {
        if (sender.hasPermission("kitpvp.uuid")) {
            if (args.length == 1) {
                String a = args[0];
                if (Bukkit.getPlayer(a) != null) {
                    Player target = Bukkit.getPlayer(a);
                    sender.sendMessage(target.getUniqueId().toString());
                }
            }
        }
        return false;
    }
}
