package fr.tarkod.kitpvp.profile.atm.command;

import fr.tarkod.kitpvp.KitPvP;
import fr.tarkod.kitpvp.combat.Fight;
import fr.tarkod.kitpvp.profile.Profile;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ATMCommand extends Command {

    private KitPvP main;

    public ATMCommand(KitPvP main) {
        super("atm");
        this.main = main;
    }

    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {
        if(sender instanceof Player) {
            Player player = (Player) sender;
            Profile profile = 	main.getDataManager().getProfileManager().get(player.getUniqueId());
            profile.getAtmManager().collectATM();
        }
        return false;
    }

}