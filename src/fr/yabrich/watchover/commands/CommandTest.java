package fr.yabrich.watchover.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import fr.yabrich.watchover.Main;

public class CommandTest implements CommandExecutor {
	
	private Main main;

	public CommandTest(Main main) {
		this.main = main;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {
			
		if(sender instanceof Player) {
			Player player = (Player)sender;
			
			if(cmd.getName().equalsIgnoreCase("test")) {
				player.sendMessage(main.getConfig().getString("messages.test"));
				player.sendMessage(main.getConfig().getString("messages.oui"));
				main.getConfig().set("messages.oui", 9);
				player.sendMessage(main.getConfig().getString("messages.oui"));
				main.getConfig().createSection("bigtest");
				main.getConfig().set("bigtest", "ya");
				
				main.saveConfig();
				
			}
			
		}
		return true;
	}

}
