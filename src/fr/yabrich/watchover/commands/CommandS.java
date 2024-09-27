package fr.yabrich.watchover.commands;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandS implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {
		
		if(sender instanceof Player) {
			Player player = (Player)sender;
			
		if(args.length == 0) {
			player.sendMessage("§cErreur de syntaxe (Argument(s) manquant(s))");
			player.sendMessage("§cUsage : /s [joueur]");
		}
		
		else {
			if(Bukkit.getPlayer(args[0]) == null) {
				player.sendMessage("§cErreur : Joueur introuvable");
			}
			else {
				Player targetPlayer = Bukkit.getPlayer(args[0]);
				Location player_cos = new Location(player.getWorld(),player.getLocation().getX(),player.getLocation().getY(),player.getLocation().getZ());
				
				targetPlayer.teleport(player_cos);
				player.sendMessage("§6Téléportation de §e"+targetPlayer.getDisplayName()+"§6 à vous en cours...");
				targetPlayer.sendMessage("§6Vous avez été téléporté par un modérateur.");
			}
		}
		
		}
		return true;
	}

}
