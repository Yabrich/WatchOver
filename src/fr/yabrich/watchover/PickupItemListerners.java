package fr.yabrich.watchover;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;

import fr.yabrich.watchover.commands.CommandMod;

@SuppressWarnings("deprecation")
public class PickupItemListerners implements Listener {
	
	@EventHandler
	public void onItemPickup(PlayerPickupItemEvent event) {
		Player player = event.getPlayer();
		
		if(CommandMod.woactivated.contains(player) || CommandMod.staffwoactivated.contains(player)) {
			event.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onEntityDamage(EntityDamageByEntityEvent event) {
		Entity damager = event.getDamager();
		
		if(damager instanceof Player) {
			Player player = (Player)damager;
			
			if(CommandMod.woactivated.contains(player) || CommandMod.staffwoactivated.contains(player)) {
				event.setCancelled(true);
			}
		}
	}
}
