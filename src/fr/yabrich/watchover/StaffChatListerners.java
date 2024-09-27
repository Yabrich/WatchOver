package fr.yabrich.watchover;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class StaffChatListerners implements Listener {
	@EventHandler
	public void onPlayerMessage(AsyncPlayerChatEvent event) {
		Player player = event.getPlayer();
		String message = event.getMessage();
		
		if(player.hasPermission("woy.staffchat.use") && message.startsWith("*")) {
			message = (String)message.subSequence(1, message.length());
			if(message.length() != 0) {
				player.performCommand("watchover:staffchat "+message);
				
			}
			else {
				player.sendMessage("§cErreur : Message vide");
			}
			
			event.setCancelled(true);
		}
	}
}
