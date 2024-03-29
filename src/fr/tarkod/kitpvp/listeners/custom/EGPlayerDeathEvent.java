package fr.tarkod.kitpvp.listeners.custom;

import fr.tarkod.kitpvp.profile.Profile;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

import java.util.Set;

public class EGPlayerDeathEvent extends Event {

	private Player victim;
    private Set<Profile> assister;

    private DamageCause cause;
    private double damage;
    
    private boolean isCancelled;

    public EGPlayerDeathEvent(Player victim, Set<Profile> assister, DamageCause cause, double damage) {
        this.victim = victim;
        this.assister = assister;
        this.cause = cause;
        this.damage = damage;
        this.isCancelled = false;
    }

    private static final HandlerList HANDLERS = new HandlerList();

    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    public Player getVictim() {
		return victim;
    }
    
    public DamageCause getDamageCause() {
    	return cause;
    }
    
    public double getDamage() {
    	return damage;
    }
    
    public boolean isCancelled() {
        return this.isCancelled;
    }

    public void setCancelled(boolean isCancelled) {
        this.isCancelled = isCancelled;
    }

    public Set<Profile> getAssister() {
        return assister;
    }
}