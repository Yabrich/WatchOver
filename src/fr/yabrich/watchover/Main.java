package fr.yabrich.watchover;

import org.bukkit.plugin.java.JavaPlugin;

import fr.yabrich.watchover.commands.CommandAlert;
import fr.yabrich.watchover.commands.CommandAns;
import fr.yabrich.watchover.commands.CommandBan;
import fr.yabrich.watchover.commands.CommandChatClear;
import fr.yabrich.watchover.commands.CommandChatMod;
import fr.yabrich.watchover.commands.CommandFreeze;
import fr.yabrich.watchover.commands.CommandHelpme;
import fr.yabrich.watchover.commands.CommandHist;
import fr.yabrich.watchover.commands.CommandId;
import fr.yabrich.watchover.commands.CommandInvsee;
import fr.yabrich.watchover.commands.CommandMod;
import fr.yabrich.watchover.commands.CommandMsgCo;
import fr.yabrich.watchover.commands.CommandMute;
import fr.yabrich.watchover.commands.CommandNv;
import fr.yabrich.watchover.commands.CommandPlayerXYZ;
import fr.yabrich.watchover.commands.CommandReport;
import fr.yabrich.watchover.commands.CommandS;
import fr.yabrich.watchover.commands.CommandSpawn;
import fr.yabrich.watchover.commands.CommandStaffChat;
import fr.yabrich.watchover.commands.CommandStaffVanish;
import fr.yabrich.watchover.commands.CommandTest;
import fr.yabrich.watchover.commands.CommandUnBan;
import fr.yabrich.watchover.commands.CommandUnMute;
import fr.yabrich.watchover.commands.CommandUnWarn;
import fr.yabrich.watchover.commands.CommandVanish;
import fr.yabrich.watchover.commands.CommandWarn;
import fr.yabrich.watchover.commands.CommandWarnList;

public class Main extends JavaPlugin {
	
	@Override
	public void onEnable() {
		System.out.println("[WatchOver] Loading config...");
		saveDefaultConfig();
		
		System.out.println("[WatchOver] Initializing commands...");
		getCommand("test").setExecutor(new CommandTest(this));
		getCommand("spawn").setExecutor(new CommandSpawn());
		getCommand("watchover").setExecutor(new CommandMod(this));
		getCommand("spycmd").setExecutor(new CommandMod(this));
		getCommand("invsee").setExecutor(new CommandInvsee());
		getCommand("s").setExecutor(new CommandS());
		getCommand("vanish").setExecutor(new CommandVanish());
		getCommand("staffvanish").setExecutor(new CommandStaffVanish());
		getCommand("id").setExecutor(new CommandId());
		getCommand("msgco").setExecutor(new CommandMsgCo());
		getCommand("nv").setExecutor(new CommandNv());
		getCommand("freeze").setExecutor(new CommandFreeze());
		getCommand("alert").setExecutor(new CommandAlert());
		getCommand("playerxyz").setExecutor(new CommandPlayerXYZ());
		getCommand("warn").setExecutor(new CommandWarn(this));
		getCommand("warnlist").setExecutor(new CommandWarnList(this));
		getCommand("unwarn").setExecutor(new CommandUnWarn(this));
		getCommand("helpme").setExecutor(new CommandHelpme());
		getCommand("ans").setExecutor(new CommandAns());
		getCommand("report").setExecutor(new CommandReport());
		getCommand("chatclear").setExecutor(new CommandChatClear());
		getCommand("chat").setExecutor(new CommandChatMod());
		getCommand("mute").setExecutor(new CommandMute(this));
		getCommand("unmute").setExecutor(new CommandUnMute(this));
		getCommand("ban").setExecutor(new CommandBan(this));
		getCommand("unban").setExecutor(new CommandUnBan(this));
		getCommand("staffchat").setExecutor(new CommandStaffChat());
		getCommand("hist").setExecutor(new CommandHist(this));
		
		System.out.println("[WatchOver] Initializing listeners...");
		getServer().getPluginManager().registerEvents(new WatchOverListerners(), this);
		getServer().getPluginManager().registerEvents(new SpyCmdListerners(), this);
		getServer().getPluginManager().registerEvents(new RTPListerners(), this);
		getServer().getPluginManager().registerEvents(new MsgCoListerners(), this);
		getServer().getPluginManager().registerEvents(new NvListerners(), this);
		getServer().getPluginManager().registerEvents(new FreezeListerners(), this);
		getServer().getPluginManager().registerEvents(new AlertListerners(), this);
		getServer().getPluginManager().registerEvents(new InvseeListerners(), this);
		getServer().getPluginManager().registerEvents(new PickupItemListerners(), this);
		getServer().getPluginManager().registerEvents(new SanctionsListerners(this), this);
		getServer().getPluginManager().registerEvents(new ChatModListerners(), this);
		getServer().getPluginManager().registerEvents(new StaffChatListerners(), this);
		
		System.out.println("[WatchOver] Plugin On !");
	}
	
	@Override
	public void onDisable() {
		
		System.out.println("[WatchOver] Plugin Off !");
	}

}
