package fr.yabrich.watchover;

import java.time.Duration;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import fr.yabrich.watchover.commands.CommandChatMod;

public class ChatModListerners implements Listener {
	
	private Map<Player,LocalTime> cooldown = new HashMap<>();
	
	@EventHandler
	public void onPlayerMessage(AsyncPlayerChatEvent event) {
		Player player = event.getPlayer();
		
		if(CommandChatMod.chatstatut == false && !(player.hasPermission("woy.chatmod.bypass"))) {
			event.setCancelled(true);
			player.sendMessage("§4[§6Chat§4] §cImpossible : Le chat est désactivé");
		}
		
		if(CommandChatMod.chatslowmode != 0 && !(player.hasPermission("woy.chatmod.bypass"))) {
			LocalTime now = LocalTime.now();
			
			LocalTime cd_joueur = cooldown.get(player);
			LocalTime cd_requis;
			
			if(cd_joueur == null) {
				cd_requis = now.minusSeconds(1);
			}
			else {
				cd_requis = cd_joueur.plusSeconds(CommandChatMod.chatslowmode);
			}
			
			if(!now.isAfter(cd_requis)) {
				event.setCancelled(true);
				
				Duration cd_restant = Duration.between(now,cd_requis);
				long secondes = cd_restant.toSeconds();
				
				player.sendMessage("§4[§6Chat§4] §cLe chat est en slowmode, merci d'attendre §4"+secondes+" §csecondes avant d'envoyer votre message");
			}
			else {
				cooldown.put(player, now);
			}
		}
	}

}
