package fr.yabrich.watchover.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandStaffChat implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {
		if(sender instanceof Player) {
			Player player = (Player)sender;
			
			if(args.length < 1) {
				player.sendMessage("§cErreur de syntaxe (Argument(s) manquant(s))");
				player.sendMessage("§cUsage : /staffchat [message]");
				player.sendMessage("§cUsage : /sc [message]");
				
				return true;
			}
			
			//Construction msg
			StringBuilder message = new StringBuilder();
			
			for(String part : args) {
				message.append(part + " ");
			}
			
			//Envoie msg
			for(Player staff : Bukkit.getOnlinePlayers()) {
				if(staff.hasPermission("woy.staffchat.use")) {
					staff.sendMessage("§4[§6StaffChat§4] §9"+player.getName()+" §7: §3"+message);
				}
			}
		}
		return true;
	}

}
