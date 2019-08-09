package fr.tarkod.kitpvp.profile;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import fr.tarkod.kitpvp.KitPvP;
import fr.tarkod.kitpvp.combat.Fight;
import fr.tarkod.kitpvp.kit.gui.KitGui;
import fr.tarkod.kitpvp.profile.atm.ATMManager;
import fr.tarkod.kitpvp.profile.cooldown.CooldownManager;
import fr.tarkod.kitpvp.profile.level.PrestigeManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import fr.tarkod.kitpvp.profile.level.LevelManager;
import fr.tarkod.kitpvp.scoreboard.ScoreBoard;
import fr.tarkod.kitpvp.utils.ScoreboardSign;
import fr.tarkod.kitpvp.utils.Title;

public class Profile {

	private UUID uuid;
	private String name;

	private int kill;
	private int death;
	private int killStreak;
	private int bestKillStreak;
	private double experience;
	private int level;
	private double money;
	private int bounty;
	private List<String> unlockedKit;

	private ProfileSettings settings;
	private ATMManager atmManager;
	private CooldownManager cooldownManager;

	private double prestigeCoins;
	private int prestige;
	private transient PrestigeManager prestigeManager;
	
	private transient KitGui ki;
	private transient ScoreboardSign ss;
	private transient LevelManager lm;
	private transient Fight fight;

	public Profile(UUID uuid) {
		this.kill = 0;
		this.death = 0;
		this.killStreak = 0;
		this.bestKillStreak = 0;
		this.experience = 0;
		this.level = 1;
		this.money = 0;
		this.bounty = 0;
		this.uuid = uuid;
		this.unlockedKit = Arrays.asList("Guerrier");
		defaultLoad();
	}

	public void defaultLoad(){
		defaultLoad(true, true, true);
	}

	public void defaultLoad(boolean scoreBoard, boolean cooldown, boolean kitgui) {
		if(cooldown) {
			if (cooldownManager == null) {
				this.cooldownManager = new CooldownManager(this);
			}
			cooldownManager.defaultLoad(this);
		}
		if(kitgui) {
			this.ki = new KitGui(this);
		}
		if(Bukkit.getPlayer(uuid) != null){
			name = Bukkit.getPlayer(uuid).getName();
		}
		if(settings == null) {
			this.settings = new ProfileSettings(true, true);
		}
		if(atmManager == null) {
			this.atmManager = new ATMManager(this);
		}
		if(prestigeManager == null) {
			this.prestigeManager = new PrestigeManager(this);
		}
		atmManager.defaultLoad(this);

		LevelManager lm = new LevelManager(this, KitPvP.getInstance());
		Fight fight = new Fight();
		this.lm = lm;
		this.fight = fight;
		if(scoreBoard) {
			ss = ScoreBoard.createScoreboard(this);
			updateScoreBoard();
		}
	}

	public void destroy(){
		ss.destroy();
	}
	
	public int getKill() {
		return kill;
	}

	public void setKill(int kill) {
		this.kill = kill;
		updateScoreBoard();
	}

	public int getDeath() {
		return death;
	}

	public void setDeath(int death) {
		this.death = death;
		updateScoreBoard();
	}

	public int getKillStreak() {
		return killStreak;
	}

	public void setKillStreak(int killStreak) {
		this.killStreak = killStreak;
		updateScoreBoard();
		// a enlever
		if(killStreak >= 10 && (killStreak % 5) == 0) {
			Bukkit.getOnlinePlayers().forEach(players -> Title.sendFullTitle(players, 10, 40, 10, ChatColor.DARK_RED + getPlayer().getName() + ChatColor.RED + " est un MONSTRE !!", ChatColor.WHITE + "" + getKillStreak() + " kills d'affilés"));
		}
	}

	public int getBestKillStreak() {
		return bestKillStreak;
	}

	public void setBestKillStreak(int bestKillStreak) {
		this.bestKillStreak = bestKillStreak;
		updateScoreBoard();
	}

	public String getName(){
		return name;
	}

	public double getExperience() {
		return experience;
	}

	public void setExperience(double experience) {
		this.experience = experience;
		lm.update();
		updateScoreBoard();
	}

	public double getMoney() {
		return money;
	}

	public void setMoney(double money) {
		this.money = money;
		updateScoreBoard();
	}

	public List<String> getUnlockedKit() {
		return unlockedKit;
	}

	public void setUnlockedKit(List<String> unlockedKit) {
		this.unlockedKit = unlockedKit;
		updateScoreBoard();
	}
	
	public KitGui getInventory() {
		return ki;
	}
	
	public void setInventory(KitGui ki) {
		this.ki = ki;
	}

	public ScoreboardSign getScoreBoard() {
		return ss;
	}
	
	public void updateScoreBoard() {
		ScoreBoard.update(this);
	}

	public String getKD() {
		DecimalFormat df = new DecimalFormat("0.##");
		
		double kd = (double) getKill() / (double) getDeath();
		
		return df.format(kd);
	}
	
	public Player getPlayer() {
		return Bukkit.getPlayer(uuid);
	}

	public int getBounty() {
		return bounty;
	}

	public void setBounty(int bounty) {
		this.bounty = bounty;
		updateScoreBoard();
	}
	
	public int getLevel() {
		return level;
	}
	
	public void setLevel(int level) {
		this.level = level;
		lm.update();
		updateScoreBoard();
	}

	public String getLevelPrefix(){
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(getPrestigeManager().getPrestigeColor() + "[");
		stringBuilder.append(getLevelManager().getLevelColor() + "" + getLevel());
		stringBuilder.append(getPrestigeManager().getPrestigeColor() + "]");
		return stringBuilder.toString();
	}
	

	public void setName(String name) {
		this.name = name;
	}

	public UUID getUniqueID(){
		return uuid;
	}

	public LevelManager getLevelManager() {
		return lm;
	}

	public ProfileSettings getSettings() {
		return settings;
	}

	public Fight getFight() {
		return fight;
	}

	public CooldownManager getCooldownManager() {
		return cooldownManager;
	}

	public ATMManager getAtmManager() {
		return atmManager;
	}

	public double getPrestigeCoins() {
		return prestigeCoins;
	}

	public void setPrestigeCoins(double prestigeCoins) {
		this.prestigeCoins = prestigeCoins;
	}

	public int getPrestige() {
		return prestige;
	}

	public void setPrestige(int prestige) {
		this.prestige = prestige;
	}

	public PrestigeManager getPrestigeManager() {
		return prestigeManager;
	}
}
