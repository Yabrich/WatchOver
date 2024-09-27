package fr.yabrich.watchover.commands;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import fr.yabrich.watchover.Main;

public class CommandUnBan implements CommandExecutor {
	
	private final Main main;

	public CommandUnBan(Main main) {
		this.main = main;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {
		if(sender instanceof Player) {
			Player player = (Player)sender;
			FileConfiguration config = main.getConfig();
			
			if(args.length != 1) {
				player.sendMessage("§cErreur de syntaxe (Argument(s) manquant(s))");
				player.sendMessage("§cUsage : /unban [joueur]");
				
				return true;
			}
			
			if(Bukkit.getPlayer(args[0]) == null) {
				if(!config.isSet("joueurs."+args[0])) {
					player.sendMessage("§cErreur : Joueur introuvable");
					
					return true;
				}
			}
			
			String nickname = args[0];
			
			ConfigurationSection joueurSection = config.getConfigurationSection("joueurs."+nickname);
			List<String> sanctionsJoueur = joueurSection.getStringList("sanctions");
			
			for(String sanction : sanctionsJoueur) {
				if(sanction.contains("Type : Ban")) {
					int date_fin_index = sanction.indexOf("Date_Fin :");
					CharSequence date_fin = sanction.subSequence(date_fin_index+11, sanction.length());
					
					LocalDateTime today = LocalDateTime.now();
					DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
					
					LocalDateTime date_fin_formated = LocalDateTime.parse(date_fin,formatter);
					
					if(today.isBefore(date_fin_formated)) {
						sanctionsJoueur.remove(sanction);
						joueurSection.set("sanctions", sanctionsJoueur);
						main.saveConfig();
						
						player.sendMessage("§4[§6WatchOver§4] §2Sanction retirée !");
						player.sendMessage("§4[§6WatchOver§4] §3Vous venez de débannir §e"+nickname);
						
						return true;
					}
				}
			}
			
			player.sendMessage("§cErreur : Ce joueur n'est pas banni");
		}
		return true;
	}

}
