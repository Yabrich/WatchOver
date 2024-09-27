package fr.yabrich.watchover.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandAlert implements CommandExecutor {

	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {
		if(sender instanceof Player) {
			Player player = (Player)sender;
			
			if(args.length == 0) {
				player.sendMessage("§cErreur de syntaxe (Argument(s) manquant(s))");
				player.sendMessage("§cUsage : /alert [joueur]");
				
				return true;
			}
			
			if(Bukkit.getPlayer(args[0]) == null) {
				player.sendMessage("§cErreur : Joueur introuvable");
				
				return true;
			}
			
			Player targetPlayer = Bukkit.getPlayer(args[0]);
			
			targetPlayer.sendTitle("§4/!\\ Alerte Staff /!\\", "§6Vous êtes demandé par un membre du staff !");
			targetPlayer.sendMessage("§6§kHHHH§r§6 -§4§l Alerte Staff§r§6 - §6§kHHHH");
			targetPlayer.sendMessage("§6Vous êtes demandé par un membre du staff !");
			
			player.sendMessage("§4[§6WatchOver§4] §3Alerte envoyée à §e"+targetPlayer.getDisplayName());
		}
		return true;
	}

}
