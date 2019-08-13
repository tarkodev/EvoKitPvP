package fr.tarkod.kitpvp.profile.level;

import fr.tarkod.kitpvp.KitPvP;
import fr.tarkod.kitpvp.kit.KitManager;
import fr.tarkod.kitpvp.profile.Profile;
import fr.tarkod.kitpvp.utils.Title;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;

public class PrestigeManager {

    private Profile profile;

    public PrestigeManager(Profile profile) {
        this.profile = profile;
    }

    public boolean update(){
        if(profile.getLevel() >= 100 && profile.getMoney() >= 1000 && profile.getBestKillStreak() >= 10){
            levelUp();
            profile.getPlayer().sendMessage(ChatColor.GREEN + "" + ChatColor.BOLD + "Félicitations! " + ChatColor.GOLD + ChatColor.BOLD + "Tu es maintenant Prestige " + profile.getPrestige());
            Bukkit.broadcastMessage(ChatColor.RED + "Félicitation à " + ChatColor.GOLD + profile.getName() + ChatColor.RED + " qui est passé " + profile.getPrestigeManager().getPrestigeColor() + "Prestige " + profile.getPrestige() );
            return true;
        } else {
            profile.getPlayer().sendMessage(ChatColor.RED + "Il faut être level 100, avoir 1000$ et avoir un minimum de 10 killstreak depuis le début du serveur");
            return false;
        }
    }

    public void levelUp(){
        profile.setPrestige(profile.getPrestige() + 1);
        profile.getUnlockedKit().forEach(s -> {
            KitManager kitManager = KitPvP.getInstance().getDataManager().getKitManager();
            if (kitManager.getKitByName(s) != null)
                profile.setMoney(profile.getMoney() + KitPvP.getInstance().getDataManager().getKitManager().getKitByName(s).getMoneyCost());
        });
        profile.getUnlockedKit().clear();
        profile.setMoney(profile.getMoney() / 3);
        profile.setPrestigeCoins(profile.getPrestigeCoins() + 100);
        profile.setLevel(0);
        profile.setBounty(0);
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "cc give virtual basic 1 " + profile.getName());
        Bukkit.getOnlinePlayers().forEach(player -> {
            Title.sendFullTitle(player, 40, 100, 40, ChatColor.GOLD + profile.getName(), getPrestigeColor() + "Prestige " + profile.getPrestige());
            player.playSound(player.getLocation(), Sound.WITHER_DEATH, 1, 1);
        });
    }

    public String getPrestigeColor(){
        int l = profile.getPrestige();
        StringBuilder stringBuilder = new StringBuilder();
        ChatColor chatColor = null;
        if(l == 0){
            chatColor = ChatColor.GRAY;
        } else if(l == 1){
            chatColor = ChatColor.GREEN;
        } else if(l == 2){
            chatColor = ChatColor.YELLOW;
        } else if(l == 3){
            chatColor = ChatColor.GOLD;
        } else if(l == 4){
            chatColor = ChatColor.LIGHT_PURPLE;
        } else if(l >= 5){
            chatColor = ChatColor.RED;
        }

        stringBuilder.append(chatColor);
        return stringBuilder.toString();
    }
}
