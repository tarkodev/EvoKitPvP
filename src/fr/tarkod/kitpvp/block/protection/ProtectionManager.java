package fr.tarkod.kitpvp.block.protection;

import fr.tarkod.kitpvp.KitPvP;
import fr.tarkod.kitpvp.block.BlockManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class ProtectionManager implements Listener {

    private KitPvP main;

    public ProtectionManager(KitPvP main) {
        this.main = main;
    }

}
