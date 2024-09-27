package fr.yabrich.watchover;

import fr.yabrich.watchover.WatchOverListerners;

import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

@SuppressWarnings("unused")
public class InvseeListerners implements Listener {
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onEntityInteract(PlayerInteractEntityEvent event) {
		Player player = event.getPlayer();
		Entity entity = event.getRightClicked();
		ItemStack it = player.getItemInHand();
		
		if(event.getHand().equals(EquipmentSlot.HAND)) {
			if(entity instanceof Player) {
				Player targetPlayer = (Player)entity;
				
				if(it.getType() == Material.BLAZE_ROD && it.getItemMeta().getDisplayName().equalsIgnoreCase("§e§lInvsee")) {
					if(player.hasPermission("woy.invsee.use")) {
						player.performCommand("watchover:invsee "+targetPlayer.getName());
			        }
					else {
						WatchOverListerners.suppressItem(player, it);
					}
				}
				
			}
		}
	}

}
