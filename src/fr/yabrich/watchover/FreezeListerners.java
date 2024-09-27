package fr.yabrich.watchover;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import fr.yabrich.watchover.commands.CommandFreeze;

public class FreezeListerners implements Listener {
	@EventHandler
	public void onMove(PlayerMoveEvent event) {
		Player player = event.getPlayer();
		Location from = event.getFrom();
		
		if(CommandFreeze.freezedplayer.contains(player)) {
			player.teleport(from);
			player.sendMessage("§cDéplacement impossible, vous êtes freeze !");
		}
	}
	
	@EventHandler
	public void onDisconnect(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		Player wo = event.getPlayer();
		
		for(Player staff : Bukkit.getOnlinePlayers()) {
			if(staff.hasPermission("woy.ban.use")) {
				wo = staff;
			}
		}
		
		if(CommandFreeze.freezedplayer.contains(player)) {
			wo.performCommand("watchover:ban "+player.getName()+" 9999d Déconnexion en étant freeze (BAN AUTO)");
		}
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onInteract(PlayerInteractEntityEvent event) {
		
		if(event.getHand().equals(EquipmentSlot.HAND)) {
			Player player = event.getPlayer();
			Entity entity = event.getRightClicked();
			ItemStack it = player.getItemInHand();
			
			if(player.hasPermission("woy.freeze.use")) {
				if(entity instanceof Player) {
					Player targetPlayer = (Player)entity;
					
					if(it.getType() == Material.PACKED_ICE && it.getItemMeta().getDisplayName().equalsIgnoreCase("§9§lFreeze")) {
						player.performCommand("watchover:freeze "+targetPlayer.getName());
					}
				}
			
			}
			
			else if(it.getType() == Material.PACKED_ICE && it.getItemMeta().getDisplayName().equalsIgnoreCase("§9§lFreeze")) {
				WatchOverListerners.suppressItem(player, it);
			}
		}	
	}

}
