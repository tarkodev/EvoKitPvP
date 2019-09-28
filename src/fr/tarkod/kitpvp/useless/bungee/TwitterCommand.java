package fr.tarkod.kitpvp.useless.bungee;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TwitterCommand extends Command {

    public TwitterCommand() {
        super("twitter");
    }

    @Override
    public boolean execute(CommandSender sender, String string, String[] args) {
        if(sender instanceof Player) {
            Player player = (Player) sender;
            TextComponent textComponent = new TextComponent(ChatColor.RED + "Twitter:" + ChatColor.GREEN + " twitter.evogames.fr");
            textComponent.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(ChatColor.GREEN + "Clique pour suivre le twitter !").create()));
            textComponent.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://twitter.com/EvoGamesFR"));
            player.spigot().sendMessage(textComponent);
        }
        return false;
    }
}
