package fr.tarkod.kitpvp;

import java.util.Arrays;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import fr.tarkod.kitpvp.block.BlockManager;
import fr.tarkod.kitpvp.block.command.BypassCommand;
import fr.tarkod.kitpvp.combat.FightManager;
import fr.tarkod.kitpvp.commands.*;
import fr.tarkod.kitpvp.commands.bungee.BoutiqueCommand;
import fr.tarkod.kitpvp.commands.bungee.DiscordCommand;
import fr.tarkod.kitpvp.commands.bungee.TeamSpeakCommand;
import fr.tarkod.kitpvp.commands.bungee.TwitterCommand;
import fr.tarkod.kitpvp.commands.remove.RemoveCommand;
import fr.tarkod.kitpvp.commands.useless.UuidCommand;
import fr.tarkod.kitpvp.event.EventManager;
import fr.tarkod.kitpvp.event.command.EventCommand;
import fr.tarkod.kitpvp.item.itemrarity.command.ItemRarityCommand;
import fr.tarkod.kitpvp.item.itemspecificity.command.TestItemCommand;
import fr.tarkod.kitpvp.item.listeners.CancelDropKitItem;
import fr.tarkod.kitpvp.kit.chest.KitChest;
import fr.tarkod.kitpvp.kit.commands.KitCommand;
import fr.tarkod.kitpvp.listeners.*;
import fr.tarkod.kitpvp.listeners.paytowin.KillAdvantage;
import fr.tarkod.kitpvp.message.TabTitle;
import fr.tarkod.kitpvp.message.tablist.TablistHeaderFooter;
import fr.tarkod.kitpvp.message.tablist.TablistName;
import fr.tarkod.kitpvp.profile.atm.command.ATMCommand;
import fr.tarkod.kitpvp.profile.level.PrestigeCmd;
import fr.tarkod.kitpvp.profile.listeners.PlayerListener;
import fr.tarkod.kitpvp.useless.PlayerJoinMessage;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.plugin.java.JavaPlugin;

import fr.tarkod.kitpvp.bounty.BountyCheck;
import fr.tarkod.kitpvp.item.listeners.LootItemOnDeath;
import fr.tarkod.kitpvp.killstreak.KSCheck;
import fr.tarkod.kitpvp.message.PlayerChat;
import fr.tarkod.kitpvp.listeners.custom.MakeEventWork;
import fr.tarkod.kitpvp.loot.LootManager;
import fr.tarkod.kitpvp.profile.ProfileSerializationManager;
import fr.tarkod.kitpvp.utils.welcome.PlayerJoinLeft;
import fr.tarkod.kitpvp.utils.welcome.WelcomeCmd;
import fr.tarkod.kitpvp.yaml.Configurator;
import net.minecraft.server.v1_8_R3.MinecraftServer;

public class KitPvP extends JavaPlugin {
	
	public static KitPvP INSTANCE;
	
	private LootManager lm;
	private Configurator configurator;
	private KitChest kitChest;
	private DataManager dataManager;
	private Gson gson;

	private EventManager eventManager;
	private BlockManager blockManager;
	private FightManager fightManager;

	private ItemLibrary itemLibrary;
	//private Map<UUID, ScoreboardSign> boards = new HashMap<UUID, ScoreboardSign>();
	
	private ProfileSerializationManager profileSerializationManager;
	
	public static KitPvP getInstance() {
		return INSTANCE;
	}
	
	@Override
	public void onEnable() {
		INSTANCE = this;
		
		if(!getDataFolder().exists()) {
			getDataFolder().mkdirs();
		}
		
		this.profileSerializationManager = new ProfileSerializationManager();
		
		lm = new LootManager(this);
		configurator = new Configurator(this);
		kitChest = new KitChest(configurator.getConfigYml().getKitChest());
		this.gson = new GsonBuilder()
				.setPrettyPrinting()
				.serializeNulls()
				.create();

		blockManager = new BlockManager(this);
		eventManager = new EventManager(this);
		fightManager = new FightManager(this);
		dataManager = new DataManager(gson, getDataFolder(), this);
		itemLibrary = new ItemLibrary(this);
		loadListeners();
		registerCommands();
	}
	
	@Override
	public void onDisable() {
		lm.removeAllLoot();
		lm.saveFile();
		blockManager.getBlockLocations().forEach((blockLocation, integer) -> blockLocation.getLocation("world").getBlock().setType(Material.AIR));
		dataManager.getProfileManager().save();
	}
	
	public void loadListeners() {
		Arrays.asList(
				new FoodLevelChange(),
				new EntityDamageByEntity(this),
				new PlayerPickupItem(),
				new PlayerSoup(),
				new PlayerDropItem(),
				new PlayerJoin(),
				new WeatherChange(),
				new LootItemOnDeath(),
				new KSCheck(this),
				new MakeEventWork(this),
				new BountyCheck(this),
				new PlayerChat(this),
				new PlayerJoinLeft(),
				new PlayerListener(this),
				new TabTitle(this),
				new BumpPressurePlate(),
				blockManager,
				fightManager,
				new KillAdvantage(this),
				new PlayerJoinMessage(),
				new RemoveCommand(),
				new TablistName(this),
				new TablistHeaderFooter(),
				new CancelDropKitItem(),
				new PlayerRespawn(this)
		).forEach(event -> getServer().getPluginManager().registerEvents(event, this));
	}
	
	public void registerCommands() {
		Arrays.asList(
				new LootCmd(this),
				new ExperienceCmd(this),
				new KitCommand(this),
				new MoneyCmd(this),
				new PayCmd(this),
				new GainCmd(this),
				new RespawnCmd(this),
				new TestCmd(this),
				new RarityCmd(this),
				new WelcomeCmd(),
				new EventCommand(this),
				new EnderchestCommand(this),
				new BroadcastCommand(),
				new PrivateMessageCommand(),
				new BypassCommand(this),
				new BoutiqueCommand(),
				new DiscordCommand(),
				new TeamSpeakCommand(),
				new TwitterCommand(),
				new SpawnCommand(this),
				new ATMCommand(this),
				new UuidCommand(),
				new TopCommand(this),
				new InvseeCommand(this),
				new PrestigeCmd(this),
				new SoundCommand(this),
				new StatsCommand(this),
				new ItemRarityCommand(this),
				new TestItemCommand(this)
		).forEach(this::registerCommand);
	}
	
	public void registerCommand(Command cmd, String fallbackPrefix) {
		MinecraftServer.getServer().server.getCommandMap().register(cmd.getName(), fallbackPrefix, cmd);
	}

	private void registerCommand(Command cmd) {
		this.registerCommand(cmd, this.getName());
	}
	
	public ProfileSerializationManager getProfileSerializationManager() {
		return profileSerializationManager;
	}
	
	public LootManager getLootManager() {
		return lm;
	}

	public Configurator getConfigurator() {
		return configurator;
	}

	public KitChest getKitChest() {
		return kitChest;
	}

	public DataManager getDataManager() {
		return dataManager;
	}

	public EventManager getEventManager() {
		return eventManager;
	}

	public BlockManager getBlockManager() {
		return blockManager;
	}

	public FightManager getFightManager() {
		return fightManager;
	}

	public ItemLibrary getItemLibrary() {
		return itemLibrary;
	}
}