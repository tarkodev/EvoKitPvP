package fr.tarkod.kitpvp.commands;

import fr.tarkod.kitpvp.KitPvP;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class InvseeCommand extends Command {

    private KitPvP main;

    public InvseeCommand(KitPvP main) {
        super("invsee");
        this.main = main;
    }

    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {
        if(sender instanceof Player){
            Player player = (Player) sender;
            if(player.hasPermission("kitpvp.invsee")){
                if(args.length == 1){
                    String a = args[0];
                    if(Bukkit.getPlayer(a) != null){
                        Player victim = Bukkit.getPlayer(a);
                        player.openInventory(victim.getInventory());
                    }
                }
            }
        }
        return false;
    }
}
