package fr.tarkod.kitpvp.listeners;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.weather.ThunderChangeEvent;
import org.bukkit.event.weather.WeatherChangeEvent;

public class WeatherChange implements Listener {
	
	@EventHandler
	public void onWeatherChange(WeatherChangeEvent e) {
		boolean rain = e.toWeatherState();
		if(rain) e.setCancelled(true);
	}

	@EventHandler
	public void onThunderChange(ThunderChangeEvent e) {
		boolean storm = e.toThunderState();
		if(storm) e.setCancelled(true);
	}
}
