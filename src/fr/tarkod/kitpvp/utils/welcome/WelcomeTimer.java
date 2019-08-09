package fr.tarkod.kitpvp.utils.welcome;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class WelcomeTimer extends BukkitRunnable {

	private Player player;
	
	public WelcomeTimer(Player player) {
		this.player = player;
	}
	
	private List<Player> playerAlreadyThank = new ArrayList<Player>();
	
	int timeLeft = 20;
	
	@Override
	public void run() {
		timeLeft--;
		if(timeLeft == 0) {
			WelcomeCmd.welcomeMap.remove(player);
			cancel();
		}
	}

	public List<Player> getPlayerAlreadyThank() {
		return playerAlreadyThank;
	}
}
