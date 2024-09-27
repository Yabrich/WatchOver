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

public class CommandBan implements CommandExecutor {
	
	private final Main main;

	public CommandBan(Main main) {
		this.main = main;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {
		if(sender instanceof Player) {
			Player player = (Player)sender;
			FileConfiguration config = main.getConfig();
			
			if(args.length < 3) {
				player.sendMessage("§cErreur de syntaxe (Argument(s) manquant(s))");
				player.sendMessage("§cUsage : /ban [joueur] [temps] [raison]");
				
				return true;
			}
			
			if(Bukkit.getPlayer(args[0]) == null) {
				if(!config.isSet("joueurs."+args[0])) {
					player.sendMessage("§cErreur : Joueur introuvable");
					return true;
				}
			}
			else if(Bukkit.getPlayer(args[0]).hasPermission("woy.mute.use")) {
				player.sendMessage("§cErreur : Ce joueur est immunisé");
				return true;
			}
			
			//Pseudo joueur sanctionné
			String nickname = args[0];
			
			//Durée sanction
			String[] long_possible = {"m","h","d",};
			String[] long_types = {"minute","hour","day"};
			String duree_type = null;
			
			for(int i=0;i<3;i++) {
				if(args[1].endsWith(long_possible[i])) {
					duree_type = long_types[i];
				}
			}
			
			if(duree_type == null) {
				player.sendMessage("§cErreur : Type de durée invalide");
				return true;
			}
			
			String duree_string = args[1].substring(0, args[1].length()-1);
			int duree_int = 0;
			
			try {
				duree_int = Integer.parseInt(duree_string);
				
			} catch (NumberFormatException e) {
				player.sendMessage("§cErreur : Durée invalide");
				return true;
			}
			
			if(duree_int == 0) {
				player.sendMessage("§cErreur : Durée nulle");
				return true;
			}
			
			//Raison sanction
			StringBuilder raison = new StringBuilder();
			for(String word : args) {
				if(!word.equals(nickname) && !(word.equals(args[1]))) {
					raison.append(word + " ");
				}
			}
			
			ConfigurationSection joueurSection = config.getConfigurationSection("joueurs."+nickname);
			
			int id_punishment = config.getInt("id_sanctions");
			List<String> sanctionsJoueur = joueurSection.getStringList("sanctions");
			
			//Check si pas déjà ban actuellement
			for(String sanction : sanctionsJoueur) {
				if(sanction.contains("Type : Ban")) {
					int date_fin_index = sanction.indexOf("Date_Fin :");
					CharSequence date_fin = sanction.subSequence(date_fin_index+11, sanction.length());
					
					LocalDateTime today = LocalDateTime.now();
					DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
					
					LocalDateTime date_fin_formated = LocalDateTime.parse(date_fin,formatter);
					
					if(today.isBefore(date_fin_formated)) {
						player.sendMessage("§cErreur : Ce joueur est déjà ban actuellement !");
						player.sendMessage("§cMerci d'unban puis reban ensuite afin de changer la sanction");
						return true;
					}
				}
			}
			
			//Ajout de la sanction dans la liste
			config.set("id_sanctions", id_punishment+1);
			
			String date_debut = LocalDateTime.now().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT));
			
			LocalDateTime date_fin;
			
			if(duree_type.equalsIgnoreCase("minute")) {
				date_fin = LocalDateTime.now().plusMinutes(duree_int);
			}
			else if(duree_type.equalsIgnoreCase("hour")) {
				date_fin = LocalDateTime.now().plusHours(duree_int);
			}
			else {
				date_fin = LocalDateTime.now().plusDays(duree_int);
			}
			
			String date_fin_format = date_fin.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT));
			
			String sanction = "ID : "+id_punishment+" | Type : Ban | Raison : "+ raison + "| Moderateur : "+player.getName()+" | Date_Debut : "+date_debut+" | Date_Fin : "+date_fin_format;
			
			sanctionsJoueur.add(sanction);
			joueurSection.set("sanctions",sanctionsJoueur);
			
			main.saveConfig();
			
			//Message utilisateur
			player.sendMessage("§4[§6WatchOver§4] §3Vous avez banni §e"+nickname+" §3pour la raison §7: §6"+raison);
			player.sendMessage("§4[§6WatchOver§4] §3La sanction expirera le §6"+date_fin_format+" §2[§aID:"+id_punishment+"§2]");
			
			Bukkit.broadcastMessage("§cUn joueur a été banni par un modérateur.");
			
			if(Bukkit.getPlayer(nickname) != null) {
				Player targetPlayer = Bukkit.getPlayer(nickname);
				
				String kick_msg = "§6Vous venez d'être banni par un modérateur pour la raison suivante : §e"+raison+"§7| §6La sanction expirera le : §e"+date_fin_format;
				
				String config_msg = config.getString("global_config.lien_msg_ban");
				
				if(config_msg.length() != 0) {
					kick_msg = kick_msg + "\n§4Pour toute contestation rendez vous sur §c"+config_msg;
				}
				
				targetPlayer.kickPlayer(kick_msg);
			}
			
		}
		
		return true;
	}

}
