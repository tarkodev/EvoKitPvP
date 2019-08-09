package fr.tarkod.kitpvp.utils.all.language;

import org.bukkit.entity.Player;

import fr.tarkod.kitpvp.KitPvP;
import fr.tarkod.kitpvp.profile.Profile;

public class LanguageManager {
	
	LanguageFile lFile;
	
	public LanguageManager(Player player) {
		Profile profile = KitPvP.getInstance().getDataManager().getProfileManager().get(player.getUniqueId());
		lFile = new LanguageFile(profile.getSettings().getLanguage());
	}
	
	public String get(LanguageWord word, String[] list) {
		String result = lFile.get(word);
		for (int i = 0; i < list.length; i++) {
			String str = list[i];
			str.replace("%" + (i+1) + "%", str);
		}
		return result;
	}
	
	public String get(LanguageWord word) {
		String result = lFile.get(word);
		return result;
	}
}
