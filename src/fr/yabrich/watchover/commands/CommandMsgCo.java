package fr.yabrich.watchover.commands;

import java.util.ArrayList;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandMsgCo implements CommandExecutor {
	
	public static ArrayList<Player> playersMsgCoOn = new ArrayList<Player>();

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {
		
		if(sender instanceof Player) {
			Player player = (Player)sender;
			
			if(!playersMsgCoOn.contains(player)) {
				playersMsgCoOn.add(player);
				player.sendMessage("§4[§6WatchOver§4] §3MsgCo §2activé §3!");
			}
			else {
				playersMsgCoOn.remove(player);
				player.sendMessage("§4[§6WatchOver§4] §3MsgCo §4désactivé §3!");
			}
			
			
		}
		return true;
	}

}
