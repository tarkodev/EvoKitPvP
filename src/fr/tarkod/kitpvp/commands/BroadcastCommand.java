package fr.tarkod.kitpvp.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class BroadcastCommand extends Command {

    public BroadcastCommand(){
        super("broadcast");
    }

    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {
        if(sender.hasPermission("kitpvp.broadcast")) {
            StringBuilder stringBuilder = new StringBuilder();
            for (String arg : args) {
                String msg = ChatColor.translateAlternateColorCodes('&', arg);
                stringBuilder.append(msg + " ");
            }
            Bukkit.broadcastMessage(stringBuilder.toString());
        }
        return false;
    }
}
