package fr.tarkod.kitpvp.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.tarkod.kitpvp.KitPvP;
import fr.tarkod.kitpvp.utils.timer.RespawnTimer;

public class RespawnCmd extends Command {

	private KitPvP main;

	public RespawnCmd(KitPvP main) {
		super("respawn");
		this.main = main;
		this.setPermission("kitpvp.respawn");
	}

	@Override
	public boolean execute(CommandSender sender, String label, String[] args) {
		if(sender instanceof Player) {
			Player player = (Player) sender;
			new RespawnTimer(player, Integer.parseInt(args[0])).runTaskTimer(main, 20, 20);
		}
		return false;
	}

}

