package fr.tarkod.kitpvp.commands;

import fr.tarkod.kitpvp.KitPvP;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class SoundCommand extends Command {

    private KitPvP main;

    public SoundCommand(KitPvP main) {
        super("sound");
        this.main = main;
    }

    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {
        if(sender.hasPermission("kitpvp.sound")) {
            Bukkit.getOnlinePlayers().forEach(player -> player.playSound(player.getLocation(), Sound.WITHER_DEATH, 1, 1));
        }
        return false;
    }

}

