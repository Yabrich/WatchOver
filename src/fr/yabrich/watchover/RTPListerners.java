package fr.yabrich.watchover;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class RTPListerners implements Listener {
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onInteract(PlayerInteractEvent event) {
		
		//RTP
		
		Player player = event.getPlayer();
		Action action = event.getAction();
		ItemStack it = event.getItem();
		
		if(it == null) return;
		
		if(player.hasPermission("woy.rtp.use") && it.getTypeId() == 399 && it.getItemMeta().getDisplayName().equalsIgnoreCase("§d§lR-TP") && (action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK)) {
			List<Player> onlinePlayers = new ArrayList<>();
			
			for(Player players : Bukkit.getOnlinePlayers()) {
				if(players != player && !players.isOp() && !players.hasPermission("woy.rtp.bypass")) {
					onlinePlayers.add(players);
				}
			}
			
			if(!onlinePlayers.isEmpty()) {
				Random random = new Random();
				int index = random.nextInt(onlinePlayers.size());
				
				Player player_rand = onlinePlayers.get(index);
				Location location = player_rand.getLocation();
				
				Location player_cos = new Location(player_rand.getWorld(),location.getX(),location.getY(),location.getZ());
				
				player.teleport(player_cos);
				player.sendMessage("§4[§6R-TP§4] §6Téléportation vers §e"+player_rand.getDisplayName()+" §6en cours...");
			}
			else {
				player.sendMessage("§4[§6R-TP§4] §cTéléportation impossible : Vous êtes le seul connecté !");
			}
			
		}
		else if(it.getTypeId() == 399 && it.getItemMeta().getDisplayName().equalsIgnoreCase("§d§lR-TP")) {
			WatchOverListerners.suppressItem(player, it);
		}
	}
}
