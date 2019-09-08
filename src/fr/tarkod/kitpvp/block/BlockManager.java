package fr.tarkod.kitpvp.block;

import fr.tarkod.kitpvp.KitPvP;
import fr.tarkod.kitpvp.loot.BlockLocation;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerBucketEmptyEvent;
import org.bukkit.event.player.PlayerBucketFillEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BlockManager implements Listener {

    private KitPvP main;

    private Map<BlockLocation, Integer> blockLocations;
    private List<Player> playerList;

    private BlockRunnable blockRunnable;

    public BlockManager(KitPvP main) {
        this.main = main;
        this.blockLocations = new HashMap<>();
        this.playerList = new ArrayList<>();
        this.blockRunnable = new BlockRunnable(this);
        blockRunnable.runTaskTimer(main, 0, 1);
    }

    public void destroy(){

    }

    @EventHandler
    public void onPlaceBlock(BlockPlaceEvent event){
        addToList(event.getBlockPlaced().getLocation(), event.getPlayer());
    }

    @EventHandler
    public void onPlaceBucket(PlayerBucketEmptyEvent event){
        addToList(event.getBlockClicked().getRelative(event.getBlockFace()).getLocation(), event.getPlayer());
    }

    @EventHandler
    public void onRemoveBucket(PlayerBucketFillEvent event){
        if(!remove(event.getBlockClicked().getRelative(event.getBlockFace()).getLocation())){
            if(!playerList.contains(event.getPlayer())) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onBreakBlock(BlockBreakEvent event){
        if(!remove(event.getBlock().getLocation())){
            if(!playerList.contains(event.getPlayer())) {
                event.setCancelled(true);
            }
        }
    }

    public boolean remove(Location location){
        List<BlockLocation> blockRemoved = new ArrayList<>();

        blockLocations.keySet().stream().filter(blockLocation ->
                blockLocation.getX() == location.getBlockX() && blockLocation.getY() == location.getBlockY() && blockLocation.getZ() == location.getBlockZ()
        ).forEach(blockRemoved::add);
        if(blockRemoved.size() != 0){
            return true;
        }
        blockRemoved.forEach(blockLocation -> blockLocations.remove(blockLocation));
        return false;
    }

    public void addToList(Location location, Player player){
        if(!playerList.contains(player)) {
            blockLocations.put(new BlockLocation(location), 20*4);
        }
    }

    public Map<BlockLocation, Integer> getBlockLocations() {
        return blockLocations;
    }

    public List<Player> getPlayerList(){
        return playerList;
    }

    public void bypass(Player player){
        playerList.add(player);
    }
}
