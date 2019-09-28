package fr.tarkod.kitpvp.level;

import fr.tarkod.kitpvp.profile.Profile;
import fr.tarkod.kitpvp.utils.Title;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class LevelManager {

    private double base;
    private double coef;

    public LevelManager(double base, double coef){
        this.base = base;
        this.coef = coef;
    }

    public void update(Profile profile) {
        int level = getProfileLevel(profile).getLevel();
        if(level > profile.getLevel()) {
            levelUp(profile);
        }
        profile.setLevel(level);
    }

    public ProfileLevel getProfileLevel(Profile profile) {
        return checkLevel(profile.getExperience(), 0);
    }

    private void levelUp(Profile profile) {
        Player player = profile.getPlayer();

        profile.setMoney(profile.getMoney() + 20);

        Title.sendFullTitle(player, 10, 40, 10, ChatColor.AQUA + "LEVEL UP!", ChatColor.GRAY + "[" + (profile.getLevel() - 1) + "] -> [" + profile.getLevel() + "]");
        player.sendMessage(ChatColor.GREEN + "+20$ (LEVEL UP)");
        player.setHealth(player.getMaxHealth());
        player.playSound(player.getLocation(), Sound.LEVEL_UP, 1, 1);
    }

    private ProfileLevel checkLevel(double xp, int level) {
        double u = base * Math.pow(coef, level);
        if(xp >= u) return checkLevel(xp - u, level + 1);
        return new ProfileLevel(level + 1, xp, u);
    }

}