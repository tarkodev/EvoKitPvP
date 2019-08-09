package fr.tarkod.kitpvp.commands;

import fr.tarkod.kitpvp.KitPvP;
import fr.tarkod.kitpvp.combat.Fight;
import fr.tarkod.kitpvp.profile.Profile;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TestCmd extends Command {

	private KitPvP main;

	public TestCmd(KitPvP main) {
		super("test");
		this.main = main;
	}

	@Override
	public boolean execute(CommandSender sender, String label, String[] args) {
		if(sender instanceof Player) {
			Player player = (Player) sender;
			Profile profile = 	main.getDataManager().getProfileManager().get(player.getUniqueId());
			Fight fight = profile.getFight();
			int e = main.getDataManager().getProfileManager().get(player.getUniqueId()).getFight().getTimeLeft();
			player.sendMessage(profile + "\n" + profile.getFight() + "\n" + e + "");
		}
		return false;
	}

}
