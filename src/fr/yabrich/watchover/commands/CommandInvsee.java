package fr.yabrich.watchover.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.PlayerInventory;

public class CommandInvsee implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {
			
			if(sender instanceof Player) {
				Player player = (Player)sender;
			
				if(args.length == 0) {
					player.sendMessage("§cErreur de syntaxe (Argument(s) manquant(s))");
					player.sendMessage("§cUsage : /invsee [joueur]");
				}
				
				else {
					if(Bukkit.getPlayer(args[0]) == null) {
						player.sendMessage("§cErreur : Joueur introuvable");
					}
					
					else {
						Player targetPlayer = Bukkit.getPlayer(args[0]);
						
						PlayerInventory targetPlayerInv = targetPlayer.getInventory();
						player.openInventory(targetPlayerInv);
			            player.sendMessage("§4[§6WatchOver§4] §3Ouverture de l'inventaire de §b"+targetPlayer.getDisplayName());
					}
				}
			}
		return true;
	}	
}
