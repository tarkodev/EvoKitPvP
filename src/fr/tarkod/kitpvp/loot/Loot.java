package fr.tarkod.kitpvp.loot;

import fr.tarkod.kitpvp.KitPvP;
import fr.tarkod.kitpvp.utils.HologramManager;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.EntityLightning;
import net.minecraft.server.v1_8_R3.PacketPlayOutSpawnEntityWeather;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_8_R3.util.CraftMagicNumbers;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityChangeBlockEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Loot implements Listener {

    private Location loc;
    private Inventory inventory;
    private boolean isEnabled;
    private int secondsAlive;

    private FallingBlock fb;

    private KitPvP main;

    public Loot(Location loc, KitPvP main) {
        this.loc = new Location(loc.getWorld(), loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());
        this.main = main;
        this.inventory = Bukkit.createInventory(null, 9 * 3, "Coffre à Butin");
        defaultLoad();
    }

    private void defaultLoad() {
        Bukkit.getPluginManager().registerEvents(this, KitPvP.getInstance());
        List<ItemStack> itemList = randChest();

        for (ItemStack item : itemList) {
            randItemInInventory(item);
        }
    }

    private List<ItemStack> randChest() {
        List<ItemStack> itemList = new ArrayList<>();
        main.getDataManager().getLootManager().getLootProfile().getListItem().forEach(kitPvPItem -> {
            double rand = new Random().nextDouble() * 100;
            if(kitPvPItem.getItemSpecificity() != null) {
                if (rand <= kitPvPItem.getItemSpecificity().getItemRarity(main).getLootChance()) {
                    itemList.add(kitPvPItem.toItemStack(main));
                }
            }
        });
        return itemList;
    }

    private void randItemInInventory(ItemStack item) {
        int rand = new Random().nextInt(inventory.getSize());
        if (inventory.getItem(rand) == null) {
            inventory.setItem(rand, item);
        } else {
            randItemInInventory(item);
        }
    }

    public Location getLocation() {
        return loc;
    }

    public void setLocation(Location loc) {
        this.loc = loc;
    }

    @SuppressWarnings("deprecation")
    public void spawn(int secondsAlive) {
        this.secondsAlive = secondsAlive;
        Bukkit.broadcastMessage(ChatColor.AQUA + "UN " + ChatColor.GOLD + "COFFRE BONUS" + ChatColor.AQUA + " EST APPARU EN " + ChatColor.GOLD +
                loc.getBlockX() + ", " +
                loc.getBlockY() + ", " +
                loc.getBlockZ() + " !");

        for(Player players : Bukkit.getOnlinePlayers()) {
            players.playSound(players.getLocation(), Sound.GHAST_FIREBALL, 2, 2);
        }

        fb = loc.getWorld().spawnFallingBlock(new Location(loc.getWorld(), loc.getX(), loc.getY()+40, loc.getZ()), Material.WOOD, (byte) 0);
        isEnabled = true;

        new BukkitRunnable(){
            public void run(){
                if(!fb.isDead()) {
                    fb.getWorld().playEffect(fb.getLocation(), Effect.MOBSPAWNER_FLAMES, 4);
                } else {
                    spawnChest();
                    cancel();
                }
            }
        }.runTaskTimer(KitPvP.getInstance(), 0, 2);
    }

    private void spawnChest() {
        loc.getBlock().setType(Material.CHEST);
        EntityLightning entity = new EntityLightning(((CraftWorld) loc.getWorld()).getHandle(), loc.getX(), loc.getY(), loc.getZ());
        PacketPlayOutSpawnEntityWeather packet = new PacketPlayOutSpawnEntityWeather(entity);
        for(Player player : Bukkit.getOnlinePlayers()) {
            ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
            player.playSound(loc, Sound.AMBIENCE_THUNDER, 1000, 100f);
        }
        if(secondsAlive >= 0) {
            new BukkitRunnable() {

                int tickLeft = secondsAlive;

                HologramManager explosionHm = new HologramManager(new Location(loc.getWorld(), loc.getX(), loc.getY()+0.5, loc.getZ()), ChatColor.RED + "Explosion dans");
                HologramManager timerHm = new HologramManager(new Location(loc.getWorld(), loc.getX(), loc.getY()+0.2, loc.getZ()), tickLeft+ "s");

                @Override
                public void run() {
                    tickLeft--;
                    timerHm.setName(tickLeft+ "s");
                    for(Player players : Bukkit.getOnlinePlayers()) {
                        explosionHm.add(players);
                        timerHm.add(players);
                    }
                    if(tickLeft == 0) {
                        cancel();
                        for(Player players : Bukkit.getOnlinePlayers()) {
                            explosionHm.remove(players);
                            timerHm.remove(players);
                        }
                        remove();
                    }
                }
            }.runTaskTimer(KitPvP.getInstance(), 0, 20);
        }
    }

    @EventHandler
    private void whenFallingSandDrop(EntityChangeBlockEvent e) {
        if(!isEnabled) {
            return;
        }

        if(e.getEntity() instanceof FallingBlock && e.getEntity().equals(fb)) {
            e.setCancelled(true);
            spawnChest();
        }
    }

    public void remove() {
        loc.getBlock().setType(Material.AIR);
        isEnabled = false;
        loc.getWorld().playEffect(loc, Effect.EXPLOSION_HUGE, 1);
        for(Player players : Bukkit.getOnlinePlayers()) {
            players.playSound(loc, Sound.EXPLODE, 2, 2f);
        }
        if(inventory.getViewers() != null && inventory.getViewers().size() != 0) {
            for(HumanEntity he : inventory.getViewers()) {
                if(he instanceof Player) {
                    Player player = ((Player) he);
                    //player.closeInventory();
                }
            }
        }
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void openInventory(Player player) {
        player.openInventory(inventory);
    }

    @EventHandler
    private void onPlayerInteractWithLoot(PlayerInteractEvent e) {
        if(!isEnabled) {
            return;
        }

        Player player = e.getPlayer();
        if(e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            if(e.getClickedBlock() != null) {
                Block block = e.getClickedBlock();
                if(block.getLocation().equals(loc)) {
                    e.setCancelled(true);
                    openInventory(player);
                }
            }
        }
    }

    @EventHandler
    private void onClose(InventoryCloseEvent e) {
        if(!isEnabled) {
            return;
        }

        if(e.getInventory().equals(inventory)) {
            if(inventory.getViewers().size() == 1) {
                chestAnimation(false);
            }
        }
    }

    @EventHandler
    private void onOpen(InventoryOpenEvent e) {
        if(!isEnabled) {
            return;
        }

        if(e.getInventory().equals(inventory)) {
            if(inventory.getViewers().size() == 1) {
                chestAnimation(true);
            }
        }
    }

    /*
     * True : Open Animation
     * False : Close Animation
     */
    private void chestAnimation(boolean arg0) {
		/*PacketPlayOutBlockBreakAnimation packet = new PacketPlayOutBlockBreakAnimation(42, new BlockPosition(loc.getX(), loc.getY(), loc.getZ()), 9);
		for(Player player : Bukkit.getOnlinePlayers()) {
			((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
		}*/
        if(arg0) {
            ((CraftWorld) loc.getWorld()).getHandle().playBlockAction(new BlockPosition(loc.getX(), loc.getY(), loc.getZ()), CraftMagicNumbers.getBlock(loc.getBlock()), 1, 1);
        } else {
            ((CraftWorld) loc.getWorld()).getHandle().playBlockAction(new BlockPosition(loc.getX(), loc.getY(), loc.getZ()), CraftMagicNumbers.getBlock(loc.getBlock()), 1, 0);
        }
    }
}
