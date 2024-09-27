package fr.yabrich.watchover;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import fr.yabrich.watchover.commands.CommandMsgCo;

public class MsgCoListerners implements Listener {
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player player_connected = event.getPlayer();
		
		for(Player player : CommandMsgCo.playersMsgCoOn) {
			player.sendMessage("§4[§6MSGCO§4] §e"+player_connected.getDisplayName()+" §6vient de se connecter au serveur");
		}
	}
	
	@EventHandler
	public void onPlayerLeave(PlayerQuitEvent event) {
		Player player_disconnected = event.getPlayer();
		
		for(Player player : CommandMsgCo.playersMsgCoOn) {
			player.sendMessage("§4[§6MSGCO§4] §e"+player_disconnected.getDisplayName()+" §6vient de se déconnecter au serveur");
		}
	}
}
