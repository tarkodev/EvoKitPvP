package fr.tarkod.kitpvp.listeners.custom;

import fr.tarkod.kitpvp.profile.Profile;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.util.Set;

public class EGPlayerDeathByEntityEvent extends Event {

	private Player victim;
    private Entity killer;

    private double damage;
    private boolean isGappleLoot;
    private int moneyDrop;
    private int experienceDrop;
    
    public EGPlayerDeathByEntityEvent(Player victim, Entity killer, double damage) {
        this.victim = victim;
        this.killer = killer;
        this.damage = damage;
        this.isGappleLoot = true;
        this.moneyDrop = 20;
        this.experienceDrop = 3;
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
    
    public Entity getKiller() {
    	return killer;
    }
    
    public double getDamage() {
    	 return damage;
    }
    
    public boolean isGappleLoot() {
    	return isGappleLoot;
    }
    
    public int getDroppedMoney() {
    	return moneyDrop;
    }
    
    public void setDroppedMoney(int coinsPerKill) {
    	this.moneyDrop = coinsPerKill;
    }
    
    /*
     * Seulement si un joueur meurt (pas une entity), il droppera une pomme en or par terre
     * True : Enable
     * False : Disable
     */
    public void setGappleLoot(boolean isLoot) {
    	this.isGappleLoot = isLoot;
    }
    
    public int getDroppedExperience() {
    	return experienceDrop;
    }
    
    public void setDroppedExperience(int experienceDrop) {
    	this.experienceDrop = experienceDrop;
    }
    
}