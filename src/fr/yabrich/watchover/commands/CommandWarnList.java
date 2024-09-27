package fr.yabrich.watchover.commands;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import fr.yabrich.watchover.Main;

public class CommandWarnList implements CommandExecutor {
	
	private Main main;

	public CommandWarnList(Main main) {
		this.main = main;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {
		if(sender instanceof Player) {
			Player player = (Player)sender;
			FileConfiguration config = main.getConfig();
			String nickname;
			
			if(player.hasPermission("woy.warnlist.others") && (args.length != 0)) {
				if(!config.isSet("joueurs."+args[0])) {
					player.sendMessage("§cErreur : Joueur introuvable");
					return true;
				}
				else {
					nickname = args[0];
				}
			}
			else {
				nickname = player.getName();
			}
			
			ConfigurationSection joueurSection = config.getConfigurationSection("joueurs."+nickname);
			List<String> sanctionsJoueur = joueurSection.getStringList("sanctions");
			int barre;
			
			if(sanctionsJoueur.isEmpty()) {
				player.sendMessage("§4[§6WatchOver§4] §3Ce joueur n'a aucune sanctions !");
				return true;
			}
			
			player.sendMessage("§6Liste des avertissements de §e§n"+nickname+"§r §6:");
			
			for(String sanction : sanctionsJoueur) {
				if(sanction.contains("Type : Warn")) {
					
					barre = sanction.indexOf("|", 0)-1;
					CharSequence id = sanction.subSequence(5, barre); //Id sanction
					
					int raison_index = sanction.indexOf("Raison :");
					barre = sanction.indexOf("|",raison_index)-1;
					CharSequence raison = sanction.subSequence(raison_index+9, barre); //Raison sanction
					
					int date_debut_index = sanction.indexOf("Date_Debut :");
					barre = sanction.indexOf("|",date_debut_index)-1;
					CharSequence date_debut = sanction.subSequence(date_debut_index+13, barre); //Date du début de la sanction
					
					int date_fin_index = sanction.indexOf("Date_Fin :");
					CharSequence date_fin = sanction.subSequence(date_fin_index+11, sanction.length()); //Date d'expiration de sanction
					
					LocalDateTime today = LocalDateTime.now();
					DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
					LocalDateTime date_fin_formated = LocalDateTime.parse(date_fin, formatter); //Conversion de la date de fin en LocalDateTime
					
					String warnlist;
					
					warnlist = "§7- §2[§aID:"+id+"§2] §6Raison : §e"+raison+" §6Date de sanction : §e"+date_debut+" §6Date d'expriation : §e"+date_fin;
					
					if(date_fin_formated.isBefore(today)) {
						String expire = " §8[§7EXPIRE§8]";
						warnlist = warnlist+expire; //Rajout d'une mention "expiré" pour les sanctions expirées
					}

					player.sendMessage(warnlist);
					
				}
			}
			
		}
		
		return true;
	}

}
