package fr.tarkod.kitpvp.profile.listeners;

import java.io.File;

import fr.tarkod.kitpvp.profile.Profile;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import fr.tarkod.kitpvp.KitPvP;

public class PlayerListener implements Listener {

	private File saveDir;
	private KitPvP main;
	
	public PlayerListener(KitPvP main) {
		this.main = main;
		this.saveDir = new File(main.getDataFolder(), "/playerData/");
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e){
	    Player player = e.getPlayer();
	    Profile profile = main.getDataManager().getProfileManager().get(player.getUniqueId());
	    profile.setName(player.getName());
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e){
        Player player = e.getPlayer();
        Profile profile = main.getDataManager().getProfileManager().get(player.getUniqueId());
        profile.destroy();
        profile.setName(player.getName());
        main.getDataManager().getProfileManager().save(player.getUniqueId());
        main.getDataManager().getProfileManager().remove(player.getUniqueId());
    }
}