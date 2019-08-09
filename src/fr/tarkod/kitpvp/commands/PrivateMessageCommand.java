package fr.tarkod.kitpvp.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class PrivateMessageCommand extends Command {

    public PrivateMessageCommand() {
        super("pm");
        setAliases(Arrays.asList("privatemessage"));
    }

    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {
        if(sender.hasPermission("kitpvp.admin")) {
            if (args.length >= 2) {
                if (Bukkit.getPlayer(args[0]) == null) {
                    sender.sendMessage("player is null");
                    return false;
                }
                Player player = Bukkit.getPlayer(args[0]);
                StringBuilder stringBuilder = new StringBuilder();
                for (int i = 1; i < args.length; i++) {
                    String message = ChatColor.translateAlternateColorCodes('&', args[i] + " ");
                    stringBuilder.append(message);
                }
                player.sendMessage(stringBuilder.toString());
            }
        }
        return false;
    }
}
