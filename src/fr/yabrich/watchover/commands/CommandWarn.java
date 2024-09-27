package fr.yabrich.watchover.commands;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import fr.yabrich.watchover.Main;

public class CommandWarn implements CommandExecutor {
	
	private Main main;

	public CommandWarn(Main main) {
		this.main = main;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {
		if(sender instanceof Player) {
			Player player = (Player)sender;
			FileConfiguration config = main.getConfig();
			
			
			if(args.length < 2) {
				player.sendMessage("§cErreur de syntaxe (Argument(s) manquant(s))");
				player.sendMessage("§cUsage : /warn [joueur] [raison]");
				
				return true;
			}
			
			if(Bukkit.getPlayer(args[0]) == null) {
				if(!config.isSet("joueurs."+args[0])) {
					player.sendMessage("§cErreur : Joueur introuvable");
					return true;
				}
			}
			else if(Bukkit.getPlayer(args[0]).hasPermission("woy.warn.use")) {
				player.sendMessage("§cErreur : Ce joueur est immunisé");
				return true;
			}
			
			//Ajout de la sanction à la config
			String nickname = args[0];
			
			StringBuilder raison = new StringBuilder();
			for(String word : args) {
				if(!word.equals(nickname)) {
					raison.append(word + " ");
				}
			}
			
			ConfigurationSection joueurSection = config.getConfigurationSection("joueurs."+nickname);
			
			
			int id_punishment = config.getInt("id_sanctions");
			List<String> sanctionsJoueur = joueurSection.getStringList("sanctions");

			config.set("id_sanctions", id_punishment+1);
			
			String date_debut = LocalDateTime.now().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT));
			
			LocalDateTime date_fin = LocalDateTime.now().plusDays(30);
			String date_fin_format = date_fin.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT));
			
			String sanction = "ID : "+id_punishment+" | Type : Warn | Raison : "+ raison + "| Moderateur : "+player.getName()+" | Date_Debut : "+date_debut+" | Date_Fin : "+date_fin_format;
			
			sanctionsJoueur.add(sanction);
			joueurSection.set("sanctions",sanctionsJoueur);
			
			main.saveConfig();
			
			//Envoie des messages joueurs
			
			player.sendMessage("§4[§6WatchOver§4] §3Vous avez averti §e"+nickname+" §3pour la raison §7: §6"+raison);
			player.sendMessage("§4[§6WatchOver§4] §3La sanction expirera le §6"+date_fin_format+" §2[§aID:"+id_punishment+"§2]");
			
			//Envoie des messages targetPlayer uniquement si joueur connecté.
			if(Bukkit.getPlayer(args[0]) != null) {
				Player targetPlayer = (Player)Bukkit.getPlayer(args[0]);
				
				targetPlayer.sendMessage("§6§kHHHH§r§6 -§4§l Avertissement§r§6 - §6§kHHHH");
				targetPlayer.sendMessage("§6Vous venez d'être averti par un modérateur pour la raison suivante : §e"+raison);
				targetPlayer.sendMessage("§6La sanction expirera le : §e"+date_fin_format);
				targetPlayer.sendMessage("§7Plus d'informations : /warnlist");
			}
		}
		return true;
	}

}
