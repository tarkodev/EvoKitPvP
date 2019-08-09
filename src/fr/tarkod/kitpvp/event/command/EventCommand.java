package fr.tarkod.kitpvp.event.command;

import fr.tarkod.kitpvp.KitPvP;
import fr.tarkod.kitpvp.event.EventManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class EventCommand extends Command {

    private KitPvP main;

    public EventCommand(KitPvP main) {
        super("event");
        this.main = main;
    }

    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {
        if(sender instanceof Player) {
            Player player = (Player) sender;
            EventManager eventManager = main.getEventManager();
            if (player.hasPermission("kitpvp.event")) {
                if (args.length == 0) {
                    eventManager.getGui().open(player);
                }
            }
        }
        return false;
    }
}