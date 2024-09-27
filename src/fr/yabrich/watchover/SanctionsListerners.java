package fr.yabrich.watchover;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent.Result;
import org.bukkit.event.player.PlayerJoinEvent;

public class SanctionsListerners implements Listener {
	
	private final Main main;
	
	public SanctionsListerners(Main main) {
		this.main = main;
	}

	@EventHandler
	//Création section config si new joueur
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		String nickname = player.getName();
		FileConfiguration config = main.getConfig();
		
		if(!config.isSet("joueurs."+nickname)) {
			config.createSection("joueurs."+nickname);
			
			main.saveConfig();
		}
	}
	
	//Gestion MUTE
	@EventHandler
	public void onPlayerMessage(AsyncPlayerChatEvent event) {
		Player player = event.getPlayer();
		String nickname = player.getName();
		FileConfiguration config = main.getConfig();
		
		ConfigurationSection joueurSection = config.getConfigurationSection("joueurs."+nickname);
		List<String> sanctionsJoueur = joueurSection.getStringList("sanctions");
		
		for(String sanction : sanctionsJoueur) {
			if(sanction.contains("Type : Mute")) {
				int date_fin_index = sanction.indexOf("Date_Fin :");
				CharSequence date_fin = sanction.subSequence(date_fin_index+11, sanction.length());
				
				LocalDateTime today = LocalDateTime.now();
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
				
				LocalDateTime date_fin_formated = LocalDateTime.parse(date_fin,formatter);
				
				if(today.isBefore(date_fin_formated)) {
					event.setCancelled(true);
					player.sendMessage("§cImpossible : vous avez été réduit au silence.");
					player.sendMessage("§cFin de la sanction : §6"+date_fin);
				}
			}
		}
	}
	
	//Gestion BAN
	@EventHandler
	public void onPlayerConnection(AsyncPlayerPreLoginEvent event) {
		String nickname = event.getName();
		FileConfiguration config = main.getConfig();
		
		ConfigurationSection joueurSection = config.getConfigurationSection("joueurs."+nickname);
		List<String> sanctionsJoueur = joueurSection.getStringList("sanctions");
		
		for(String sanction : sanctionsJoueur) {
			if(sanction.contains("Type : Ban")) {
				int date_fin_index = sanction.indexOf("Date_Fin :");
				CharSequence date_fin = sanction.subSequence(date_fin_index+11, sanction.length());
				
				int raison_index = sanction.indexOf("Raison :");
				int barre = sanction.indexOf("|",raison_index)-1;
				CharSequence raison = sanction.subSequence(raison_index+9, barre);
				
				LocalDateTime today = LocalDateTime.now();
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
				
				LocalDateTime date_fin_formated = LocalDateTime.parse(date_fin,formatter);
				
				String kick_msg = "§6Vous êtes banni du serveur pour la raison suivante : §e"+raison+" §7| §6La sanction expirera le : §e"+date_fin;
				
				String config_msg = config.getString("global_config.lien_msg_ban");
				
				if(config_msg.length() != 0) {
					kick_msg = kick_msg + "\n§4Pour toute contestation rendez vous sur §c"+config_msg;
				}
				
				if(today.isBefore(date_fin_formated)) {
					event.disallow(Result.KICK_BANNED,kick_msg);
				}
			}
		}
	}
}
