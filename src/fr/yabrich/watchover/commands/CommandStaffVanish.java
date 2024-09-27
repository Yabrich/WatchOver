package fr.yabrich.watchover.commands;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandStaffVanish implements CommandExecutor {
	
	public static ArrayList<Player> staffvanished = new ArrayList<Player>();

	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {
		if(sender instanceof Player) {
			Player player = (Player)sender;
			
			//Activation staffvanish
			if(!staffvanished.contains(player)) {
				for (Player pl : Bukkit.getOnlinePlayers()) {
					if(!pl.hasPermission("woy.staffvanish.seeothers")) {
						pl.hidePlayer(player);
					}
				}
				staffvanished.add(player);
				player.sendMessage("§4[§6WatchOver§4] §3StaffVanish §2activé §3!");
			}
			else {
				for (Player pl : Bukkit.getOnlinePlayers()){
					pl.showPlayer(player);
				}
				staffvanished.remove(player);
				player.sendMessage("§4[§6WatchOver§4] §3StaffVanish §4désactivé §3!");
			}
			
		
		}
		return true;
	}

}
