package fr.tarkod.kitpvp.profile;

import fr.tarkod.kitpvp.utils.all.language.Language;

public class ProfileSettings {
	
	private boolean isKillMessageVisible;
	private boolean isDeathMessageVisible;
	private Language language;
	
	public ProfileSettings(boolean killMessageVisible, boolean deathMessageVisible) {
		this.isKillMessageVisible = killMessageVisible;
		this.isDeathMessageVisible = deathMessageVisible;
	}
	
	public boolean isKillMessageVisible() {
		return isKillMessageVisible;
	}
	
	public void setKillMessageVisible(boolean isKillMessageVisible) {
		this.isKillMessageVisible = isKillMessageVisible;
	}
	
	public boolean isDeathMessageVisible() {
		return isDeathMessageVisible;
	}
	
	public void setDeathMessageVisible(boolean isDeathMessageVisible) {
		this.isDeathMessageVisible = isDeathMessageVisible;
	}

	public Language getLanguage() {
		return language;
	}

	public void setLanguage(Language language) {
		this.language = language;
	}
}
