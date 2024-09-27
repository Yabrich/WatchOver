package fr.yabrich.watchover;

import fr.yabrich.watchover.WatchOverListerners;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

@SuppressWarnings("unused")
public class AlertListerners implements Listener {
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onEntityInteract(PlayerInteractEntityEvent event) {
		Player player = event.getPlayer();
		Entity entity = event.getRightClicked();
		ItemStack it = player.getItemInHand();
		
		if(event.getHand().equals(EquipmentSlot.HAND)){
			if(player.hasPermission("woy.alert.use")) {
				if(entity instanceof Player) {
					Player targetPlayer = (Player)entity;
					
					if(it.getTypeId() == 339 && it.getItemMeta().getDisplayName().equalsIgnoreCase("§c§lAlert")) {
						player.performCommand("watchover:alert "+targetPlayer.getName());
					}
				}
			}
			else if(it.getTypeId() == 339 && it.getItemMeta().getDisplayName().equalsIgnoreCase("§c§lAlert")) {
				WatchOverListerners.suppressItem(player, it);
			}
		}
	}
}
