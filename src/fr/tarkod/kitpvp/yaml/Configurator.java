package fr.tarkod.kitpvp.yaml;

import fr.tarkod.kitpvp.KitPvP;

public class Configurator {
	
	private ConfigYml configYml;
	
	public Configurator(KitPvP main) {
		this.configYml = new ConfigYml(main);
	}

	public ConfigYml getConfigYml() {
		return configYml;
	}
}
