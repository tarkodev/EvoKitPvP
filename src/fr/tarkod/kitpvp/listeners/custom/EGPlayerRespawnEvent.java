package fr.tarkod.kitpvp.listeners.custom;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class EGPlayerRespawnEvent extends Event {

	private Player player;

    public EGPlayerRespawnEvent(Player player) {
        this.player = player;
    }

    private static final HandlerList HANDLERS = new HandlerList();

    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    public Player getPlayer() {
		return player;
    }
}