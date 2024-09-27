package fr.yabrich.watchover.commands;

import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import fr.yabrich.watchover.Main;

public class CommandHist implements CommandExecutor {
	
	private final Main main;

	public CommandHist(Main main) {
		this.main = main;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {
		if(sender instanceof Player) {
			Player player = (Player)sender;
			ConfigurationSection config = main.getConfig();
			
			if(args.length == 0) {
				player.sendMessage("§cErreur de syntaxe (Argument(s) manquant(s))");
				player.sendMessage("§cUsage : /hist [joueur] (nb_sanction)");
				player.sendMessage("§cUsage : /history [joueur] (nb_sanction)");
				
				return true;
			}
			
			if(!config.isSet("joueurs."+args[0])) {
				player.sendMessage("§cErreur : Joueur introuvable");
				
				return true;
			}
			
			String nickname = args[0];
			int nb_sanction_printed = 10;
			
			if(args.length == 2) {
				try {
					nb_sanction_printed = Integer.parseInt(args[1]);
				} catch (NumberFormatException e) {
					player.sendMessage("§cErreur : Nombre de sanction invalide");
					return true;
				}
			}
			
			ConfigurationSection joueurSection = config.getConfigurationSection("joueurs."+nickname);
			List<String> sanctionsJoueur = joueurSection.getStringList("sanctions");
			int barre;
			
			if(sanctionsJoueur.isEmpty()) {
				player.sendMessage("§4[§6WatchOver§4] §3Ce joueur n'a aucune sanctions !");
				return true;
			}
			
			if(sanctionsJoueur.size() < nb_sanction_printed) {
				nb_sanction_printed = sanctionsJoueur.size();
			}
			
			int nb_sanction = sanctionsJoueur.size();
			nb_sanction_printed = nb_sanction-nb_sanction_printed;
			String sanction;
			
			player.sendMessage("§6---- §4Historique de §e§l"+nickname+" §6----");
			
			for(int i=nb_sanction-1;i>=nb_sanction_printed;i--) {
				sanction = sanctionsJoueur.get(i);
				
				barre = sanction.indexOf("|", 0)-1;
				CharSequence id = sanction.subSequence(5, barre); //Id sanction
				
				int type_index = sanction.indexOf("Type :");
				barre = sanction.indexOf("|", type_index)-1;
				CharSequence type = sanction.subSequence(type_index+7, barre);
				
				int raison_index = sanction.indexOf("Raison :");
				barre = sanction.indexOf("|",raison_index)-1;
				CharSequence raison = sanction.subSequence(raison_index+9, barre); //Raison sanction
				
				int moderateur_index = sanction.indexOf("Moderateur :");
				barre = sanction.indexOf("|",moderateur_index)-1;
				CharSequence moderateur = sanction.subSequence(moderateur_index+13, barre);
				
				int date_debut_index = sanction.indexOf("Date_Debut :");
				barre = sanction.indexOf("|",date_debut_index)-1;
				CharSequence date_debut = sanction.subSequence(date_debut_index+13, barre); //Date du début de la sanction
				
				int date_fin_index = sanction.indexOf("Date_Fin :");
				CharSequence date_fin = sanction.subSequence(date_fin_index+11, sanction.length()); //Date d'expiration de sanction
				
				String hist = "§7- §2[§aID:"+id+"§2] §6Type : §e"+type+" §6Raison : §e"+raison+" §6Modérateur : §e"+moderateur+" §6Date de sanction : §e"+date_debut+" §6Date d'expriation : §e"+date_fin;
				
				player.sendMessage(hist);
			}
			
			player.sendMessage("§6---- §4Nombre de sanctions total : §c"+nb_sanction+" §6----");
			
		}
		return true;
	}

}
