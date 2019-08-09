package fr.tarkod.kitpvp.listeners;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import io.netty.channel.Channel;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.ChannelPromise;
import net.minecraft.server.v1_8_R3.PacketPlayInUseEntity;

public class PacketListener implements Listener {
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		if(e.getPlayer().getName().equalsIgnoreCase("tarklodo")) {
			injectPlayer(e.getPlayer());
		}
	}
	
	@EventHandler
	public void onJoin(PlayerQuitEvent e) {
		removePlayer(e.getPlayer());
	}
	
	private void removePlayer(Player player) {
		Channel channel = ((CraftPlayer) player).getHandle().playerConnection.networkManager.channel;
		channel.eventLoop().submit(() ->{
			channel.pipeline().remove(player.getName());
			return null;
		});
	}
	
	public void injectPlayer(Player player) {
		ChannelDuplexHandler channelDuplexHandler = new ChannelDuplexHandler() {
			
			@Override
			public void channelRead(ChannelHandlerContext channelHandlerContext, Object packet) throws Exception {
				if(!packet.toString().startsWith("net.minecraft.server.v1_8_R3.PacketPlayInFlying")){
					//Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.YELLOW + "PACKET READ:" + ChatColor.RED + packet.toString());
					if(packet instanceof PacketPlayInUseEntity) {
						Bukkit.broadcastMessage(packet+"");
						//PacketPlayInUseEntity packet = (PacketPlayInUseEntity) packet;
					}
					super.channelRead(channelHandlerContext, packet);
				}
			}
			
			@Override
			public void write(ChannelHandlerContext channelHandlerContext, Object packet, ChannelPromise channelPromise) throws Exception {
				//Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.AQUA + "PACKET WRITE:" + ChatColor.GREEN + packet.toString());
				super.write(channelHandlerContext, packet, channelPromise);
			}
		};
		
		ChannelPipeline pipeline = ((CraftPlayer) player).getHandle().playerConnection.networkManager.channel.pipeline();
		pipeline.addBefore("packet_handler", player.getName(), channelDuplexHandler);
	}

}
