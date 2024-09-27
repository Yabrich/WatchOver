package fr.yabrich.watchover.commands;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;

@SuppressWarnings("unused")
public class CommandId implements CommandExecutor {

	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {
		
		if(sender instanceof Player) {	
			Player player = (Player)sender;
		ItemStack item_hand = player.getItemInHand();
		
		MaterialData item_type = item_hand.getData();
		int item_id = item_hand.getTypeId();
		int item_byte = item_hand.getData().getData();
		
		String message = "§4[§6ID§4] §e"+item_type+" §7: §6§l"+item_id;
		
		if(item_byte != 0) {
			message = message+":"+item_byte;
		}
		
		player.sendMessage(message);
		
		}
		
	return true;

	}
}
