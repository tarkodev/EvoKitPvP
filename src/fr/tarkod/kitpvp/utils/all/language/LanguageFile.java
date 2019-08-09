package fr.tarkod.kitpvp.utils.all.language;

import java.io.File;

import org.bukkit.configuration.file.YamlConfiguration;

import fr.tarkod.kitpvp.KitPvP;

public class LanguageFile {
	
	private File file;
	private YamlConfiguration config;
	private Language defaultLanguage = Language.FR;
	
	public LanguageFile(Language lang) {
		file = new File(KitPvP.getInstance().getDataFolder() + File.separator + "lang", lang.name() + ".lang");
		
		config = YamlConfiguration.loadConfiguration(file);
	}
	
	public String get(LanguageWord word) {
		if(config.getString(word.name()) == null) {
			return new LanguageFile(defaultLanguage).config.getString(word.name());
		}
		return config.getString(word.name());
	}
	
}
