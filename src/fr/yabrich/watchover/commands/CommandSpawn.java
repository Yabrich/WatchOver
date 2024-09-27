package fr.yabrich.watchover.commands;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandSpawn implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {
		
		if(sender instanceof Player) {
			Player player = (Player)sender;
			Location spawn = new Location(player.getWorld(), 250, 64, 292);
			player.teleport(spawn);
			player.sendMessage("§6Téléportation en cours...");
		}

		return false;
	}

}
