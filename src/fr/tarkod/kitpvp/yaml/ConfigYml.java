package fr.tarkod.kitpvp.yaml;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import fr.tarkod.kitpvp.KitPvP;
import fr.tarkod.kitpvp.utils.FileUtils;
import org.bukkit.permissions.Permission;
import ru.tehkode.permissions.bukkit.PermissionsEx;

public class ConfigYml {
	
	private File file;
	private YamlConfiguration config;
	private ConfigurationSection defaultCs;
	
	public ConfigYml(KitPvP main) {
		file = new File(main.getDataFolder(), "custom.yml");
		
		FileUtils.createFile(file);
		config = FileUtils.loadContentYaml(file);
		
		if(!(config.isConfigurationSection("default"))) {
			config.createSection("default");
		}
		defaultCs = config.getConfigurationSection("default");
		
		getKitChest();
		save();
	}
	
	public void save() {
		try {
			config.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public Location getKitChest() {
		ConfigurationSection cs = getSection("kit_chest");
		Location loc = new Location(getWorld(), cs.getDouble("x"), cs.getDouble("y"), cs.getDouble("z"));
		return loc;
	}
	
	public World getWorld() {
		defaultCs.addDefault("world", "world");
		return Bukkit.getWorld(defaultCs.getString("world"));
	}
	
	public ConfigurationSection getSection(String str) {
		if(!(defaultCs.isConfigurationSection(str))) {
			defaultCs.createSection(str);
		}
		return defaultCs.getConfigurationSection(str);
	}
	
	public <T> Object getObj(ConfigurationSection cs, String str, Class<T> classObj) {
		if(!(cs.isSet(str))) {
			cs.set(str, -1);
		}
		return cs.get(str, classObj);
	}
	
	public int getInt(ConfigurationSection cs, String str) {
		if(!(cs.isSet(str))) {
			cs.set(str, -1);
		}
		return cs.getInt(str);
	}
}
