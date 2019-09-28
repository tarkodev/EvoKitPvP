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

public class TeamSpeakCommand extends Command {

    public TeamSpeakCommand() {
        super("teamspeak");
        setAliases(Arrays.asList("ts"));
    }

    @Override
    public boolean execute(CommandSender sender, String string, String[] args) {
        if(sender instanceof Player) {
            Player player = (Player) sender;
            TextComponent textComponent = new TextComponent(ChatColor.GOLD + "TeamSpeak:" + ChatColor.GREEN + " ts.evogames.fr");
            textComponent.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(ChatColor.GREEN + "Clique pour rejoindre le teamspeak !").create()));
            textComponent.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://ts.evogames.fr"));
            player.spigot().sendMessage(textComponent);
        }
        return false;
    }
}
