package fr.yabrich.watchover.commands;

import fr.yabrich.watchover.Main;
import fr.yabrich.watchover.commands.CommandVanish;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

@SuppressWarnings("unused")
public class CommandMod implements CommandExecutor {
	
	public static ArrayList<Player> woactivated = new ArrayList<Player>();
	public static ArrayList<Player> staffwoactivated = new ArrayList<Player>();
	
	private Map<Player, ItemStack[]> inventory_saves = new HashMap<>();
	
	private final Main main;
	
	public CommandMod(Main main) {
		this.main = main;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {
			
			if(sender instanceof Player) {
				Player player = (Player)sender;
				FileConfiguration config = main.getConfig();
				
				if(cmd.getName().equalsIgnoreCase("wo") || cmd.getName().equalsIgnoreCase("watchover")) {
					
					boolean wo_classic = true;
					
					//Check /wo staff + perm
					if(player.hasPermission("woy.staffwo.use") && args.length > 0) {
						if(args[0].toLowerCase().contentEquals("staff")) {
							wo_classic = false;
						}
					}
					
					//Création des items
					
					//Création vanish
					
					ItemStack usedvanish = new ItemStack(Material.AIR);
					
					//Vanish si /wo
					
					if(wo_classic == true){
						ItemStack vanish = new ItemStack(Material.SLIME_BALL);
						ItemMeta custom_vanish = vanish.getItemMeta();
						custom_vanish.setDisplayName("§2§lVanish");
						custom_vanish.addEnchant(Enchantment.LUCK, 1, true);
						custom_vanish.addItemFlags(ItemFlag.HIDE_ENCHANTS);
						
						vanish.setItemMeta(custom_vanish);
						
						usedvanish = vanish;
					}
					
					//StaffVanish si /wo staff
					else{
						
						ItemStack staffvanish = new ItemStack(Material.EMERALD);
						ItemMeta custom_sv = staffvanish.getItemMeta();
						custom_sv.setDisplayName("§2§lStaffVanish");
						custom_sv.addEnchant(Enchantment.LUCK, 1, true);
						custom_sv.addItemFlags(ItemFlag.HIDE_ENCHANTS);
						
						staffvanish.setItemMeta(custom_sv);
						
						usedvanish = staffvanish;
					}
					
					//Création Baton Invsee
					ItemStack invsee = new ItemStack(Material.BLAZE_ROD);
					ItemMeta custom_invsee = invsee.getItemMeta();
					custom_invsee.setDisplayName("§e§lInvsee");
					
					invsee.setItemMeta(custom_invsee);
					
					//Création R-TP
					ItemStack rtp = new ItemStack(Material.NETHER_STAR);
					ItemMeta custom_rtp = rtp.getItemMeta();
					custom_rtp.setDisplayName("§d§lR-TP");
					
					rtp.setItemMeta(custom_rtp);
					
					//Création NV
					ItemStack nv = new ItemStack(Material.GOLDEN_CARROT);
					ItemMeta custom_nv = nv.getItemMeta();
					custom_nv.setDisplayName("§1§lNight Vision");
					
					nv.setItemMeta(custom_nv);
					
					//Création compass
					ItemStack compass = new ItemStack(Material.COMPASS);
					ItemMeta custom_compass = compass.getItemMeta();
					custom_compass.setDisplayName("§6§lCompass");
					
					compass.setItemMeta(custom_compass);
					
					//Création Freeze
					ItemStack freeze = new ItemStack(Material.PACKED_ICE);
					ItemMeta custom_freeze = freeze.getItemMeta();
					custom_freeze.setDisplayName("§9§lFreeze");
					
					freeze.setItemMeta(custom_freeze);
					
					//Création Alert
					ItemStack alert = new ItemStack(Material.PAPER);
					ItemMeta custom_alert = alert.getItemMeta();
					custom_alert.setDisplayName("§c§lAlert");
					
					alert.setItemMeta(custom_alert);
					
					//Activation WO
					
					if(!(woactivated.contains(player)) && !(staffwoactivated.contains(player))) {
						
						//Save inventaire
						ItemStack[] inv = player.getInventory().getContents();
						inventory_saves.put(player, inv);
						
						//Give items
						player.getInventory().clear();
						player.getInventory().setItem(8, nv);
						player.getInventory().setItem(7, freeze);
						player.getInventory().setItem(5, invsee);
						player.getInventory().setItem(3, rtp);
						player.getInventory().setItem(1, alert);
						player.getInventory().setItem(0, usedvanish);
						
						if(config.getBoolean("global_config.compass_wo") == true) {
							player.getInventory().setItem(4, compass);
						}
						
						player.updateInventory();
						
						
						//Activation du /fly d'essentials /vanish de WO
						
						if(wo_classic == true && !(CommandVanish.vanished.contains(player))){
							player.performCommand("watchover:vanish");
						}
						else if(wo_classic == false && !(CommandStaffVanish.staffvanished.contains(player))) {
							player.performCommand("watchover:staffvanish");
						}
		
						if(config.getBoolean("global_config.fly_essentials") == true) {
							if(!(player.getGameMode() == GameMode.CREATIVE)) {
								player.performCommand("essentials:fly");
							}
						}
						
						if(wo_classic == true) {
							woactivated.add(player);
							player.sendMessage("§4[§6WatchOver§4] §3Menu modération §2activé §3!");
						}
						else {
							staffwoactivated.add(player);
							player.sendMessage("§4[§6WatchOver§4] §3Menu administration §2activé §3!");
						}
						
					
					}
					
					//Desactivation du WO
					
					else if(wo_classic == true && staffwoactivated.contains(player)) {
						player.sendMessage("§4[§6WatchOver§4] §c/wo staff déjà activé !");
					}
					
					else if(wo_classic == false && woactivated.contains(player)) {
						player.sendMessage("§4[§6WatchOver§4] §c/wo déjà activé !");
					}
					
					else {
						
						//Clear items
						player.getInventory().clear();
						
						//Regive l'inventaire initiale
						ItemStack[] inv = inventory_saves.get(player);
						inventory_saves.remove(player);
						if (inv != null) {
							player.getInventory().setContents(inv);
						}
						
						//Desactivation du /vanish de WO
						
						if(wo_classic == true && (CommandVanish.vanished.contains(player))){
							player.performCommand("watchover:vanish");
						}
						else if(wo_classic == false && (CommandStaffVanish.staffvanished.contains(player))) {
							player.performCommand("watchover:staffvanish");
						}
						
						if(wo_classic == true) {
							woactivated.remove(player);
							player.sendMessage("§4[§6WatchOver§4] §3Menu modération §4désactivé §3!");
						}
						else {
							staffwoactivated.remove(player);
							player.sendMessage("§4[§6WatchOver§4] §3Menu administration §4désactivé §3!");
						}
					}
					return true;
				}
			}
			return false;
	}
}
