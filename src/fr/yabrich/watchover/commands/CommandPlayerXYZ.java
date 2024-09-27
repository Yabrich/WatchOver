package fr.yabrich.watchover.commands;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandPlayerXYZ implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {
		if(sender instanceof Player) {
			Player player = (Player)sender;
			
			if(args.length == 0) {
				player.sendMessage("§cErreur de syntaxe (Argument(s) manquant(s))");
				player.sendMessage("§cUsage : /playerxyz [joueur]");
				
				return true;
			}
			
			if(Bukkit.getPlayer(args[0]) == null) {
				player.sendMessage("§cErreur : Joueur introuvable");
				
				return true;
			}
			
			Player targetPlayer = Bukkit.getPlayer(args[0]);
			Location location = targetPlayer.getLocation();
			
			player.sendMessage("§4[§6WatchOver§4] §3Coordonnées de §e"+targetPlayer.getDisplayName()+"§3 : \nX => "+location.getBlockX()+"\nY => "+location.getBlockY()+"\nZ => "+location.getBlockZ());
		}
		return true;
	}

}
