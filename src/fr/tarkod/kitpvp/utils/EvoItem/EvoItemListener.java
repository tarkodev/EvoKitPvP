package fr.tarkod.kitpvp.utils.EvoItem;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.function.Consumer;

public class EvoItemListener implements Listener {

    private EvoItem evoItem;

    public EvoItemListener(EvoItem evoItem) {
        this.evoItem = evoItem;
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event){
        if(event.getItem() != null){
            if(event.getItem().equals(evoItem.getItemStack())){
                Consumer<PlayerInteractEvent> eventConsumer = evoItem.getPlayerInteractEvent();
                if(eventConsumer != null) {
                    eventConsumer.accept(event);
                }
            }
        }
    }
}
