package fr.yabrich.watchover;

import fr.yabrich.watchover.WatchOverListerners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

@SuppressWarnings("unused")
public class NvListerners implements Listener {
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onInteract(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		Action action = event.getAction();
		ItemStack it = event.getItem();
		
		if(it == null) {
			return;
		}
		
		if(player.hasPermission("woy.nv.use")) {
			if(it.getTypeId() == 396 && it.getItemMeta().getDisplayName().equalsIgnoreCase("§1§lNight Vision") && (action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK)){
				player.performCommand("watchover:nv");
				event.setCancelled(true);
			}
		}
		
		else if(it.getTypeId() == 396 && it.getItemMeta().getDisplayName().equalsIgnoreCase("§1§lNight Vision")) {
			WatchOverListerners.suppressItem(player, it);
			player.updateInventory();
		}
		
	}
}
