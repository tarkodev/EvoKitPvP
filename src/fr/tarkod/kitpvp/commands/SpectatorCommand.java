package fr.tarkod.kitpvp.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class SpectatorCommand extends Command {

    public SpectatorCommand() {
        super("spec");
        setAliases(Arrays.asList("spectator", "spectateur"));
    }

    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {
        if(!(sender instanceof Player)){
            return false;
        }
        Player player = (Player) sender;
        return false;
    }
}
