package me.clannoobz.punisher;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import java.util.logging.Logger;
import org.bukkit.util.config.Configuration;

import com.nijiko.permissions.PermissionHandler;
import com.nijikokun.bukkit.Permissions.Permissions;

import java.io.File;




public class punishermain extends JavaPlugin{
	public static PermissionHandler Permissions = null;
	private final punisherblocklistener bListener = new punisherblocklistener(this);
	private final punisherplayerlistener pListener = new punisherplayerlistener(this);
	Logger log = Logger.getLogger("Minecraft");
	Configuration config;
	public String notify = "true";
	public Boolean kickonplace = true;
	public String kickmessage = "You are not allowed to use that";
	public String censoredwords = "fuck";
	

	private void defaultConfig() {
		config.setProperty("Notify", notify);
		config.setProperty("Kick-On-Place", kickonplace);
		config.setProperty("Kick-Message", kickmessage);
		config.setProperty("Censored-Words", censoredwords);
		config.save();
		log.info("[Punisher] Config created.");
	}
	private void loadConfig() {
		try{
		config.load();
		}
		catch(Exception e) { 
			log.info("[Punisher] An error has occured while trying to load config.");
		}
		notify = config.getString("Notify", notify);
		kickonplace = config.getBoolean("Kick-On-Place", true);
		kickmessage = config.getString("Kick-Message", kickmessage);
		censoredwords = config.getString("Censored-Words", censoredwords);
		log.info("[Punisher] Config Loaded.");
		}
	
@Override
public void onDisable() {
	log.info("[Punisher] Punisher version " + getDescription().getVersion() + " has been disabled!");
}
@Override
public void onEnable() {
	PluginManager pm = getServer().getPluginManager();
	log.info("[Punisher] Punisher Version " + getDescription().getVersion() + " has been enabled!");
	pm.registerEvent(Event.Type.BLOCK_PLACE, bListener, Event.Priority.Normal, this);
	pm.registerEvent(Event.Type.PLAYER_CHAT, pListener, Event.Priority.Normal, this);
	config = getConfiguration();
	if (!(new File(getDataFolder(), "config.yml")).exists()) {
		defaultConfig();
	}
		loadConfig();
		setupPermissions();
	}
private void setupPermissions() { if (permissionHandler != null) { return; }

Plugin permissionsPlugin = this.getServer().getPluginManager().getPlugin("Permissions");

if (permissionsPlugin == null) {
    log.info("Permission system not detected, defaulting to OP");
    return;
}

permissionHandler = ((Permissions) permissionsPlugin).getHandler();
log.info("Found and will use plugin "+((Permissions)permissionsPlugin).getDescription().getFullName());
} 

public PermissionHandler permissionHandler;

public boolean check(CommandSender sender, String permNode)
{
	if (sender instanceof Player)
	{
		if (Permissions == null)
		{
			if (sender.isOp()) { return true; }
			return false;
		}
		else
		{
			Player player = (Player) sender;
			return Permissions.has(player, permNode);
		}
	}
	else if (sender instanceof ConsoleCommandSender)
	{
		return true;
	}
	else
	{
		return false;
	}
}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (command.getName().equalsIgnoreCase("punisher")){
				if ((args.length == 0)) {
				sender.sendMessage(ChatColor.RED + "[Punisher] Running version " + getDescription().getVersion());
				return false;
				}
				else
				if ((args.length == 1) && (args[0].equalsIgnoreCase("reload")))
				{
					if (check(sender, "punisher.reload")) {
						loadConfig();
						sender.sendMessage(ChatColor.RED + "[Punisher] Config Reloaded");
						return false;
					}
					if (!check(sender, "punisher.reload")) {
						sender.sendMessage(ChatColor.RED + "You do not have sufficient permissions.");
						return false;
					}
				}
				else
					sender.sendMessage(ChatColor.RED + "[Punisher] Unknown command");
				}
		return false;
	}

}