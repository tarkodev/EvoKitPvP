package fr.tarkod.kitpvp.useless.bungee;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class BoutiqueCommand extends Command {

    public BoutiqueCommand() {
        super("boutique");
        setAliases(Arrays.asList("shop"));
    }

    @Override
    public boolean execute(CommandSender sender, String string, String[] args) {
        if(sender instanceof Player) {
            Player player = (Player) sender;
            TextComponent textComponent = new TextComponent(ChatColor.LIGHT_PURPLE + "Boutique:" + ChatColor.GREEN + " evogames.buycraft.net");
            textComponent.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(ChatColor.GREEN + "Clique pour ouvrir la boutique !").create()));
            textComponent.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://evogames.buycraft.net"));
            player.spigot().sendMessage(textComponent);
        }
        return false;
    }
}
