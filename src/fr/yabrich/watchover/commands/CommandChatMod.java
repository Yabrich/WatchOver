package fr.yabrich.watchover.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandChatMod implements CommandExecutor {
	
	public static boolean chatstatut = true;
	public static int chatslowmode = 0;

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {
		if(sender instanceof Player) {
			Player player = (Player)sender;
			
			if(args.length < 1) {
				player.sendMessage("§cErreur de syntaxe (Argument(s) manquant(s))");
				player.sendMessage("§cUsage : /chat (enable/disable)");
				player.sendMessage("§cUsage : /chat (slowmode) [nb_secondes]");
				
				return true;
			}
			//Chat Disable
			if(args[0].equalsIgnoreCase("disable")) {
				if(chatstatut == true) {
					chatstatut = false;
					Bukkit.broadcastMessage("§4[§6Chat§4] §eLe chat à été §4désactivé");
				}
				else {
					player.sendMessage("§4[§6Chat§4] §cErreur : Le chat est déjà désactivé");
				}
				return true;
			}
			//Chat Enable
			if(args[0].equalsIgnoreCase("enable")) {
				if(chatstatut == false) {
					chatstatut = true;
					Bukkit.broadcastMessage("§4[§6Chat§4] §eLe chat à été §aréactivé");
				}
				else {
					player.sendMessage("§4[§6Chat§4] §cErreur : Le chat est déjà activé");
				}
						
				return true;
			}
			//Chat slowmode
			if(args[0].equalsIgnoreCase("slowmode")) {
				
				if(args.length >= 2) {
					try {
						int cd = Integer.parseInt(args[1]);
						if(cd == chatslowmode) {
							player.sendMessage("§4[§6Chat§4] §cErreur : Le slowmode est déjà activé à §4"+cd+" §csecondes");
							
							return true;
						}
						chatslowmode = cd;
						
						if(cd == 0) {
							Bukkit.broadcastMessage("§4[§6Chat§4] §eLe slowmode du chat à bien été §4désactivé");
						}
						else {
							Bukkit.broadcastMessage("§4[§6Chat§4] §eLe slowmode du chat à été §aactivé §eà §6"+cd+" §esecondes");
						}
							
					} catch (NumberFormatException e) {
						player.sendMessage("§4[§6Chat§4] §cErreur : Nombre de secondes non valide");
					}
				}
					
				else {
					player.sendMessage("§4[§6Chat§4] §cErreur : Nombre de secondes manquant");
				}
				
				return true;
			}
		}
		return true;
	}

}
