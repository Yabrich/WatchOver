package fr.yabrich.watchover;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class SpyCmdListerners implements Listener {
	private Set<Player> playersSpycmdOn = new HashSet<>();
	
	@EventHandler
	public void onCmdEvent(PlayerCommandPreprocessEvent event) {
		Player sender = event.getPlayer();
		String cmd = event.getMessage();
		String cmd_lower = event.getMessage().toLowerCase();
		
		//Command /spycmd
		if(sender.hasPermission("woy.spycmd.use")) {
			
		//Activation du spycmd
			if (cmd_lower.startsWith("/spycmd") || cmd_lower.startsWith("/cmdspy") || cmd_lower.startsWith("/watchover:spycmd") || cmd_lower.startsWith("/watchover:cmdspy")) {
				if(playersSpycmdOn.contains(sender)) {
					playersSpycmdOn.remove(sender);
					sender.sendMessage("§4[§6WatchOver§4] §3SpyCmd §4désactivé §3!");
				}
				else{
					playersSpycmdOn.add(sender);
					sender.sendMessage("§4[§6WatchOver§4] §3SpyCmd §2activé §3!");
				}
			}
		
		}
		
		//Envoie des messages
		for(Player players : Bukkit.getOnlinePlayers()) {
			if(playersSpycmdOn.contains(players)) {		
				if(!(sender.getDisplayName() == players.getDisplayName()) && !(sender.isOp())) { //Envoie les commandes effectués de tout les joueurs sauf sois-même et les ops
					
				String CmdMessage = "§7[§8Spy§7] §7" + sender.getName() + " : §8" + cmd;
				players.sendMessage(CmdMessage);
				}
			}
		}
	}
}
