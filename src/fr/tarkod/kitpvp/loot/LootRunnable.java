package fr.tarkod.kitpvp.loot;

import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.logging.Level;

import fr.tarkod.kitpvp.item.loot.Loot;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.scheduler.BukkitRunnable;

import fr.tarkod.kitpvp.KitPvP;

public class LootRunnable extends BukkitRunnable {

	private int tick;
	
	@Override
	public void run() {
		if(Bukkit.getOnlinePlayers().size() >= 1) {
			tick++;
		}
		
		if(tick == 60*5) {
			tick = 0;
			KitPvP main = KitPvP.getInstance();
			/*LootManager lm = main.getLootManager();
			LootProfile lp = lm.getLootProfile();
			List<Location> locList = lp.getLocationList();
			if(locList.size() > 0) {
				Loot loot = new Loot(locList.get(new Random().nextInt(locList.size())));
				loot.spawn(60*2);
				lm.getLootList().add(loot);
			} else {
				Bukkit.getConsoleSender().sendMessage("Please add a lot location.");
			}*/
		}
	}
}
