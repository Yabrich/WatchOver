package fr.yabrich.watchover.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.*;

public class CommandNv implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {
		
		if(sender instanceof Player) {
			Player player = (Player)sender;
			
			PotionEffectType nv = PotionEffectType.NIGHT_VISION;
			
			if(player.getPotionEffect(nv) == null) {
				player.addPotionEffect(new PotionEffect(nv, Integer.MAX_VALUE, 0));
				player.sendMessage("§4[§6WatchOver§4] §3Vision Nocturne §2activée §3!");
			}
			else {
				player.removePotionEffect(nv);
				player.sendMessage("§4[§6WatchOver§4] §3Vision Nocturne §4désactivée §3!");
			}
			
		}
		return true;
	}

}
