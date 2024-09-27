package fr.yabrich.watchover;

import fr.yabrich.watchover.commands.CommandStaffVanish;
import fr.yabrich.watchover.commands.CommandVanish;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class WatchOverListerners implements Listener {
	
	//Fonction pour simuler l'exécution d'une cmd
		public static void simulateCommand(Player player,String cmd) {
			String simulatedCommand = cmd;
			Bukkit.dispatchCommand(player, simulatedCommand);
			
			PlayerCommandPreprocessEvent event = new PlayerCommandPreprocessEvent(player, simulatedCommand);
	        Bukkit.getServer().getPluginManager().callEvent(event);
		}
	
	//Fonction pour supprimer un item interdit
		public static void suppressItem(Player player, ItemStack item) {
			player.getInventory().removeItem(item);
			player.sendMessage("§4Objet interdit ! L'item vous a été retiré.");
		}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onInteract(PlayerInteractEvent event) {
		
		//SlimeBall VANISH
		
		Player player = event.getPlayer();
		Action action = event.getAction();
		ItemStack it = event.getItem();
		
		if(it == null) return;
		
		String itemname = it.getItemMeta().getDisplayName();
		
		
		if(player.hasPermission("woy.vanish.use") && it.getTypeId() == 341 && (itemname.equalsIgnoreCase("§4§lVanish") || itemname.equalsIgnoreCase("§2§lVanish")) && (action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK)) {
			player.performCommand("watchover:vanish");
			ItemMeta itmeta = it.getItemMeta();
			
			if(!CommandVanish.vanished.contains(player)) {
				itmeta.setDisplayName("§4§lVanish");
				itmeta.removeEnchant(Enchantment.LUCK);
			}
			else {
				itmeta.setDisplayName("§2§lVanish");
				itmeta.addEnchant(Enchantment.LUCK, 1, true);
			}
			
			it.setItemMeta(itmeta);
			
			
			player.updateInventory();
			
		}
		
		//Emerald STAFFVANISH
		else if(player.hasPermission("woy.staffvanish.use") && it.getTypeId() == 388 && (itemname.equalsIgnoreCase("§2§lStaffVanish") || itemname.equalsIgnoreCase("§4§lStaffVanish")) && (action == Action.RIGHT_CLICK_AIR || action == Action.RIGHT_CLICK_BLOCK)) {
			player.performCommand("watchover:sv");
			ItemMeta itmeta = it.getItemMeta();
			
			if(!CommandStaffVanish.staffvanished.contains(player)) {
				itmeta.setDisplayName("§4§lStaffVanish");
				itmeta.removeEnchant(Enchantment.LUCK);
			}
			else {
				itmeta.setDisplayName("§2§lStaffVanish");
				itmeta.addEnchant(Enchantment.LUCK,1,true);
			}
			
			it.setItemMeta(itmeta);
			
			player.updateInventory();
		}
		
		else if(it.getTypeId() == 341 && (itemname.equalsIgnoreCase("§4§lVanish") || itemname.equalsIgnoreCase("§2§lVanish")) || it.getTypeId() == 388 && (itemname.equalsIgnoreCase("§4§lStaffVanish") || itemname.equalsIgnoreCase("§2§lStaffVanish"))) {
			suppressItem(player, it);
		}
	
	}
	
	//Vanish_Exeption
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player player_connected = event.getPlayer();
		
		if(!player_connected.hasPermission("woy.vanish.seeothers")) {
			for(Player pl_vanish : CommandVanish.vanished) {
				player_connected.hidePlayer(pl_vanish);
			}
		}
		
		if(!player_connected.hasPermission("woy.staffvanish.seeothers")) {
			for(Player pl_staffv : CommandStaffVanish.staffvanished) {
				player_connected.hidePlayer(pl_staffv);
			}
		}
		
	}
	
	@EventHandler
	public void onPlayerLeave(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		CommandVanish.vanished.remove(player);
		CommandStaffVanish.staffvanished.remove(player);
	}
	
	
	//Clique droit pseudo
	@SuppressWarnings("deprecation")
	@EventHandler
	
	public void onInteract(PlayerInteractEntityEvent event) {
		Player player = event.getPlayer();
		Entity entity = event.getRightClicked();
		ItemStack it = player.getItemInHand();
		
		if(event.getHand().equals(EquipmentSlot.HAND)){
			if(entity instanceof Player) {
				Player targetPlayer = (Player)entity;
				
				if(player.hasPermission("woy.nickname.use")) {
					if(it.getType() == Material.AIR) {
						player.sendMessage("§9Pseudo du joueur : §b" + targetPlayer.getDisplayName());
					}
				}
			}	
		}
	}
}
	
