package me.clannoobz.punisher;

import java.util.logging.Logger;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockListener;
import org.bukkit.event.block.BlockPlaceEvent;
public class PunisherBlockListener extends BlockListener {
	private final PunisherMain plugin;
	public PunisherBlockListener(PunisherMain instance) 
	{
		plugin = instance;
	}
	Logger log = Logger.getLogger("Minecraft");
	@Override
	public void onBlockPlace(BlockPlaceEvent event) {
		Block block = event.getBlockPlaced();
		Player player = event.getPlayer();
		Server server = player.getServer();
		{
			
			if (!plugin.check(player, "punisher.blockplace.exemptall"))
			{
				if	(block.getType() == Material.matchMaterial("46")) {
				String playername = player.getName();
				if (plugin.notify.matches("true"))
				{
					for (Player p : server.getOnlinePlayers()) {
			            if (plugin.check(p, "punisher.blockplace.notify")) {
			                player.sendMessage((ChatColor.RED + "[Punisher] " + playername + " has placed " + block.getType() + " (" + block.getTypeId() + ")" + " in world: " + block.getWorld().getName() + " at " + block.getX() + "," + block.getY() + "," + block.getZ() + "."));
			            }
			        }
				}
				if (plugin.notifyconsole.matches("true"))
				{
				log.warning("[Punisher] " + playername + " has placed " + block.getType() + " (" + block.getTypeId() + ")" + " in world: " + block.getWorld().getName() + " at " + block.getX() + "," + block.getY() + "," + block.getZ() + ".");
				}
				if (plugin.removeblock.matches("true"))
				{
					if (!plugin.check(player, "punisher.blockplace.removeblockexempt"))
					{
						block.setTypeId(0);
					}
				}
				if (plugin.kickonplace.matches("true"))
				{
					if (!plugin.check(player, "punisher.blockplace.kickexempt"))
					{
					player.kickPlayer(plugin.kickmessage);
					}
				}
						if (plugin.lightningonplace.matches("true"))
						{
							if (!plugin.check(player, "punisher.blockplace.lightningexempt"))
							{
							player.getWorld().strikeLightning(player.getLocation());
							}
						}
				if (plugin.setfireonplace.matches("true"))
				{
					if (!plugin.check(player, "punisher.blockplace.fireexempt"))
					{
				player.setFireTicks(plugin.fireseconds * 20);
					}
				}
				if (plugin.killonplace.matches("true"))
				{
					if (!plugin.check(player, "punisher.blockplace.killexempt"))
					{
				player.setHealth(0);
					}
				}
			}
		    }
		
}
}
}