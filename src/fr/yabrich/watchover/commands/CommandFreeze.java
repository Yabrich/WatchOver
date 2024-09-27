package fr.yabrich.watchover.commands;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class CommandFreeze implements CommandExecutor {
	
	public static ArrayList<Player> freezedplayer = new ArrayList<Player>();
	private Map<Player, ItemStack> helmet_saves = new HashMap<>();

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {
		if(sender instanceof Player) {
			Player player = (Player)sender;
			
			if(args.length == 0) {
				player.sendMessage("§cErreur de syntaxe (Argument(s) manquant(s))");
				player.sendMessage("§cUsage : /freeze [joueur]");
				
				return true;
			}
			
			if(Bukkit.getPlayer(args[0]) == null) {
				player.sendMessage("§cErreur : Joueur introuvable");
				
				return true;
			}
			
			Player targetPlayer = Bukkit.getPlayer(args[0]);
			
			if(targetPlayer.isOp() || targetPlayer.hasPermission("woy.freeze.bypass") || targetPlayer == player) {
				player.sendMessage("§cErreur : Ce joueur est immunisé !");
				
				return true;
			}
			
			if(freezedplayer.contains(targetPlayer)) {
				ItemStack helmet = helmet_saves.get(targetPlayer);
				targetPlayer.getInventory().setHelmet(helmet);
				freezedplayer.remove(targetPlayer);
				
				player.sendMessage("§6Le joueur §e"+targetPlayer.getDisplayName()+" §6a bien été défreeze !");
				targetPlayer.sendMessage("§6Vous avez été défreeze par un modérateur.");
			}
			else {
				
				ItemStack helmet = targetPlayer.getInventory().getHelmet();
				helmet_saves.put(targetPlayer, helmet);
				freezedplayer.add(targetPlayer);
				
				ItemStack ice = new ItemStack(Material.PACKED_ICE);
				targetPlayer.getInventory().setHelmet(ice);
				
				player.sendMessage("§6Le joueur §e"+targetPlayer.getDisplayName()+" §6a bien été freeze !");
				targetPlayer.sendMessage("§6Vous avez été freeze par un modérateur !");
			}
		}
		return true;
	}

}
