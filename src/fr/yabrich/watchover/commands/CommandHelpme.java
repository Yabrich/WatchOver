package fr.yabrich.watchover.commands;

import java.time.Duration;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandHelpme implements CommandExecutor {
	
	private Map<Player,LocalTime> cooldown = new HashMap<>();

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {
		if(sender instanceof Player) {
			Player player = (Player)sender;
			String nickname = player.getName();
			
			if(args.length == 0) {
				player.sendMessage("§cErreur de syntaxe (Argument(s) manquant(s))");
				player.sendMessage("§cUsage : /helpme [question]");
				
				return true;
			}
			
			//Gestion du cooldown de la commande
			LocalTime now = LocalTime.now();
			
			LocalTime cd_joueur = cooldown.get(player);
			LocalTime cd_requis;
			
			if(cd_joueur == null) {
				cd_requis = now.minusSeconds(1);
			}
			else {
				cd_requis = cd_joueur.plusSeconds(30);
			}
			 
			//Envoie du message si le cooldown est respecté
			if(now.isAfter(cd_requis)) {
				
				StringBuilder question = new StringBuilder();
				for(String part : args) {
					question.append(part + " ");
				}
				
				player.sendMessage("§2[§aHelpMe§2] §6Votre demande à bien été prise en compte et envoyée aux staffs !");
				cooldown.put(player, now);
				
				for(Player staff : Bukkit.getOnlinePlayers()) {
					if(staff.hasPermission("woy.helpme.ans")) {
						staff.sendMessage("§2[§aHelpMe§2] §8§o(/ans "+nickname+" pour répondre) §e"+nickname+" §7: §6"+question);
					}
				}
			}
			//Si cooldown non respecté
			else {
				Duration cd_restant = Duration.between(now,cd_requis);
				long secondes = cd_restant.toSeconds();
				
				player.sendMessage("§2[§aHelpMe§2] §cMerci d'attendre §4"+secondes+" secondes §cavant d'envoyer votre requête !");
			}
			
		}
		return true;
	}
}
