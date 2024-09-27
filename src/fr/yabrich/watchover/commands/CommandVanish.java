package fr.yabrich.watchover.commands;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandVanish implements CommandExecutor {
	
	public static ArrayList<Player> vanished = new ArrayList<Player>();

	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {
		if(sender instanceof Player) {
			Player player = (Player)sender;
			
			//Activation vanish
			if(!vanished.contains(player)) { //Si il l'est pas déjà
				for (Player pl : Bukkit.getOnlinePlayers()) { //On le cache pour tout les joueurs connectés
					if(!pl.hasPermission("woy.vanish.seeothers")) { //Sauf ceux qu'on la perm woy.vanish.seeothers
						pl.hidePlayer(player);
					}		
				}
				vanished.add(player);
				
				sender.sendMessage("§4[§6WatchOver§4] §3Vanish §2activé §3!");
			}
		else {
			for (Player pl : Bukkit.getOnlinePlayers()) {
				pl.showPlayer(player);
			}
			vanished.remove(player);
			player.sendMessage("§4[§6WatchOver§4] §3Vanish §4désactivé §3!");
			}
			
			
		}
		return true;
	}

}
