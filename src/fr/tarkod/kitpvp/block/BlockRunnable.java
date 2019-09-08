package fr.tarkod.kitpvp.block;

import fr.tarkod.kitpvp.item.loot.BlockLocation;
import org.bukkit.Material;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BlockRunnable extends BukkitRunnable {

    private BlockManager blockManager;

    public BlockRunnable(BlockManager blockManager) {
        this.blockManager = blockManager;
    }

    @Override
    public void run() {
        Map<BlockLocation, Integer> blockLocationIntegerMap = blockManager.getBlockLocations();
        for(BlockLocation blockLocation : blockLocationIntegerMap.keySet()){
            int blockInt = blockLocationIntegerMap.get(blockLocation);
            if(blockInt == 0){
                blockLocation.getLocation("world").getBlock().setType(Material.AIR);
            }
        }
        blockLocationIntegerMap.forEach((blockLocation, integer) -> blockLocationIntegerMap.replace(blockLocation, integer - 1));
        List<BlockLocation> blockToRemove = new ArrayList<>();
        blockLocationIntegerMap.keySet().stream().filter(blockLocation -> blockLocationIntegerMap.get(blockLocation) < 0).forEach(blockToRemove::add);
        blockToRemove.forEach(blockLocationIntegerMap::remove);
    }
}
