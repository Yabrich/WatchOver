package fr.yabrich.watchover.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandAns implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {
		if(sender instanceof Player) {
			Player player = (Player)sender;
			
			if(args.length < 2) {
				player.sendMessage("§cErreur de syntaxe (Argument(s) manquant(s))");
				player.sendMessage("§cUsage : /ans [joueur] [réponse]");
				
				return true;
			}
			
			if(Bukkit.getPlayer(args[0]) == null) {
				player.sendMessage("§cErreur : Joueur introuvable");
				
				return true;
			}
			
			if(args[0].equalsIgnoreCase(player.getName())) {
				player.sendMessage("§cErreur : Vous ne pouvez pas vous répondre à vous même !");
				
				return true;
			}
			
			Player targetPlayer = Bukkit.getPlayer(args[0]);
			
			StringBuilder answer = new StringBuilder();
			for(String part : args) {
				if(!part.equalsIgnoreCase(args[0])) {
					answer.append(part + " ");
				}
			}
			
			for(Player staff : Bukkit.getOnlinePlayers()) {
				if(staff.hasPermission("woy.helpme.ans")) {
					staff.sendMessage("§2[§aHelpMe§2] §2Réponse de §a"+player.getName()+" §2à §a"+targetPlayer.getName()+" §7: §6"+answer);
				}
			}
			targetPlayer.sendMessage("§2[§aHelpMe§2] §2Réponse de §a"+player.getName()+" §7: §6"+answer);
		}
		return true;
	}

}
