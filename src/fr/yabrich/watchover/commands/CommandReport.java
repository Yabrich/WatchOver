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

public class CommandReport implements CommandExecutor {
	
	private Map<Player,LocalTime> cooldown = new HashMap<>();

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {
		if(sender instanceof Player) {
			Player player = (Player)sender;
			String nickname = player.getName();
			
			if(args.length < 2) {
				player.sendMessage("§cErreur de syntaxe (Argument(s) manquant(s))");
				player.sendMessage("§cUsage : /report [pseudo] [raison]");
				
				return true;
			}
			
			//Gestion cd
			LocalTime now = LocalTime.now();
			
			LocalTime cd_joueur = cooldown.get(player);
			LocalTime cd_requis;
			
			if(cd_joueur == null) {
				cd_requis = now.minusSeconds(1);
			}
			else {
				cd_requis = cd_joueur.plusSeconds(30);
			}
			
			//Envoie msg si cooldown ok
			if(now.isAfter(cd_requis)) {
				StringBuilder raison = new StringBuilder();
				for(String part : args) {
					raison.append(part + " ");
				}
				
				player.sendMessage("§6[§4Report§6] §eVotre signalement à bien été pris en compte et envoyé aux staffs !");
				cooldown.put(player, now);
				
				for(Player staff : Bukkit.getOnlinePlayers()) {
					if(staff.hasPermission("woy.report.see")) {
						staff.sendMessage("§6[§4Report§6] §cSignalement de "+nickname+" §7: §e"+raison);
					}
				}
			}
			
			else {
				Duration cd_restant = Duration.between(now,cd_requis);
				long secondes = cd_restant.toSeconds();
				
				player.sendMessage("§6[§4Report§6] §cMerci d'attendre §4"+secondes+" secondes §cavant d'envoyer votre requête !");
			}
			
		}
		return true;
	}

}
