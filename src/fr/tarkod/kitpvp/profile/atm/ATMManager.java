package fr.tarkod.kitpvp.profile.atm;

import fr.tarkod.kitpvp.profile.Profile;
import org.bukkit.ChatColor;
import org.bukkit.permissions.Permission;
import ru.tehkode.permissions.PermissionUser;
import ru.tehkode.permissions.bukkit.PermissionsEx;

import java.time.Instant;

public class ATMManager {

    private transient Profile profile;

    private int lastATM;
    private double moneyCollectedWithATM;

    public ATMManager(Profile profile) {
        defaultLoad(profile);
    }

    public void defaultLoad(Profile profile){
        this.profile = profile;
    }

    public void collectATM(){
        double money = lastATM * 0.05;
        PermissionUser pex = PermissionsEx.getUser(profile.getUniqueID().toString());
        if(pex.getOption("atm") != null) {
            String option = pex.getOption("atm");
            money = lastATM * Integer.parseInt(option);
        }
        moneyCollectedWithATM = moneyCollectedWithATM + money;
        profile.setMoney(profile.getMoney() + money);
        profile.getPlayer().sendMessage(ChatColor.GOLD + "Tu as reçu " + ChatColor.GREEN + money + "$" + ChatColor.GOLD + " pour être resté " + ChatColor.AQUA + lastATM + "s" + ChatColor.GOLD + " sur le serveur !");
        resetATM();
    }

    public int getLastATM() {
        return lastATM;
    }

    public void setLastATM(int lastATM) {
        this.lastATM = lastATM;
    }

    public void resetATM() {
        this.lastATM = 0;
    }
}
