package fr.yabrich.watchover.commands;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import fr.yabrich.watchover.Main;

public class CommandUnWarn implements CommandExecutor {
	
	private final Main main;

	public CommandUnWarn(Main main) {
		this.main = main;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {
		if(sender instanceof Player) {
			Player player = (Player)sender;
			FileConfiguration config = main.getConfig();
			
			if(args.length != 2) {
				player.sendMessage("§cErreur de syntaxe (Argument(s) manquant(s))");
				player.sendMessage("§cUsage : /unwarn [joueur] [id]");
				
				return true;
			}
			
			if(Bukkit.getPlayer(args[0]) == null) {
				if(!config.isSet("joueurs."+args[0])) {
					player.sendMessage("§cErreur : Joueur introuvable");
					return true;
				}
			}
			
			String nickname = args[0];
			String id_searched = args[1];
			
			ConfigurationSection joueurSection = config.getConfigurationSection("joueurs."+nickname);
			List<String> sanctionsJoueur = joueurSection.getStringList("sanctions");
			
			for(String sanction : sanctionsJoueur) {
				if(sanction.contains("Type : Warn")) {
					int barre = sanction.indexOf("|")-1;
					CharSequence id = sanction.subSequence(5, barre);
					
					if(id.toString().equals(id_searched)) {
						sanctionsJoueur.remove(sanction);
						joueurSection.set("sanctions", sanctionsJoueur);
						player.sendMessage("§4[§6WatchOver§4] §3L'avertissement §2[§aID:"+id+"§2] §3a bien été retiré à §e"+nickname);
						main.saveConfig();
						return true;
					}
				}
			}
			
			player.sendMessage("§cErreur : ID introuvable");
		}
		
		return true;
	}

}
