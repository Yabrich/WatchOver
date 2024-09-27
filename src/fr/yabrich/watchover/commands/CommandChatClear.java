package fr.yabrich.watchover.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandChatClear implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {
		if(sender instanceof Player) {
			Player player = (Player)sender;
			
			for(int i = 0;i < 100;i++) {
				Bukkit.broadcastMessage(" ");
			}
			Bukkit.broadcastMessage("§4[§6Chat§4] §eLe chat à été clear");
			
			for(Player staff : Bukkit.getOnlinePlayers()) {
				if(staff.hasPermission("woy.chatclear.use")) {
					staff.sendMessage("§4[§6Chat§4] §eChatClear effectué par §6"+player.getName());
				}
			}
		}
		return true;
	}

}