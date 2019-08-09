package fr.tarkod.kitpvp.utils.welcome;

import java.util.*;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.tarkod.kitpvp.KitPvP;
import fr.tarkod.kitpvp.profile.Profile;

public class WelcomeCmd extends Command {
	
	public WelcomeCmd() {
		super("welcome");
		setAliases(Arrays.asList("bienvenue", "bvn", "bienvenu"));
	}
	
	public static Map<Player, WelcomeTimer> welcomeMap = new HashMap<Player, WelcomeTimer>();

	@Override
	public boolean execute(CommandSender sender, String label, String[] args) {
		Player player = (Player) sender;
		Profile playerProfile = KitPvP.getInstance().getDataManager().getProfileManager().get(player.getUniqueId());
		boolean asThankSomeone = false;
		for(Player players : welcomeMap.keySet()) {
			if(!players.equals(player)) {
				WelcomeTimer timer = welcomeMap.get(players);
				if(!timer.getPlayerAlreadyThank().contains(player)) {
					List<String> chatList = new ArrayList<>(Arrays.asList(
							"Bienvenue !",
							"Bienvenue ! :D",
							"Bienvenue " + players.getName() + " ! :D",
							"Bon jeu sur EvoGames !"
					));
					if(!asThankSomeone) {
						player.chat(chatList.get(new Random().nextInt(chatList.size())));
					}
					playerProfile.setMoney(playerProfile.getMoney() + 20);
					player.sendMessage(ChatColor.GOLD + "Tu as chaleureusement accueilli " + ChatColor.AQUA + players.getName() + ChatColor.GOLD + " ! " + ChatColor.GREEN + "+20€");
					timer.getPlayerAlreadyThank().add(player);
					asThankSomeone = true;
				}
			}
		}
		if(!asThankSomeone) {
			player.sendMessage(ChatColor.RED + "Erreur: Vous n'avez personne à qui souhaiter la bienvenue !");
		}
		return false;
	}
}
