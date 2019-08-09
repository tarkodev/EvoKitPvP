package fr.tarkod.kitpvp.block.command;

import fr.tarkod.kitpvp.KitPvP;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BypassCommand extends Command{

    private KitPvP main;

    public BypassCommand(KitPvP main) {
        super("bypass");
        this.main = main;
    }

    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {
        if(sender instanceof Player){
            Player player = (Player) sender;
            if(player.hasPermission("kitpvp.bypass")) {
                if (!main.getBlockManager().getPlayerList().contains(player)) {
                    main.getBlockManager().getPlayerList().add(player);
                    player.sendMessage("block bypass added");
                } else {
                    main.getBlockManager().getPlayerList().remove(player);
                    player.sendMessage("block bypass removed");
                }
            }
        }
        return false;
    }
}
