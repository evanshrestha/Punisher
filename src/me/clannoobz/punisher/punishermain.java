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

/**
 * Punisher version 0.x
 * Copyright (C) 2011  Evan 'Clannoobz' Shrestha <clannoobz@gmail.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Permissions Public License as published by
 * the Free Software Foundation, either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Permissions Public License for more details.
 *
 * You should have received a copy of the GNU Permissions Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

public class PunisherMain extends JavaPlugin{
	public static PermissionHandler Permissions = null;
	private final PunisherBlockListener bListener = new PunisherBlockListener(this);
	private final PunisherPlayerListener pListener = new PunisherPlayerListener(this);
	Logger log = Logger.getLogger("Minecraft");
	Configuration config;
	public String notify = "true";
	public String notifyconsole = "true";
	public String kickonplace = "false";
	public String kickmessage = "You are not allowed to use that";
	public String censoredwords = "";
	public String lightningonplace = "false";
	public String removeblock = "true";
	public String opoverride = "true";
	public String setfireonplace = "false";
	public Integer fireseconds = 3;
	public String killonplace = "false";

	private void defaultConfig() {
		config.setProperty("Notify", notify);
		config.setProperty("Notify-Console", notifyconsole);
		config.setProperty("Kick-On-Place", kickonplace);
		config.setProperty("Kick-Message", kickmessage);
		config.setProperty("Censored-Words", censoredwords);
		config.setProperty("Lightning-On-Place", lightningonplace);
		config.setProperty("Remove-Block", removeblock);
		config.setProperty("Op-Override", opoverride);
		config.setProperty("Set-Fire-On-Place", setfireonplace);
		config.setProperty("Fire-Seconds", fireseconds);
		config.setProperty("Kill-On-Place", killonplace);
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
		notifyconsole = config.getString("Notify-Console", notifyconsole);
		kickonplace = config.getString("Kick-On-Place", kickonplace);
		kickmessage = config.getString("Kick-Message", kickmessage);
		censoredwords = config.getString("Censored-Words", censoredwords);
		lightningonplace = config.getString("Lightning-On-Place", lightningonplace);
		removeblock = config.getString("Remove-Block", removeblock);
		opoverride = config.getString("Op-Override", opoverride);
		setfireonplace = config.getString("Set-Fire-On-Place", setfireonplace);
		fireseconds = config.getInt("Fire-Seconds", fireseconds);
		killonplace = config.getString("Kill-On-Place", killonplace);
		log.info("[Punisher] Config Loaded.");
		}
	private void reloadConfig() {
		try{
		config.load();
		}
		catch(Exception e) { 
			log.info("[Punisher] An error has occured while trying to reload config.");
		}
		notify = config.getString("Notify", notify);
		notifyconsole = config.getString("Notify-Console", notifyconsole);
		kickonplace = config.getString("Kick-On-Place", kickonplace);
		kickmessage = config.getString("Kick-Message", kickmessage);
		censoredwords = config.getString("Censored-Words", censoredwords);
		lightningonplace = config.getString("Lightning-On-Place", lightningonplace);
		removeblock = config.getString("Remove-Block", removeblock);
		opoverride = config.getString("Op-Override", opoverride);
		setfireonplace = config.getString("Set-Fire-On-Place", setfireonplace);
		fireseconds = config.getInt("Fire-Ticks", fireseconds);
		killonplace = config.getString("Kill-On-Place", killonplace);
		log.info("[Punisher] Config Reloaded");
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
    log.info("[Punisher] Permission system not detected, defaulting to OP");
    return;
}

permissionHandler = ((Permissions) permissionsPlugin).getHandler();
log.info("[Punisher] Found and will use plugin "+((Permissions)permissionsPlugin).getDescription().getFullName());
} 

public PermissionHandler permissionHandler;

public boolean check(CommandSender sender, String permNode)
{
	if (sender instanceof Player)
	{
		if (Permissions == null)
		{
				if (opoverride.matches("true") && sender.isOp()) { return true; }
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
				else if ((args.length == 1) && (args[0].equalsIgnoreCase("reload")))
				{
					if (check(sender, "punisher.reload")) {
						reloadConfig();
						if(sender instanceof Player)
						{
						sender.sendMessage(ChatColor.RED + "[Punisher] Config Reloaded");
						}
					}
					 if (!check(sender, "punisher.reload")) {
						sender.sendMessage(ChatColor.RED + "You do not have sufficient permissions.");
					}
				}
				else if ((args.length >= 1) && (args[0].equalsIgnoreCase("lightning")))
				{
					if (check(sender, "punisher.punish.lightning"))
					{
					if ((args.length == 1) && (args[0].equalsIgnoreCase("lightning")))
					{
						sender.sendMessage(ChatColor.RED + "Syntax is /punisher lightning (player)");
					}
					else if ((args.length == 2) && (args[0].equalsIgnoreCase("lightning")))
					{
						if (getServer().matchPlayer(args[1]).isEmpty())
						{
							sender.sendMessage(ChatColor.RED + " " + args[1] + " doesn't seem to be online");
							return false;
						}
						for (Player player : getServer().matchPlayer(args[1]))
						{
							try {
								if (!check(player, "punisher.punish.lightningexempt"))
								{
							player.getWorld().strikeLightning(player.getLocation());
							sender.sendMessage(ChatColor.RED + "You have punished " + player.getDisplayName() + " with a shocking surprise.");
								}
								else if (check(player, "punisher.punish.lightningexempt"))
								{
									sender.sendMessage(ChatColor.RED + "You can not strike this person with lightning.");
								}
								}catch(Exception e) {
								sender.sendMessage(ChatColor.RED + "An error has occured.");
							}
						}
					}
					}
					else if (!check(sender, "punisher.punish.lightning"))
					{
						sender.sendMessage(ChatColor.RED + "You do not have sufficient permissions.");
					}
					
				}
				else if ((args.length >= 1) && (args[0].equalsIgnoreCase("burn")))
				{
					if (check(sender, "punisher.punish.burn"))
					{
						if ((args.length == 1) && (args[0].equalsIgnoreCase("burn")))
						{
							sender.sendMessage(ChatColor.RED + "Syntax is /punisher burn (player) (seconds)");
						}
						if ((args.length == 2) && (args[0].equalsIgnoreCase("burn")))
						{
							sender.sendMessage(ChatColor.RED + "Syntax is /punisher burn (player) (seconds)");
						}
						if ((args.length == 3) && (args[0].equalsIgnoreCase("burn")))
						{
							if (getServer().matchPlayer(args[1]).isEmpty())
							{
								sender.sendMessage(ChatColor.RED + " " + args[1] + " doesn't seem to be online");
								return false;
							}
							for (Player player : getServer().matchPlayer(args[1]))
							{
								try {
									if (!check(player, "punisher.punish.fireexempt"))
									{
								player.setFireTicks(Integer.parseInt(args[2])*20);
								sender.sendMessage(ChatColor.RED + "You have punished " + player.getDisplayName() + " with a warming welcome.");
									}
									else if (check(player, "punisher.punishment.fireexempt"))
									{
										sender.sendMessage(ChatColor.RED + "You can not burn this person.");
									}
									}catch(Exception e) {
									sender.sendMessage(ChatColor.RED + "An error has occured.");
								}
							}
						}
					}
				}
				else if ((args.length >= 1) && (args[0].equalsIgnoreCase("kill")))
				{
					if (check(sender, "punisher.punish.kill"))
					{
					if ((args.length == 1) && (args[0].equalsIgnoreCase("kill")))
					{
						sender.sendMessage(ChatColor.RED + "Syntax is /punisher kill (player)");
					}
					else if ((args.length == 2) && (args[0].equalsIgnoreCase("kill")))
					{
						if (getServer().matchPlayer(args[1]).isEmpty())
						{
							sender.sendMessage(ChatColor.RED + " " + args[1] + " doesn't seem to be online");
							return false;
						}
						for (Player player : getServer().matchPlayer(args[1]))
						{
							try {
								if (!check(player, "punisher.punish.killexempt"))
								{
							player.getWorld().strikeLightning(player.getLocation());
							sender.sendMessage(ChatColor.RED + "You have punished " + player.getDisplayName() + " with a glass of redrum.");
								}
								else if (check(player, "punisher.punish.killexempt"))
								{
									sender.sendMessage(ChatColor.RED + "You can not kill this person.");
								}
								}catch(Exception e) {
								sender.sendMessage(ChatColor.RED + "An error has occured.");
							}
				}
					}
					}
				}
				else
					sender.sendMessage(ChatColor.RED + "[Punisher] Unknown command");
				}
		return false;
	}

}